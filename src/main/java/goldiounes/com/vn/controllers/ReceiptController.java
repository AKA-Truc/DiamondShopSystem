package goldiounes.com.vn.controllers;



import goldiounes.com.vn.models.Receipt;
import goldiounes.com.vn.services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Receipt")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/receipts")
    public Receipt createReceipt(Receipt receipt) {
        Receipt existingReceipt = receiptService.findById(receipt.getReceiptID());
        if (existingReceipt != null) {
            throw new RuntimeException("Receipt already exists");
        }
        return receiptService.save(receipt);
    }
    @GetMapping("/receipts")
    public List<Receipt> getReceipt() {
        List<Receipt> receipts = receiptService.findAll();
        if (receipts.isEmpty()) {
            throw new RuntimeException("Receipts not found");
        }
        return receipts;
    }
    @GetMapping("/receipts/{id}")
    public Receipt getReceiptById(@PathVariable int id) {
        Receipt existingReceipt = receiptService.findById(id);
        if (existingReceipt == null) {
            throw new RuntimeException("Receipts not found");
        }
        return existingReceipt;
    }
    @DeleteMapping("/receipts/{id}")
    public void deleteReceipt(@PathVariable int id) {
        Receipt existingReceipt = receiptService.findById(id);
        if (existingReceipt == null) {
            throw new RuntimeException("Receipts not found");
        }
        receiptService.deleteById(existingReceipt.getReceiptID());
    }

    @PutMapping("/receipts/{id}")
    public Receipt updateReceipt(@RequestBody Receipt receipt, @PathVariable String id) {
        Receipt existingReceipt = receiptService.findById(receipt.getReceiptID());
        if (existingReceipt == null) {
            throw new RuntimeException("Receipts not found");
        }
        existingReceipt.setProduct(receipt.getProduct());
        existingReceipt.setQuantity(receipt.getQuantity());
        return receiptService.save(existingReceipt);
    }

}