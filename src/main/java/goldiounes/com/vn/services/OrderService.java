package goldiounes.com.vn.services;

import goldiounes.com.vn.models.*;
import goldiounes.com.vn.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Order createOrder(Order order) {
        User user = userRepo.findById(order.getUser().getUserID()).get();
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        order.setUser(user);
        Cart cart = cartRepo.findById(order.getCart().getCartID()).get();
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        order.setCart(cart);
        Promotion promotion = promotionRepo.findById(order.getPromotion().getPromotionID()).get();
        if (promotion == null) {
            throw new RuntimeException("Promotion not found");
        }
        order.setPromotion(promotion);
        order.setStatus("New");

        List<OrderDetail> orderDetails = new ArrayList<>();
        int totalPrice = 0;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Product product = productRepo.findById(orderDetail.getProduct().getProductID()).get();
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
            orderDetail.setProduct(product);
            orderDetail.setOrder(order);

            totalPrice += product.getSellingPrice() * orderDetail.getQuantity();
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(totalPrice);

        return orderRepo.save(order);
    }

    public Order findById(int id) {
        return orderRepo.findById(id).get();
    }

    public void deleteById(int id) {
        orderRepo.deleteById(id);
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public Order updateOrder(int id, Order order) {
        Order existingOrder = orderRepo.findById(id).get();

        if (existingOrder == null) {
            throw new RuntimeException("Order not found");
        }

        existingOrder.setStatus(order.getStatus());
        existingOrder.setShippingAddress(order.getShippingAddress());
        existingOrder.setTotalPrice(order.getTotalPrice());

        List<OrderDetail> updatedOrderDetails = order.getOrderDetails();
        List<OrderDetail> existingOrderDetails = existingOrder.getOrderDetails();

        for (OrderDetail updatedDetail : updatedOrderDetails) {
            for (OrderDetail existingDetail : existingOrderDetails) {
                if (existingDetail.getProduct().getProductID() == updatedDetail.getProduct().getProductID()) {
                    existingDetail.setQuantity(updatedDetail.getQuantity());
                    break;
                }
            }
        }
        return orderRepo.save(existingOrder);
    }

    public void deleteOrder(int id) {
        Order existingOrder = orderRepo.findById(id).get();
        if (existingOrder == null) {
            throw new RuntimeException("Order not found");
        }
        if (!existingOrder.getStatus().equals("NEW")) {
            throw new RuntimeException("Cannot delete order that is not in 'NEW' status");
        }
        orderRepo.delete(existingOrder);
    }
}
