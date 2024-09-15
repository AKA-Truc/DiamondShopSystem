package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.*;
import goldiounes.com.vn.models.entities.*;
import goldiounes.com.vn.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private PromotionService promotionService;

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
        //emailService.sendSizeSelectionEmail(order.getUser().getEmail());
        return modelMapper.map(savedOrder, OrderDTO.class);
    }


    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        // Ánh xạ từ orderDTO sang order
        Order order = modelMapper.map(orderDTO, Order.class);

        // Kiểm tra xem promotion trong orderDTO có bị null không
        if (orderDTO.getPromotion() == null) {
            System.out.println("OrderDTO promotion is null, skipping promotion update");
        } else {
            System.out.println("OrderDTO promotion exists: " + orderDTO.getPromotion());
        }

        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        existingOrder.setStatus(order.getStatus());
        existingOrder.setShippingAddress(order.getShippingAddress());
        existingOrder.setTypePayment(order.getTypePayment());
        existingOrder.setPhone(order.getPhone());

        int totalPrice = 0;

        for (OrderDetail orderDetail : order.getOrderDetails()) {
            OrderDetail existingOrderDetail = existingOrder.getOrderDetails().stream()
                    .filter(od -> od.getOrderDetailID() == orderDetail.getOrderDetailID())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("OrderDetail not found"));

            existingOrderDetail.setSize(orderDetail.getSize());
            existingOrderDetail.setQuantity(orderDetail.getQuantity());

            OrderDetailDTO updatedOrderDetailDTO = orderDetailService.update(
                    existingOrderDetail.getOrderDetailID(),
                    modelMapper.map(existingOrderDetail, OrderDetailDTO.class)
            );

            Product product = orderDetail.getProduct();
            ProductDetail productDetail = productDetailRepo.findBySizeAndProductId(orderDetail.getSize(), product.getProductID());
            totalPrice += (int) (productDetail.getSellingPrice() * orderDetail.getQuantity());
        }

        System.out.println("Promotion of order: " + order.getPromotion());
        Promotion promotion = order.getPromotion();

        // Kiểm tra promotion của order có null không
        if (promotion != null) {
            int discountPercent = promotion.getDiscountPercent();
            totalPrice = totalPrice - (discountPercent * totalPrice) / 100;
            existingOrder.setPromotion(promotion);
        } else {
            System.out.println("No promotion applied");
            existingOrder.setPromotion(null);
        }

        User existingUser = existingOrder.getUser();
        Point point = existingUser.getPoint();

        if (totalPrice >= 10000000) {
            applyPointDiscount(totalPrice, point, 0.1);
        } else if (totalPrice >= 5000000) {
            applyPointDiscount(totalPrice, point, 0.05);
        }

        existingOrder.setTotalPrice(totalPrice);
        point.setPoints(point.getPoints() + totalPrice / 100);
        pointRepo.save(point);
        existingOrder.setUser(existingUser);
        Order savedOrder = orderRepo.save(existingOrder);
        emailService.sendInvoiceEmail(existingUser.getEmail(), savedOrder);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    private void applyPointDiscount(int totalPrice, Point point, double discountRate) {
        int discountAmount = (int) (totalPrice * discountRate);
        if (point.getPoints() >= discountAmount) {
            totalPrice -= discountAmount;
            point.setPoints(point.getPoints() - discountAmount);
        }
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

    public List<OrderDTO> getAllOrderDone() {
        List<Order> orders = orderRepo.findAll();
        if (orders.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        return orders.stream()
                .filter(order -> order.getStatus().equals("Đã giao"))
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }


    public List<OrderDTO> getAllOrderNotDone() {
        List<Order> orders = orderRepo.findAll();
        if (orders.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        return orders.stream()
                .filter(order -> !order.getStatus().equals("Đã giao"))
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

    public OrderDTO updateStatus(int orderId, String status) {
        Order existingOrder = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        existingOrder.setStatus(status);
        Order savedOrder = orderRepo.save(existingOrder);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    public Long getRevenueBySpecificMonth(int year, int month) {
        return orderRepo.findRevenueBySpecificMonth(year, month);
    }

    public List<Object[]> getRevenue(int year) {
        return orderRepo.findMonthlyRevenue(year);
    }

    public Long getCountOrdersToday() {
        return orderRepo.countOrdersByToday();
    }

    public Long getCountOrders() {
        return orderRepo.countOrder();
    }
    public List<Object[]> getCountOrdersByYear(int year) {
        return orderRepo.countOrdersByMonthInYear(year);
    }

}