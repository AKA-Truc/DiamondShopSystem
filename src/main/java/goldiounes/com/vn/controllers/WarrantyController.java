//package goldiounes.com.vn.controllers;
//
//import goldiounes.com.vn.models.entity.Product;
//import goldiounes.com.vn.models.entity.User;
//import goldiounes.com.vn.models.entity.Warranty;
//import goldiounes.com.vn.services.ProductService;
//import goldiounes.com.vn.services.UserService;
//import goldiounes.com.vn.services.WarrantyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping("warranty-management")
//public class WarrantyController {
//    @Autowired
//    private WarrantyService  warrantyService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ProductService productService;
//
//    @GetMapping("/warranties")
//    public List<Warranty> getWarranty() {
//        List<Warranty> warranties = warrantyService.findAll();
//        if (warranties.isEmpty()) {
//            throw new RuntimeException("Warranty not found");
//        }
//        return warranties;
//    }
//    @GetMapping("/warranties/{id}")
//    public Warranty getWarrantyById(@PathVariable int id) {
//        Warranty existingWarranty = warrantyService.findById(id);
//        if (existingWarranty == null) {
//            throw new RuntimeException("Warranty not found");
//        }
//        return existingWarranty;
//    }
//    @GetMapping("/warranties/{email}")
//    public List<Warranty> findByUserEmail(@PathVariable String email) {
//        User existingUser = userService.findByEmail(email);
//        if (existingUser == null) {
//            throw new RuntimeException("User not found");
//        }
//        List<Warranty> warranties = warrantyService.findByUserId(existingUser.getUserID());
//        if (warranties.isEmpty()) {
//            throw new RuntimeException("Warranty not found");
//        }
//        return warranties;
//    }
//    @PostMapping("/warranties")
//    public Warranty createWarranty(@RequestBody Warranty warranty) {
//        Product existingProduct = productService.findById(warranty.getProduct().getProductID());
//        if (existingProduct == null) {
//            throw new RuntimeException("Product not found");
//        }
//
//        User existingUser = userService.findById(warranty.getUser().getUserID());
//        if (existingUser == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        // Calculate the end date based on the product's warranty period
//        Date startDate = warranty.getStartDate();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(startDate);
//        calendar.add(Calendar.MONTH, (int) existingProduct.getWarrantyPeriod());
//        Date endDate = calendar.getTime();
//
//        warranty.setEndDate(endDate);
//        warranty.setProduct(existingProduct);
//        warranty.setUser(existingUser);
//
//        return warrantyService.save(warranty);
//    }
//
//    @DeleteMapping("/warranties/{id}")
//    public void deleteWarranty(@PathVariable int id) {
//        Warranty existingWarranty = warrantyService.findById(id);
//        if (existingWarranty == null) {
//            throw new RuntimeException("Warranty not found");
//        }
//        warrantyService.deleteById(existingWarranty.getWarrantyID());
//    }
//    @PutMapping("/warranties/{id}")
//    public Warranty updateWarranty(@RequestBody Warranty warranty, @PathVariable int id) {
//        Warranty existingwarranty = warrantyService.findById(warranty.getWarrantyID());
//        if (existingwarranty == null) {
//            throw new RuntimeException("Warranty not found");
//        }
//        existingwarranty.setUser(warranty.getUser());
//        existingwarranty.setStartDate(warranty.getStartDate());
//        existingwarranty.setEndDate(warranty.getEndDate());
//        existingwarranty.setWarrantyDetails(warranty.getWarrantyDetails());
//        existingwarranty.setProduct(warranty.getProduct());
//        return warrantyService.save(existingwarranty);
//    }
//}
