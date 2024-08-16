package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.WarrantyDTO;
import goldiounes.com.vn.models.entities.Order;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.models.entities.Warranty;
import goldiounes.com.vn.repositories.OrderRepo;
import goldiounes.com.vn.repositories.WarrantyRepo;
import goldiounes.com.vn.repositories.ProductRepo;
import goldiounes.com.vn.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class WarrantyService {

    @Autowired
    private WarrantyRepo warrantyRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<WarrantyDTO> getAllWarranties() {
        List<Warranty> warranties = warrantyRepo.findAll();
        if (warranties.isEmpty()) {
            throw new RuntimeException("No warranties found");
        }
        return modelMapper.map(warranties, new TypeToken<List<WarrantyDTO>>() {}.getType());
    }

    public WarrantyDTO getWarranty(int id) {
        Warranty existingWarranty = warrantyRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Warranty not found"));
        return modelMapper.map(existingWarranty, WarrantyDTO.class);
    }

    public List<WarrantyDTO> findByUserEmail( String email) {
        User existingUser = userRepo.findByEmail(email);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        List<Warranty> warranties = warrantyRepo.findByUserID(existingUser.getUserID());
        if (warranties.isEmpty()) {
            throw new RuntimeException("Warranty not found");
        }
        return modelMapper.map(warranties, new TypeToken<List<WarrantyDTO>>() {}.getType());
    }

    public WarrantyDTO createWarranty(int orderId, WarrantyDTO warrantyDTO) {
        Warranty warranty = modelMapper.map(warrantyDTO, Warranty.class);
        Product existingProduct = productRepo.findById(warranty.getProduct().getProductID())
                .orElseThrow(()-> new RuntimeException("Product not found"));
        User existingUser = userRepo.findById(warranty.getUser().getUserID())
                .orElseThrow(()-> new RuntimeException("User not found"));
        Calendar calendar = Calendar.getInstance();
        Order order = orderRepo.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
        Date startDate = order.getStartDate();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, (int) existingProduct.getWarrantyPeriod());
        Date endDate = calendar.getTime();

        warranty.setEndDate(endDate);
        warranty.setProduct(existingProduct);
        warranty.setUser(existingUser);
        warrantyRepo.save(warranty);
        return modelMapper.map(warranty, WarrantyDTO.class );
    }

    public WarrantyDTO updateWarranty(int id, WarrantyDTO warrantyDTO) {
        Warranty warranty = modelMapper.map(warrantyDTO, Warranty.class);
        Warranty existingWarranty = warrantyRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Warranty not found"));

        existingWarranty.setEndDate(warranty.getEndDate());

        if (warranty.getProduct() != null) {
            Product existingProduct = productRepo.findById(warranty.getProduct().getProductID())
                    .orElseThrow(()-> new RuntimeException("Product not found"));
            existingWarranty.setProduct(existingProduct);
        }

        if (warranty.getUser() != null) {
            User existingUser = userRepo.findById(warranty.getUser().getUserID())
                    .orElseThrow(()-> new RuntimeException("User not found"));
            existingWarranty.setUser(existingUser);
        }

        warrantyRepo.save(existingWarranty);
        return modelMapper.map(existingWarranty, WarrantyDTO.class);
    }

    public boolean deleteWarranty(int id) {
        if (!warrantyRepo.existsById(id)) {
            throw new RuntimeException("No warranty found ");
        }
        warrantyRepo.deleteById(id);
        return true;
    }
}
