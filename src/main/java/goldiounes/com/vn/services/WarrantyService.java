package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.WarrantyDTO;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.models.entities.Warranty;
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
    private ModelMapper modelMapper;

    public List<WarrantyDTO> getAllWarranties() {
        List<Warranty> warranties = warrantyRepo.findAll();
        if (warranties.isEmpty()) {
            throw new RuntimeException("No warranties found");
        }
        return modelMapper.map(warranties, new TypeToken<List<WarrantyDTO>>() {}.getType());
    }

    public WarrantyDTO getWarranty(int id) {
        Warranty existingWarranty = warrantyRepo.findById(id).get();
        return modelMapper.map(existingWarranty, WarrantyDTO.class);
    }

    public List<Warranty> findByUserEmail( String email) {
        User existingUser = userRepo.findByEmail(email);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        List<Warranty> warranties = warrantyRepo.findByUserID(existingUser.getUserID());
        if (warranties.isEmpty()) {
            throw new RuntimeException("Warranty not found");
        }
        return warranties;
    }

    public WarrantyDTO createWarranty(WarrantyDTO warrantyDTO) {
        Product existingProduct = productRepo.findById(warrantyDTO.getProduct().getProductID()).get();
        if (existingProduct == null) {
            throw new RuntimeException("Product not found");
        }
        User existingUser = userRepo.findById(warrantyDTO.getUser().getUserID()).get();
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        Date startDate = warrantyDTO.getStartDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, (int) existingProduct.getWarrantyPeriod());
        Date endDate = calendar.getTime();

        Warranty warranty = modelMapper.map(warrantyDTO, Warranty.class);
        warranty.setStartDate(endDate);
        warranty.setProduct(existingProduct);
        warranty.setUser(existingUser);
        warrantyRepo.save(warranty);
        return modelMapper.map(warranty, new TypeToken<WarrantyDTO>() {}.getType() );
    }

    public WarrantyDTO updateWarranty(int id, WarrantyDTO warrantyDTO) {
        Warranty existingWarranty = warrantyRepo.findById(id).get();
        if (existingWarranty == null) {
            throw new RuntimeException("Warranty not found");
        }

        existingWarranty.setWarrantyDetails(warrantyDTO.getWarrantyDetails());
        existingWarranty.setStartDate(warrantyDTO.getStartDate());
        existingWarranty.setEndDate(warrantyDTO.getEndDate());

        if (warrantyDTO.getProduct() != null) {
            Product existingProduct = productRepo.findById(warrantyDTO.getProduct().getProductID()).get();
            if (existingProduct == null) {
                throw new RuntimeException("Product not found");
            }
            existingWarranty.setProduct(existingProduct);
        }

        if (warrantyDTO.getUser() != null) {
            User existingUser = userRepo.findById(warrantyDTO.getUser().getUserID()).get();
            if (existingUser == null) {
                throw new RuntimeException("User not found");
            }
            existingWarranty.setUser(existingUser);
        }

        warrantyRepo.save(existingWarranty);
        return modelMapper.map(existingWarranty, WarrantyDTO.class);
    }

    public void deleteWarranty(int id) {
        if (!warrantyRepo.existsById(id)) {
            throw new RuntimeException("No warranty found ");
        }
        warrantyRepo.deleteById(id);
    }
}
