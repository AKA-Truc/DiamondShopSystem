package goldiounes.com.vn.controllers;

import goldiounes.com.vn.services.OrderService;
import goldiounes.com.vn.services.ProductService;
import goldiounes.com.vn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;



}
