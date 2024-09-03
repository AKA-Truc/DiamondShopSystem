package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
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
    private ProductService productService;

    @GetMapping("/product-topSelling")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductDTO>>> getTopProductsSelling() {
        List<ProductDTO> productDTOS = productService.getTopSellingProducts();
        ResponseWrapper<List<ProductDTO>> response = new ResponseWrapper<>("Users retrieved successfully", productDTOS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/revenue-month")
    public ResponseEntity<ResponseWrapper<Integer>> getRevenueByMonth(@RequestParam int month, @RequestParam int year) {
        int revenue = orderService.getRevenueBySpecificMonth(year, month);
        ResponseWrapper<Integer> response = new ResponseWrapper<>("Revenue retrieved successfully", revenue);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/revenue-year")
    public ResponseEntity<ResponseWrapper<Integer>> getRevenueByYear(@RequestParam int year) {
        int revenue = orderService.getRevenueBySpecificYear(year);
        ResponseWrapper<Integer> response = new ResponseWrapper<>("Revenue retrieved successfully", revenue);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/order-date")
    public ResponseEntity<ResponseWrapper<List<Object[]>>> getTotalOrderByDate() {
        List<Object[]> totalOrder = orderService.getCountOrdersByMonth();
        ResponseWrapper<List<Object[]>> response = new ResponseWrapper<>("Total order retrieved successfully", totalOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/order-month")
    public ResponseEntity<ResponseWrapper<List<Object[]>>> getTotalOrderByMonth() {
        List<Object[]> totalOrder = orderService.getCountOrdersByMonth();
        ResponseWrapper<List<Object[]>> response = new ResponseWrapper<>("Total order retrieved successfully", totalOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/order-year")
    public ResponseEntity<ResponseWrapper<List<Object[]>>> getTotalOrderByYear() {
        List<Object[]> totalOrder = orderService.getCountOrdersByYear();
        ResponseWrapper<List<Object[]>> response = new ResponseWrapper<>("Total order retrieved successfully", totalOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
