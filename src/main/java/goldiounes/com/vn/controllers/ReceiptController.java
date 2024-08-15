package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.ReceiptDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receipt-management")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/receipts")
    public ResponseEntity<ResponseWrapper<ReceiptDTO>> createReceipt(@RequestBody ReceiptDTO receipt) {
        ReceiptDTO createdReceipt = receiptService.createReceipt(receipt);
        ResponseWrapper<ReceiptDTO> response = new ResponseWrapper<>("Receipt created successfully", createdReceipt);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/receipts/{id}")
    public ResponseEntity<ResponseWrapper<ReceiptDTO>> getReceipt(@PathVariable int id) {
        ReceiptDTO receipt = receiptService.getReceipt(id);
        if (receipt != null) {
            ResponseWrapper<ReceiptDTO> response = new ResponseWrapper<>("Receipt retrieved successfully", receipt);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<ReceiptDTO> response = new ResponseWrapper<>("Receipt not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/receipts")
    public ResponseEntity<ResponseWrapper<List<ReceiptDTO>>> getAllReceipts() {
        List<ReceiptDTO> receipts = receiptService.getAllReceipts();
        ResponseWrapper<List<ReceiptDTO>> response = new ResponseWrapper<>("Receipts retrieved successfully", receipts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/receipts/{id}")
    public ResponseEntity<ResponseWrapper<ReceiptDTO>> updateReceipt(@PathVariable int id, @RequestBody ReceiptDTO receiptDTO) {
        ReceiptDTO updatedReceipt = receiptService.updateReceipt(id, receiptDTO);
        if (updatedReceipt != null) {
            ResponseWrapper<ReceiptDTO> response = new ResponseWrapper<>("Receipt updated successfully", updatedReceipt);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<ReceiptDTO> response = new ResponseWrapper<>("Receipt not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/receipts/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteReceipt(@PathVariable int id) {
        boolean isDeleted = receiptService.deleteReceipt(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("Receipt deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Receipt not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
