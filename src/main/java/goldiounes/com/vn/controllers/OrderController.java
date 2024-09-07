package goldiounes.com.vn.controllers;

import goldiounes.com.vn.config.CustomUserDetails;
import goldiounes.com.vn.models.dtos.OrderDTO;
import goldiounes.com.vn.models.dtos.OrderDetailDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.OrderDetailService;
import goldiounes.com.vn.services.OrderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/order-management")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @PostMapping("/orders")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO, Authentication authentication) {
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"))) {
            if (orderDTO.getUser().getUserId() != currentUser.getId()) {
                return new ResponseEntity<>(new ResponseWrapper<>("Access denied", null), HttpStatus.FORBIDDEN);
            }
        }

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

    @GetMapping("/Bill")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF', 'ROLE_DELIVERY STAFF', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<List<OrderDTO>>> getAllOrderDone() {
        List<OrderDTO> orders = orderService.getAllOrderDone();
        ResponseWrapper<List<OrderDTO>> response;
        if (!orders.isEmpty()) {
            response = new ResponseWrapper<>("Orders Done retrieved successfully", orders);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("No orders found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/NotDone")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF', 'ROLE_DELIVERY STAFF', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<List<OrderDTO>>> getAllOrderNotDone() {
        List<OrderDTO> orders = orderService.getAllOrderNotDone();
        ResponseWrapper<List<OrderDTO>> response;

        if (!orders.isEmpty()) {
            response = new ResponseWrapper<>("Orders Not Done retrieved successfully", orders);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("No orders found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF', 'ROLE_DELIVERY STAFF', 'ROLE_MANAGER', 'ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<OrderDTO>> getOrder(@PathVariable int id, Authentication authentication) {
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        OrderDTO order = orderService.getOrderById(id);

        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"))) {
            if (order.getUser().getUserId() != currentUser.getId()) {
                return new ResponseEntity<>(new ResponseWrapper<>("Access denied", null), HttpStatus.FORBIDDEN);
            }
        }

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


    @GetMapping("/orders/user/{uid}")
    @PreAuthorize(
            "hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SALE_STAFF', 'ROLE_DELIVERY_STAFF') or " +
                    "(hasAuthority('ROLE_CUSTOMER') and #uid == #authentication.principal.id)"
    )
    public ResponseEntity<ResponseWrapper<List<OrderDTO>>> getOrderByUserID(@PathVariable int uid, Authentication authentication) {

        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"))) {
            if (uid != currentUser.getId()) {
                return new ResponseEntity<>(new ResponseWrapper<>("Access denied", null), HttpStatus.FORBIDDEN);
            }
        }
        List<OrderDTO> orders = orderService.getOrderByUserId(uid);
        ResponseWrapper<List<OrderDTO>> response;
        if (!orders.isEmpty()) {
            response = new ResponseWrapper<>("Orders retrieved successfully", orders);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("No orders found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/orders/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF','ROLE_DELIVERY STAFF', 'ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<OrderDTO>> updateOrder(@PathVariable int id, @RequestBody OrderDTO orderDTO, Authentication authentication) throws MessagingException, IOException {
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"))) {
            if (orderDTO.getUser().getUserId() != currentUser.getId()) {
                return new ResponseEntity<>(new ResponseWrapper<>("Access denied", null), HttpStatus.FORBIDDEN);
            }
        }

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

    @PatchMapping("/orders/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_DELIVERY STAFF')")
    public ResponseEntity<ResponseWrapper<OrderDTO>> updateStatus(@PathVariable int id, String status) {
        OrderDTO updatedOrder = orderService.updateStatus(id, status);
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF','ROLE_CUSTOMER')")
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
        log.debug("isCustomerOrder() called with username: " + authentication.getName());
        return orderService.isOrderBelongsToCustomer(orderId, authentication.getName());
    }
}
