package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.Order;
import goldiounes.com.vn.models.OrderDetail;
import goldiounes.com.vn.services.OrderDetailService;
import goldiounes.com.vn.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-management")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order) {
        Order existingOrder = orderService.findById(order.getOrderID());
        if (existingOrder != null) {
            throw new RuntimeException("Order already exists");
        }
        return orderService.createOrder(order);
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        List<Order> orders = orderService.findAll();
        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found");
        }
        return orders;
    }

    @GetMapping("/orders/{id}")
    public Order getOrder(@PathVariable int id) {
        Order existingOrder = orderService.findById(id);
        if (existingOrder == null) {
            throw new RuntimeException("Order not found");
        }
        return existingOrder;
    }

    @PutMapping("/orders/{id}")
    public void updateOrder(@PathVariable int id, @RequestBody Order order) {
        orderService.updateOrder(id, order);
    }

    @DeleteMapping("/orders/{id}")
    public void deleteOrder(@PathVariable int id, @RequestBody Order order) {
        orderService.deleteOrder(id);
    }

    @PostMapping("/orders/{orderId}/details")
    public OrderDetail createOrderDetail(@PathVariable int orderId, @RequestBody OrderDetail orderDetail) {
        orderDetail.setOrder(orderService.findById(orderId));
        return orderDetailService.save(orderDetail);
    }

    @GetMapping("/orders/{orderId}/details")
    public List<OrderDetail> getOrderDetailByOrderId(@PathVariable int orderId) {
        return orderDetailService.findByOrderId(orderId);
    }

    public void deleteOrderDetail(@RequestBody OrderDetail orderDetail) {
        OrderDetail existingOrderDetail = orderDetailService.findById(orderDetail.getOrderDetailID());
        if (existingOrderDetail != null) {
            throw new RuntimeException("OrderDetail already exists");
        }
        orderDetailService.deleteById(orderDetail.getOrderDetailID());
    }

    public void updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        OrderDetail existingOrderDetail = orderDetailService.findById(orderDetail.getOrderDetailID());
        if (existingOrderDetail != null) {
            throw new RuntimeException("OrderDetail already exists");
        }
        existingOrderDetail.setOrder(orderDetail.getOrder());
        existingOrderDetail.setProduct(orderDetail.getProduct());
        existingOrderDetail.setQuantity(orderDetail.getQuantity());
        existingOrderDetail.setPrice(orderDetail.getPrice());
        orderDetailService.save(orderDetail);
    }
}
