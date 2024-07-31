package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Order;
import goldiounes.com.vn.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    public List<Order> findAll() {
        return orderRepo.findAll();
    }
    public Order findById(int id) {
        return orderRepo.findById(id).get();
    }
    public Order save(Order order) {
        return orderRepo.save(order);
    }
    public void deleteById(int id) {
        orderRepo.deleteById(id);
    }
}
