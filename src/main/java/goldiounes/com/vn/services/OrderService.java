package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.OrderDTO;
import goldiounes.com.vn.models.dtos.OrderDetailDTO;
import goldiounes.com.vn.models.entities.*;
import goldiounes.com.vn.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

//        int totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails) {
            Product product = productRepo.findById(orderDetail.getProduct().getProductID())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            orderDetail.setProduct(product);
            orderDetail.setOrder(order);
//            totalPrice += product.getSellingPrice() * orderDetail.getQuantity();
        }
//
//        if (order.getPromotion().getPromotionID() != 0) {
//            Promotion promotion = promotionRepo.findById(order.getPromotion().getPromotionID())
//                    .orElseThrow(() -> new RuntimeException("Promotion not found"));
//            order.setPromotion(promotion);
//            totalPrice -= (promotion.getDiscountPercent() * totalPrice) / 100;
//        }
//        else {
//            order.setPromotion(null);
//        }
//
//
//        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepo.save(order);

        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(savedOrder);
            orderDetailService.save(modelMapper.map(orderDetail,OrderDetailDTO.class));
        }
//        emailService.sendSizeSelectionEmail(order.getUser().getEmail(), order.getOrderID());
        return modelMapper.map(savedOrder, OrderDTO.class);
    }


    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
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
        existingOrder.setTotalPrice(totalPrice);
        User existingUser = existingOrder.getUser();
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        Point point = existingUser.getPoint();
        point.setPoints(point.getPoints() + totalPrice/10000);
        pointRepo.save(point);
        existingOrder.setUser(existingUser);
        Order savedOrder = orderRepo.save(existingOrder);
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

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        if (orders.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }
}