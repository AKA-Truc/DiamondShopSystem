package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.dtos.UserDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.OrderDetailService;
import goldiounes.com.vn.services.OrderService;
import goldiounes.com.vn.services.ProductService;
import goldiounes.com.vn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ProductService productService;

    @GetMapping("/product-topSelling")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<List<Object[]>>> getTopProductsSelling() {
        List<Object[]> productDTOS = productService.getTopSellingProducts();
        ResponseWrapper<List<Object[]>> response = new ResponseWrapper<>("List products retrieved successfully", productDTOS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/products/totalSold")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Long>> getTotalProductsSold() {
        Long totalSold = orderDetailService.findTotalProductsSold();
        if (totalSold != null && totalSold > 0) {
            ResponseWrapper<Long> response = new ResponseWrapper<>("Total number of products sold retrieved successfully", totalSold);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<Long> response = new ResponseWrapper<>("No products sold", 0L);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/revenue-month")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Long>> getRevenueByMonth(@RequestParam int month, @RequestParam int year) {
        Long revenue = orderService.getRevenueBySpecificMonth(year, month);
        ResponseWrapper<Long> response = new ResponseWrapper<>("Revenue retrieved successfully", revenue);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<List<Object[]>>> getRevenue(@RequestParam int year) {
        List<Object[]> revenue = orderService.getRevenue(year);
        if (!revenue.isEmpty()) {
            ResponseWrapper<List<Object[]>> response = new ResponseWrapper<>("Revenue retrieved successfully", revenue);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<List<Object[]>> response = new ResponseWrapper<>("No revenue found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/order")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Long>> getTotalOrder() {
        Long totalOrder = orderService.getCountOrders();
        ResponseWrapper<Long> response = new ResponseWrapper<>("Total order retrieved successfully", totalOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/order-date")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Long>> getTotalOrderByDate() {
        Long totalOrder = orderService.getCountOrdersToday();
        ResponseWrapper<Long> response = new ResponseWrapper<>("Total order retrieved successfully", totalOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/order-year")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<List<Object[]>>> getTotalOrderByYear(@RequestParam int year) {
        List<Object[]> totalOrder = orderService.getCountOrdersByYear(year);
        ResponseWrapper<List<Object[]>> response = new ResponseWrapper<>("Total order retrieved successfully", totalOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user-list")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<List<UserDTO>>> getUsers() {
        List<UserDTO> userDTOS = userService.getTopUser();
        if (!userDTOS.isEmpty()) {
            ResponseWrapper<List<UserDTO>> response = new ResponseWrapper<>("Users retrieved successfully", userDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<List<UserDTO>> response = new ResponseWrapper<>("No user found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/customer/gender")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<List<Object[]>>> getCustomerGender() {
        List<Object[]> count = userService.getCountCustomerByGender();

        if (!count.isEmpty()) {
            ResponseWrapper<List<Object[]>> response = new ResponseWrapper<>("Users retrieved successfully", count);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<List<Object[]>> response = new ResponseWrapper<>("No user found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Long>> getTotalCustomer() {
        Long totalCustomer = userService.getCountCustomer();
        ResponseWrapper<Long> response = new ResponseWrapper<>("Total customer", totalCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
