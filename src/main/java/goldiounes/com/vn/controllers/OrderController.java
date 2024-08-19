package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.OrderDTO;
import goldiounes.com.vn.models.dtos.OrderDetailDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.OrderDetailService;
import goldiounes.com.vn.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseWrapper<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        ResponseWrapper<OrderDTO> response = new ResponseWrapper<>("Order created successfully", createdOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<ResponseWrapper<List<OrderDTO>>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        ResponseWrapper<List<OrderDTO>> response;

        if (!orders.isEmpty()) {
            response = new ResponseWrapper<>("Orders retrieved successfully", orders);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("No orders found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ResponseWrapper<OrderDTO>> getOrder(@PathVariable int id) {
        OrderDTO order = orderService.getOrderById(id);
        ResponseWrapper<OrderDTO> response;

        if (order != null) {
            response = new ResponseWrapper<>("Order retrieved successfully", order);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("Order not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<ResponseWrapper<OrderDTO>> updateOrder(@PathVariable int id, @RequestBody OrderDTO orderDTO) {
        OrderDTO isUpdated = orderService.updateOrder(id, orderDTO);
        ResponseWrapper<OrderDTO> response;
        if (isUpdated != null) {
            response = new ResponseWrapper<>("Order updated successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("Order not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteOrder(@PathVariable int id) {
        boolean isDeleted = orderService.deleteOrder(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("Order deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Order not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/orders/details")
    public ResponseEntity<ResponseWrapper<OrderDetailDTO>> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO createdOrderDetail = orderDetailService.save(orderDetailDTO);
        ResponseWrapper<OrderDetailDTO> response = new ResponseWrapper<>("Order detail created successfully", createdOrderDetail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}/details")
    public ResponseEntity<ResponseWrapper<List<OrderDetailDTO>>> getOrderDetailByOrderId(@PathVariable int orderId) {
        List<OrderDetailDTO> orderDetails = orderDetailService.findByOrderId(orderId);
        ResponseWrapper<List<OrderDetailDTO>> response;

        if (!orderDetails.isEmpty()) {
            response = new ResponseWrapper<>("Order details retrieved successfully", orderDetails);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("No order details found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/orders/details/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteOrderDetail(@PathVariable int id) {
        boolean isDeleted = orderDetailService.deleteById(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("Order detail deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Order detail not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/orders/details/{id}")
    public ResponseEntity<ResponseWrapper<OrderDetailDTO>> updateOrderDetail(@PathVariable int id, @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO updatedOrderDetail = orderDetailService.update(id, orderDetailDTO);
        ResponseWrapper<OrderDetailDTO> response;

        if (updatedOrderDetail != null) {
            response = new ResponseWrapper<>("Order detail updated successfully", updatedOrderDetail);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("Order detail not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
