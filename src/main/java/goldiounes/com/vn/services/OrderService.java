package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.*;
import goldiounes.com.vn.models.entities.*;
import goldiounes.com.vn.repositories.*;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private PromotionRepo promotionRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ProductDetailRepo productDetailRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PointRepo pointRepo;

    @Autowired
    private WarrantyService warrantyService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        User user = userRepo.findById(order.getUser().getUserID())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        Cart cart = cartRepo.findCartByUserId(user.getUserID());
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        order.setCart(cart);

        List<OrderDetail> orderDetails = order.getOrderDetails();
        if (orderDetails.isEmpty()) {
            throw new RuntimeException("OrderDetail not found");
        }

        for (CartItem cartItem : cart.getCartItems()) {
            for (OrderDetail orderDetail : orderDetails) {
                if (orderDetail.getProduct().getProductID() == cartItem.getProduct().getProductID()) {
                    cartItemService.removeCartItem(cartItem.getCartItemID());
                }
            }
        }

        order.setStatus("New");

        Order savedOrder = orderRepo.save(order);

        for (OrderDetail orderDetail : orderDetails) {
            Product product = productRepo.findById(orderDetail.getProduct().getProductID())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            orderDetail.setProduct(product);
            orderDetail.setOrder(savedOrder);
            orderDetailService.save(modelMapper.map(orderDetail, OrderDetailDTO.class));

            // Tạo warranty cho mỗi OrderDetail
            WarrantyDTO warrantyDTO = new WarrantyDTO();
            warrantyDTO.setProduct(modelMapper.map(product, ProductDTO.class));
            warrantyDTO.setUser(modelMapper.map(user, UserDTO.class));

            // Thiết lập ngày kết thúc warranty
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, (int) product.getWarrantyPeriod());
            warrantyDTO.setEndDate(calendar.getTime());

            // Lưu warranty
            warrantyService.createWarranty(savedOrder.getOrderID(), warrantyDTO);
        }
        emailService.sendSizeSelectionEmail(order.getUser().getEmail());
        return modelMapper.map(savedOrder, OrderDTO.class);
    }


    public OrderDTO updateOrder(int id, OrderDTO orderDTO){
        Order order = modelMapper.map(orderDTO, Order.class);
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        existingOrder.setStatus(order.getStatus());
        existingOrder.setShippingAddress(order.getShippingAddress());

        int totalPrice = 0;

        for (OrderDetail orderDetail : order.getOrderDetails()) {
            OrderDetail existingOrderDetail = existingOrder.getOrderDetails().stream()
                    .filter(od -> od.getOrderDetailID() == orderDetail.getOrderDetailID())
                    .findFirst()
                    .orElse(null);

            if (existingOrderDetail == null) {
                throw new RuntimeException("OrderDetail not found");
            }
            existingOrderDetail.setSize(orderDetail.getSize());
            existingOrderDetail.setQuantity(orderDetail.getQuantity());

            OrderDetailDTO updatedOrderDetailDTO = orderDetailService.update(
                    existingOrderDetail.getOrderDetailID(),
                    modelMapper.map(existingOrderDetail, OrderDetailDTO.class)
            );

            Product product = orderDetail.getProduct();
            ProductDetail productDetail = productDetailRepo.findBySizeAndProductId(orderDetail.getSize(), product.getProductID());
            totalPrice += productDetail.getSellingPrice() * orderDetail.getQuantity();
        }

        if (orderDTO.getPromotion() != null) {
            Promotion promotion = promotionRepo.findById(orderDTO.getPromotion().getPromotionId())
                    .orElseThrow(() -> new RuntimeException("Promotion not found"));

            totalPrice = totalPrice - (promotion.getDiscountPercent() * totalPrice) / 100;
            existingOrder.setPromotion(promotion);
        }

        User existingUser = existingOrder.getUser();
        Point point = existingUser.getPoint();

        if (totalPrice >= 10000000) {
            int discountAmount = (int) (totalPrice * 0.1);
            if (point.getPoints() >= discountAmount) {
                totalPrice -= discountAmount;
                point.setPoints(point.getPoints() - discountAmount);
            }
        } else if (totalPrice >= 5000000) {
            int discountAmount = (int) (totalPrice * 0.05);
            if (point.getPoints() >= discountAmount) {
                totalPrice -= discountAmount;
                point.setPoints(point.getPoints() - discountAmount);
            }
        }

        existingOrder.setTotalPrice(totalPrice);
        point.setPoints(point.getPoints() + totalPrice / 100);
        pointRepo.save(point);
        existingOrder.setUser(existingUser);
        Order savedOrder = orderRepo.save(existingOrder);
        emailService.sendInvoiceEmail(existingUser.getEmail(), savedOrder);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }



    public boolean deleteOrder(int id) {
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (existingOrder.getStatus().equals("Final")){
            throw new RuntimeException("Order is Final");
        }
        for (OrderDetail orderDetail : existingOrder.getOrderDetails()) {
            if(!orderDetailService.deleteById(orderDetail.getOrderDetailID())){
                throw new RuntimeException("Delete OrderDetails Failed");
            }
        }
        orderRepo.deleteById(existingOrder.getOrderID());
        return true;
    }

    public OrderDTO getOrderById(int id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderDTO.class);
    }

    public List<OrderDTO> getOrderByUserId(int id) {
        List<Order> order = orderRepo.findByUserId(id);
        if (order.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        return modelMapper.map(order, new TypeToken<List<OrderDTO>>() {}.getType());
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        if (orders.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public boolean isOrderBelongsToCustomer(int orderId, String username) {
        Optional<Order> orderOptional = orderRepo.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            User user = order.getUser(); // Giả sử Order có một trường User
            return user != null && user.getEmail().equals(username);
        }
        return false;
    }

    public int getRevenueBySpecificMonth(int year, int month) {
        return orderRepo.findRevenueBySpecificMonth(year, month);
    }

    public int getRevenueBySpecificYear(int year) {
        return orderRepo.findRevenueBySpecificYear(year);
    }

    public List<Object[]> getCountOrdersByDate() {
        return orderRepo.countOrdersByDate();
    }

    public List<Object[]> getCountOrdersByMonth() {
        return orderRepo.countOrdersByMonth();
    }

    public List<Object[]> getCountOrdersByYear() {
        return orderRepo.countOrdersByYear();
    }

}