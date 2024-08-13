package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.ReceiptDTO;
import goldiounes.com.vn.services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receipt-management")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/receipts")
    public ReceiptDTO createReceipt(@RequestBody ReceiptDTO receipt) {
        return receiptService.createReceipt(receipt);
    }

    @GetMapping("/receipts/{id}")
    public ReceiptDTO getReceipt(@PathVariable int id) {
        return receiptService.getReceipt(id);
    }

    @GetMapping("/receipts")
    public List<ReceiptDTO> getAllReceipts() {
        return receiptService.getAllReceipts();
    }

    @PutMapping("/receipts/{id}")
    public ReceiptDTO updateReceipt(@PathVariable int id, @RequestBody ReceiptDTO receiptDTO) {
        return receiptService.updateReceipt(id, receiptDTO);
    }

    @DeleteMapping("/receipts/{id}")
    public void deleteReceipt(@PathVariable int id) {
        receiptService.deleteReceipt(id);
    }
}
