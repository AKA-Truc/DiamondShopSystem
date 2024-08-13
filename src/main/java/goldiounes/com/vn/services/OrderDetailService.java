package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.OrderDetailDTO;
import goldiounes.com.vn.models.entities.Order;
import goldiounes.com.vn.models.entities.OrderDetail;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.repositories.OrderDetailRepo;
import goldiounes.com.vn.repositories.OrderRepo;
import goldiounes.com.vn.repositories.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrderDetailDTO> findByOrderId(int orderId) {
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId(orderId);
        return orderDetails.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class))
                .collect(Collectors.toList());
    }

    public List<OrderDetailDTO> findAll() {
        List<OrderDetail> orderDetails = orderDetailRepo.findAll();
        return orderDetails.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDetailDTO findById(int id) {
        OrderDetail orderDetail = orderDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found"));
        return modelMapper.map(orderDetail, OrderDetailDTO.class);
    }

    public OrderDetailDTO save(OrderDetail orderDetail,int orderID) {
        Order existingOrder = orderRepo.findById(orderID)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderDetail.setOrder(existingOrder);
        Product existingProduct = productRepo.findById(orderDetail.getProduct().getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        orderDetail.setProduct(existingProduct);
        OrderDetail savedOrderDetail = orderDetailRepo.save(orderDetail);
        return modelMapper.map(savedOrderDetail, OrderDetailDTO.class);
    }

    public void deleteById(int id) {
        if (!orderDetailRepo.existsById(id)) {
            throw new RuntimeException("OrderDetail not found");
        }
        orderDetailRepo.deleteById(id);
    }

    public OrderDetailDTO update(int id, OrderDetail orderDetail) {
        OrderDetail existingOderDetail = orderDetailRepo.findById(orderDetail.getOrderDetailID()).orElseThrow(() -> new RuntimeException("OrderDetail not found"));
        existingOderDetail.setOrderDetailID(orderDetail.getOrderDetailID());
        existingOderDetail.setOrder(orderDetail.getOrder());
        existingOderDetail.setQuantity(orderDetail.getQuantity());
        existingOderDetail.setProduct(orderDetail.getProduct());
        OrderDetail savedOrderDetail = orderDetailRepo.save(existingOderDetail);
        return modelMapper.map(savedOrderDetail, OrderDetailDTO.class);
    }
}
