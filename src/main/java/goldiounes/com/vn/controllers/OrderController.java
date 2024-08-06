package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dto.OrderDTO;
import goldiounes.com.vn.models.dto.OrderDetailDTO;
import goldiounes.com.vn.models.entity.Order;
import goldiounes.com.vn.models.entity.OrderDetail;
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
    public OrderDTO createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/orders")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{id}")
    public OrderDTO getOrder(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/orders/{id}")
    public void updateOrder(@PathVariable int id, @RequestBody OrderDTO orderDTO) {
        orderService.updateOrder(id, orderDTO);
    }

    @DeleteMapping("/orders/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }

    @PostMapping("/orders/{orderId}/details")
    public OrderDetailDTO createOrderDetail(@PathVariable int orderId, @RequestBody OrderDetail orderDetail) {
        return orderDetailService.save(orderDetail);
    }

    @GetMapping("/orders/{orderId}/details")
    public List<OrderDetailDTO> getOrderDetailByOrderId(@PathVariable int orderId) {
        return orderDetailService.findByOrderId(orderId);
    }

    @DeleteMapping("/orders/{orderId}/details/{id}")
    public void deleteOrderDetail(@RequestBody OrderDetail orderDetail) {
        orderDetailService.deleteById(orderDetail.getOrderDetailID());
    }

    @PutMapping("/orders/{orderId}/details/{id}")
    public OrderDetailDTO updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.save(orderDetail);
    }
}
