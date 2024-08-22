package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.OrderDetailDTO;
import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.dtos.ProductDetailDTO;
import goldiounes.com.vn.models.entities.Order;
import goldiounes.com.vn.models.entities.OrderDetail;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.models.entities.ProductDetail;
import goldiounes.com.vn.repositories.OrderDetailRepo;
import goldiounes.com.vn.repositories.OrderRepo;
import goldiounes.com.vn.repositories.ProductDetailRepo;
import goldiounes.com.vn.repositories.ProductRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductDetailRepo productDetailRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrderDetailDTO> findByOrderId(int orderId) {
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId(orderId);
        if (orderDetails.isEmpty()) {
            throw new RuntimeException("No order found with order id " + orderId);
        }
        return orderDetails.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class))
                .collect(Collectors.toList());
    }

    public List<OrderDetailDTO> findAll() {
        List<OrderDetail> orderDetails = orderDetailRepo.findAll();
        if (orderDetails.isEmpty()) {
            throw new RuntimeException("No order found");
        }
        return orderDetails.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDetailDTO findById(int id) {
        OrderDetail orderDetail = orderDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found"));
        return modelMapper.map(orderDetail, OrderDetailDTO.class);
    }

    public OrderDetailDTO save(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);

        Order existingOrder = orderRepo.findById(orderDetail.getOrder().getOrderID())
                .orElseThrow(() -> new RuntimeException("Order not found"));


        Product existingProduct = productRepo.findById(orderDetail.getProduct().getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        orderDetail.setSize(0);
        orderDetail.setOrder(existingOrder);
        orderDetail.setProduct(existingProduct);
        OrderDetail savedOrderDetail = orderDetailRepo.save(orderDetail);

        return modelMapper.map(savedOrderDetail, OrderDetailDTO.class);
    }

    public boolean deleteById(int id) {
        OrderDetail orderDetail = orderDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found"));
        Product product = productRepo.findById(orderDetail.getProduct().getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        ProductDTO indexProduct = modelMapper.map(product, ProductDTO.class);
        ProductDetailDTO existingProductDetailsDTO = productDetailService.findById(indexProduct.getProductId());
        ProductDetail existingProductDetails = modelMapper.map(existingProductDetailsDTO, ProductDetail.class);
        if (existingProductDetailsDTO == null) {
            throw new RuntimeException("Product detail not found");
        }
        else {
            ProductDetail updateInventory = modelMapper.map(existingProductDetails, ProductDetail.class);
            updateInventory.setInventory(existingProductDetails.getInventory()+orderDetail.getQuantity());
            productDetailRepo.save(updateInventory);
        }
        orderDetailRepo.deleteById(id);
        return true;
    }

    public OrderDetailDTO update(int id, OrderDetailDTO orderDetailDTO) {
        OrderDetail existingOrderDetail = orderDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found"));

        existingOrderDetail.setSize(orderDetailDTO.getSize());

        ProductDetailDTO productDetailDTO = productDetailService.getProductDetailBySize(
                orderDetailDTO.getSize(),
                modelMapper.map(existingOrderDetail.getProduct(), ProductDTO.class)
        );

        ProductDetail updateInventory = modelMapper.map(productDetailDTO, ProductDetail.class);
        if(updateInventory.getInventory() >= existingOrderDetail.getQuantity()) {
            updateInventory.setInventory(updateInventory.getInventory() - existingOrderDetail.getQuantity());
            productDetailRepo.save(updateInventory);
        } else {
            throw new RuntimeException("Not enough inventory");
        }

        OrderDetail savedOrderDetail = orderDetailRepo.save(existingOrderDetail);
        return modelMapper.map(savedOrderDetail, OrderDetailDTO.class);
    }
    public List<OrderDetailDTO> findByProductId(int productId) {
        List<OrderDetail> orderDetails = orderDetailRepo.findByProductId(productId);
        if (orderDetails.isEmpty()) {
            throw new RuntimeException("No order found with product id " + productId);
        }
        return modelMapper.map(orderDetails, new TypeToken<List<OrderDetailDTO>>() {}.getType());
    }
}
