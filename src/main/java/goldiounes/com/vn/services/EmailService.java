package goldiounes.com.vn.services;

import goldiounes.com.vn.models.entities.Order;
import goldiounes.com.vn.models.entities.OrderDetail;
import goldiounes.com.vn.models.entities.ProductDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    public static String generateInvoiceHTML(Order order) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head><style>");
        sb.append("body { font-family: Arial, sans-serif; color: #333; background-color: #f5f5f5; padding: 10px; }");
        sb.append("h1 { text-align: center; color: #0a113b; }");
        sb.append("p { margin: 5px 0; font-size: 14px; }");
        sb.append(".invoice-container { background-color: #fff; padding: 15px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
        sb.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        sb.append("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; font-size: 14px; }");
        sb.append("th { background-color: #0a113b; color: #fff; text-align: center; }");
        sb.append("tfoot td { font-weight: bold; }");
        sb.append("tfoot td:last-child { color: red; font-size: 16px; }");
        sb.append("tfoot tr:first-child td { border-top: 2px solid #0a113b; }");
        sb.append("tfoot tr:last-child td { border-bottom: 2px solid #0a113b; }");
        sb.append("tfoot tr { background-color: #f2f2f2; }");
        sb.append(".payment-method { background-color: #0a113b; color: #fff; padding: 10px; border-radius: 4px; margin-top: 10px; text-align: center; font-size: 14px; }");
        sb.append("</style></head><body>");

        sb.append("<div class='invoice-container'>");
        sb.append("<h1>Chi Tiết Hóa Đơn</h1>");
        sb.append("<p><strong>Cửa hàng:</strong> Goldiounes Diamond Shop</p>");
        sb.append("<p><strong>Mã hóa đơn:</strong> ").append(order.getOrderID()).append("</p>");
        sb.append("<p><strong>Thời gian:</strong> ").append(order.getStartDate()).append("</p>");
        sb.append("<p><strong>Khách hàng:</strong> ").append(order.getUser().getUserName()).append("</p>");

        sb.append("<table>");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th>Tên</th>");
        sb.append("<th>S.L</th>");
        sb.append("<th>Đ.Giá</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");

        List<OrderDetail> orderDetails = order.getOrderDetails();
        int total = 0;
        for (OrderDetail detail : orderDetails) {
            double sellingPrice = 0;
            for (ProductDetail productDetail : detail.getProduct().getProductDetails()) {
                if (productDetail.getProduct().getProductName().equals(detail.getProduct().getProductName())) {
                    sellingPrice = productDetail.getSellingPrice() * detail.getQuantity();
                    total = (int) (total + sellingPrice);
                    break;
                }
            }

            sb.append("<tr>");
            sb.append("<td>").append(detail.getProduct().getProductName()).append("</td>");
            sb.append("<td style='text-align: center;'>").append(detail.getQuantity()).append("</td>");
            sb.append("<td>").append(String.format("%,.0f", sellingPrice)).append("</td>");
            sb.append("</tr>");
        }

        int totalPromotion = total*order.getPromotion().getDiscountPercent()/100;
        sb.append("</tbody>");
        sb.append("<tfoot>");
        sb.append("<tr><td colspan='2'>Tổng:</td><td>").append(String.format("%,d", total)).append(" VND</td></tr>");
        sb.append("<tr><td colspan='2'>Chiết khấu hóa đơn:</td><td>").append(String.format("%,d", totalPromotion)).append(" VND</td></tr>");
        sb.append("<tr><td colspan='2'>Thành tiền:</td><td>").append(String.format("%,d", total - totalPromotion)).append(" VND</td></tr>");
        sb.append("</tfoot>");
        sb.append("</table>");

        sb.append("<div class='payment-method'>Loại TT: Ví MOMO</div>");
        sb.append("</div>");

        sb.append("</body></html>");

        return sb.toString();
    }




    public void sendInvoiceEmail(String to, Order order) {
        String invoiceHtml = generateInvoiceHTML(order);

        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject("Invoice for Your Order #" + order.getOrderID());

            helper.setText(invoiceHtml, true);

            emailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
