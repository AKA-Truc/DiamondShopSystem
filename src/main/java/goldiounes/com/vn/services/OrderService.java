package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.OrderDTO;
import goldiounes.com.vn.models.dtos.OrderDetailDTO;
import goldiounes.com.vn.models.entities.*;
import goldiounes.com.vn.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private CartItemRepo cartItemRepo;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public OrderDTO createOrder(Order order) {
        User user = userRepo.findById(order.getUser().getUserID())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        Cart cart = cartRepo.findCartByUserId(user.getUserID());
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        List<OrderDetail> orderDetails = order.getOrderDetails();
        if (orderDetails.isEmpty()) {
            throw new RuntimeException("OrderDetail not found");
        }

        for (CartItem cartItem : cart.getCartItems()) {
            for (OrderDetail orderDetail : orderDetails) {
                if (orderDetail.getProduct().getProductID() == cartItem.getProduct().getProductID()) {
                    cartItemRepo.delete(cartItem);
                }
            }
        }

        order.setCart(cart);
        order.setStatus("New");

        int totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails) {
            Product product = productRepo.findById(orderDetail.getProduct().getProductID())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            orderDetail.setProduct(product);
            orderDetail.setOrder(order); // Set the order for each order detail
            totalPrice += product.getSellingPrice() * orderDetail.getQuantity();
        }

        if (order.getPromotion() != null) {
            Promotion promotion = promotionRepo.findById(order.getPromotion().getPromotionID())
                    .orElseThrow(() -> new RuntimeException("Promotion not found"));
            order.setPromotion(promotion);
            totalPrice -= (promotion.getDiscountPercent() * totalPrice) / 100;
        }

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepo.save(order); // Save the order first

        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(savedOrder); // Ensure orderDetail references the saved order
            orderDetailService.save(orderDetail,savedOrder.getOrderID()); // Save each orderDetail
        }

        return modelMapper.map(savedOrder, OrderDTO.class);
    }


    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        existingOrder.setStatus(orderDTO.getStatus());
        existingOrder.setShippingAddress(orderDTO.getShippingAddress());

        List<OrderDetail> updatedOrderDetails = new ArrayList<>();
        int totalPrice = 0;
        Promotion promotion = promotionRepo.findById(orderDTO.getPromotion().getPromotionID())
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        for (OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetails()) {
            Product product = productRepo.findById(orderDetailDTO.getProduct().getProductID())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(orderDetailDTO.getQuantity());
            orderDetail.setOrder(existingOrder);
            updatedOrderDetails.add(orderDetail);
            totalPrice += product.getSellingPrice() * orderDetail.getQuantity();
        }
        totalPrice = totalPrice - (promotion.getDiscountPercent() * totalPrice) / 100;
        existingOrder.setOrderDetails(updatedOrderDetails);
        existingOrder.setPromotion(promotion);
        existingOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepo.save(existingOrder);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    public void deleteOrder(int id) {
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!existingOrder.getStatus().equals("NEW")) {
            throw new RuntimeException("Cannot delete order that is not in 'NEW' status");
        }
        orderRepo.delete(existingOrder);
    }

    public OrderDTO getOrderById(int id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderDTO.class);
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }
}
