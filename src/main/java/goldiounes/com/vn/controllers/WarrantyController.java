package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.Product;
import goldiounes.com.vn.models.User;
import goldiounes.com.vn.models.Warranty;
import goldiounes.com.vn.services.ProductService;
import goldiounes.com.vn.services.UserService;
import goldiounes.com.vn.services.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("Warranty")
public class WarrantyController {
    @Autowired
    private WarrantyService  warrantyService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/warranties")
    public List<Warranty> getWarranty() {
        List<Warranty> warranties = warrantyService.findAll();
        if (warranties.isEmpty()) {
            throw new RuntimeException("Warranty not found");
        }
        return warranties;
    }
    @GetMapping("/warranties/{id}")
    public Warranty getWarrantyById(@PathVariable int id) {
        Warranty existingWarranty = warrantyService.findById(id);
        if (existingWarranty == null) {
            throw new RuntimeException("Warranty not found");
        }
        return existingWarranty;
    }
    @GetMapping("/warranties/{email}")
    public List<Warranty> findByUserEmail(@PathVariable String email) {
        User existingUser = userService.findByEmail(email);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        List<Warranty> warranties = warrantyService.findByUserId(existingUser.getUserID());
        if (warranties.isEmpty()) {
            throw new RuntimeException("Warranty not found");
        }
        return warranties;
    }
    @PostMapping("/warranties")
    public Warranty createWarranty(Warranty warranty) {
        Product existingProduct = productService.findById(warranty.getProduct().getProductID());
        if (existingProduct == null) {
            throw new RuntimeException("Product not found");
        }
        Date startDate = warranty.getStartDate();

        // Tạo Calendar instance và set thời gian bảo hành vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, (int) existingProduct.getWarrantyPeriod());

        // Lấy ngày kết thúc bảo hành từ Calendar
        Date endDate = calendar.getTime();

        // Đặt ngày kết thúc bảo hành
        warranty.setEndDate(endDate);

        return warrantyService.save(warranty);
    }
    @DeleteMapping("/warranties/{id}")
    public void deleteWarranty(int id) {
        Warranty existingWarranty = warrantyService.findById(id);
        if (existingWarranty == null) {
            throw new RuntimeException("Warranty not found");
        }
        warrantyService.deleteById(existingWarranty.getWarrantyID());
    }
    @PutMapping("/warranties/{id}")
    public Warranty updateWarranty(@RequestBody Warranty warranty, @PathVariable int id) {
        Warranty existingwarranty = warrantyService.findById(warranty.getWarrantyID());
        if (existingwarranty == null) {
            throw new RuntimeException("Warranty not found");
        }
        existingwarranty.setUser(warranty.getUser());
        existingwarranty.setStartDate(warranty.getStartDate());
        existingwarranty.setEndDate(warranty.getEndDate());
        existingwarranty.setWarrantyDetails(warranty.getWarrantyDetails());
        existingwarranty.setProduct(warranty.getProduct());
        return warrantyService.save(existingwarranty);
    }
}
