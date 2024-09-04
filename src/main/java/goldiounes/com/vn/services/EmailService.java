package goldiounes.com.vn.services;

import goldiounes.com.vn.models.entities.Order;
import goldiounes.com.vn.models.entities.OrderDetail;
import goldiounes.com.vn.models.entities.ProductDetail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSizeSelectionEmail(String to) {
        String url = "http://localhost:8080/size-selection.html";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Please select your ring size");
        message.setText("Thank you for the order. Below is a guide to measuring the size of all kinds of jewellery. After selecting the size, please return to our website: " + url + "\nGo to your order to re-select the size for the ordered products");
        emailSender.send(message);
    }

    public static String generateInvoiceText(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hóa đơn mua hàng\n");
        sb.append("Mã đơn hàng: ").append(order.getOrderID()).append("\n");
        sb.append("Tên khách hàng: ").append(order.getUser().getUserName()).append("\n");
        sb.append("Ngày đặt hàng: ").append(order.getStartDate()).append("\n\n");

        // Tiêu đề bảng
        sb.append(String.format("%-50s %-23s %-20s\n", "Tên sản phẩm", "Số lượng", "Giá"));

        // Nội dung bảng
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for (OrderDetail detail : orderDetails) {
            double sellingPrice = 0;
            for (ProductDetail productDetail : detail.getProduct().getProductDetails()) {
                if (productDetail.getProduct().getProductName().equals(detail.getProduct().getProductName())) {
                    sellingPrice = productDetail.getSellingPrice();
                    break;
                }
            }

            sb.append(String.format("%-40s %-15d %-20s\n",
                    detail.getProduct().getProductName(),
                    detail.getQuantity(),
                    String.format("%,.2f", sellingPrice)));
        }

        // Tổng cộng
        sb.append("\nTổng cộng: ").append(String.format("%,d", order.getTotalPrice())).append(" VND");

        return sb.toString();
    }


    public void sendInvoiceEmail(String to, Order order) {
        String invoiceText = generateInvoiceText(order); // Generate the invoice textcommi

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Invoice for Your Order #" + order.getOrderID());
        message.setText(invoiceText);
        emailSender.send(message);
    }

}
