package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Receipt;
import goldiounes.com.vn.repositories.ReceiptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepo receiptRepo;

    public List<Receipt> findAll() {
        return receiptRepo.findAll();
    }
    public Receipt findById(int id) {
        return receiptRepo.findById(id).get();
    }
    public Receipt save(Receipt receipt) {
        return receiptRepo.save(receipt);
    }
    public void deleteById(int id) {
        receiptRepo.deleteById(id);
    }
}
