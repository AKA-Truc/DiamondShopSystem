package goldiounes.com.vn.services;

import goldiounes.com.vn.models.entity.OrderDetail;
import goldiounes.com.vn.repositories.OrderDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepo orderDetailRepo;

    public List<OrderDetail> findByOrderId(int orderId) {
        return orderDetailRepo.findByOrderId(orderId);
    }
    public List<OrderDetail> findAll() {
        return orderDetailRepo.findAll();
    }
    public OrderDetail findById(int id) {
        return orderDetailRepo.findById(id).get();
    }
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepo.save(orderDetail);
    }
    public void deleteById(int id) {
        orderDetailRepo.deleteById(id);
    }
}
