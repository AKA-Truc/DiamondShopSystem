package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.OrderDTO;
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
    private OrderService orderService;

    @GetMapping
    public String showSizeSelectionForm(@RequestParam String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "size_selection.html"; // Tên của trang HTML hiển thị biểu mẫu chọn kích cỡ
    }

    @PostMapping
    public String submitSizeSelection(@RequestParam int orderId, @RequestBody OrderDTO orderDTO) {
        orderService.updateOrder(orderId, orderDTO);
        return "Xác nhận đơn hàng thành công";
    }
}
