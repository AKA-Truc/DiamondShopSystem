package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.OrderDTO;
import goldiounes.com.vn.models.dtos.OrderDetailDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.OrderDetailService;
import goldiounes.com.vn.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-management")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @PostMapping("/orders")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        ResponseWrapper<OrderDTO> response = new ResponseWrapper<>("Order created successfully", createdOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF', 'ROLE_DELIVERY STAFF', 'ROLE_MANAGER')")
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF', 'ROLE_DELIVERY STAFF', 'ROLE_MANAGER', 'ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<OrderDTO>> getOrder(@PathVariable int id, Authentication authentication) {
        OrderDTO order = orderService.getOrderById(id);
        ResponseWrapper<OrderDTO> response;

        if (order != null) {
            if (isCustomer(authentication) && !isCustomerOrder(id, authentication)) {
                response = new ResponseWrapper<>("Access denied", null);
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
            }
            response = new ResponseWrapper<>("Order retrieved successfully", order);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("Order not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orders/{uid}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF','ROLE_MANAGER','ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<List<OrderDTO>>> getOrderByUserID(@PathVariable int uid, Authentication authentication) {
        List<OrderDTO> order = orderService.getOrderByUserId(uid);
        ResponseWrapper<List<OrderDTO>> response;
        if (!order.isEmpty()) {
            response = new ResponseWrapper<>("Orders retrieved successfully", order);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("No orders found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/orders/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF','ROLE_DELIVERY STAFF')")
    public ResponseEntity<ResponseWrapper<OrderDTO>> updateOrder(@PathVariable int id, @RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
        ResponseWrapper<OrderDTO> response;
        if (updatedOrder != null) {
            response = new ResponseWrapper<>("Order updated successfully", updatedOrder);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("Order not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/orders/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Void>> deleteOrder(@PathVariable int id) {
        boolean isDeleted = orderService.deleteOrder(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            ResponseWrapper<Void> response = new ResponseWrapper<>("Order not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/orders/details")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<OrderDetailDTO>> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO createdOrderDetail = orderDetailService.save(orderDetailDTO);
        ResponseWrapper<OrderDetailDTO> response = new ResponseWrapper<>("Order detail created successfully", createdOrderDetail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}/details")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF', 'ROLE_DELIVERY STAFF', 'ROLE_MANAGER', 'ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<List<OrderDetailDTO>>> getOrderDetailByOrderId(@PathVariable int orderId, Authentication authentication) {
        if (isCustomer(authentication) && !isCustomerOrder(orderId, authentication)) {
            ResponseWrapper<List<OrderDetailDTO>> response = new ResponseWrapper<>("Access denied", null);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Void>> deleteOrderDetail(@PathVariable int id) {
        boolean isDeleted = orderDetailService.deleteById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            ResponseWrapper<Void> response = new ResponseWrapper<>("Order detail not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/orders/details/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF')")
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

    private boolean isCustomer(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
    }

    private boolean isCustomerOrder(int orderId, Authentication authentication) {
        return orderService.isOrderBelongsToCustomer(orderId, authentication.getName());
    }
}
