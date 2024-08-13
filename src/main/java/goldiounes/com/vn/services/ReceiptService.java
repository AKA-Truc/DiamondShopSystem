package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.ReceiptDTO;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.models.entities.Receipt;
import goldiounes.com.vn.repositories.ProductRepo;
import goldiounes.com.vn.repositories.ReceiptRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepo receiptRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;

    public List<ReceiptDTO> getAllReceipts() {
        List<Receipt> receipts = receiptRepo.findAll();
        if (receipts.isEmpty()) {
            throw new RuntimeException("No receipts found");
        }
        return modelMapper.map(receipts, new TypeToken<List<ReceiptDTO>>() {}.getType());
    }

    public ReceiptDTO getReceipt(int id) {
        Optional<Receipt> existingReceipt = receiptRepo.findById(id);
        if (existingReceipt.isEmpty()) {
            throw new RuntimeException("No receipt found ");
        }
        return modelMapper.map(existingReceipt, new TypeToken<ReceiptDTO>() {}.getType());
    }

    public ReceiptDTO createReceipt(ReceiptDTO receiptDTO) {
        Product existingProduct = productRepo.findById(receiptDTO.getProduct().getProductID()).get();
        if (existingProduct == null) {
            throw new RuntimeException("Product not found");
        }
        Optional<Receipt> existingReceipt = receiptRepo.findById(receiptDTO.getReceiptID());
        if (existingReceipt.isEmpty()) {
            throw new RuntimeException("No receipt found ");
        }
        Receipt receipt = modelMapper.map(receiptDTO, Receipt.class);
        receiptRepo.save(receipt);
        return modelMapper.map(receipt, new TypeToken<ReceiptDTO>() {}.getType());
    }

    public void deleteReceipt(int id) {
        Receipt existingreceipt = receiptRepo.findById(id).get();
        if (existingreceipt == null) {
            throw new RuntimeException("No receipt found ");
        }
        receiptRepo.deleteById(id);
    }

    public ReceiptDTO updateReceipt(int id, ReceiptDTO receiptDTO) {
        Receipt existingReceipt = receiptRepo.findById(receiptDTO.getReceiptID()).get();
        if (existingReceipt == null) {
            throw new RuntimeException("No receipt found ");
        }
        existingReceipt.setQuantity(receiptDTO.getQuantity());
        if (receiptDTO.getProduct() != null && receiptDTO.getProduct().getProductID() > 0) {
            Product existingProduct = productRepo.findById(receiptDTO.getProduct().getProductID()).get();
            existingReceipt.setProduct(existingProduct);
        }
        receiptRepo.save(existingReceipt);
        return modelMapper.map(existingReceipt, new TypeToken<ReceiptDTO>() {}.getType());
}

}
