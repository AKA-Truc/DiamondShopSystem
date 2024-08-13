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
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
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
            orderDetail.setOrder(order);
            totalPrice += product.getSellingPrice() * orderDetail.getQuantity();
        }

        if (order.getPromotion() != null) {
            Promotion promotion = promotionRepo.findById(order.getPromotion().getPromotionID())
                    .orElseThrow(() -> new RuntimeException("Promotion not found"));
            order.setPromotion(promotion);
            totalPrice -= (promotion.getDiscountPercent() * totalPrice) / 100;
        }

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepo.save(order);

        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(savedOrder);
            orderDetailService.save(modelMapper.map(orderDetail,OrderDetailDTO.class));
        }
        return modelMapper.map(savedOrder, OrderDTO.class);
    }


    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Order order = modelMapper.map(orderDTO, Order.class);

        existingOrder.setStatus(order.getStatus());
        existingOrder.setShippingAddress(order.getShippingAddress());

        List<OrderDetail> updatedOrderDetails = new ArrayList<>();
        int totalPrice = 0;
        Promotion promotion = promotionRepo.findById(order.getPromotion().getPromotionID())
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Product product = productRepo.findById(orderDetail.getProduct().getProductID())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            OrderDetail orderDetailIndex = new OrderDetail();
            orderDetailIndex.setProduct(product);
            orderDetailIndex.setQuantity(orderDetailIndex.getQuantity());
            orderDetailIndex.setOrder(existingOrder);
            updatedOrderDetails.add(orderDetailIndex);
            totalPrice += product.getSellingPrice() * orderDetailIndex.getQuantity();
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
        orderRepo.deleteById(existingOrder.getOrderID());
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
