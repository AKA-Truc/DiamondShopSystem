package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.services.OrderDetailService;
import goldiounes.com.vn.services.OrderService;
import goldiounes.com.vn.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/size-selection")
public class SizeSelectionController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public String showSizeSelectionForm(@RequestParam String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "sizeSelectionForm"; // Tên của trang HTML hiển thị biểu mẫu chọn kích cỡ
    }

//    @PostMapping
//    public String submitSizeSelection(@RequestParam int orderId, @RequestBody ProductDTO productDTO) {
//        orderDetailService.update()
//        return "redirect:/success"; // Trang thông báo thành công
//    }
}
