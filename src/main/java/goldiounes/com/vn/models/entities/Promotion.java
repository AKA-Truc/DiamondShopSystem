package goldiounes.com.vn.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "PROMOTIONS")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "PromotionID")
    private int PromotionID;

    @Column(name = "PromotionName", nullable = false)
    private String PromotionName;

    @Column(name = "Description", nullable = false)
    private String Description;

    @Column(name = "StartDate", nullable = false)
    private Date StartDate;

    @Column(name = "EndDate", nullable = false)
    private Date EndDate;

    @Column(name = "DiscountPercent", nullable = false)
    private int DiscountPercent;

    @OneToMany(mappedBy = "Promotion")
    private List<Order> Orders;

    public Promotion() {
        //cstor
    }

    public Promotion(String promotionName, String description, Date startDate, Date endDate, int discountPercent) {
        this.PromotionName = promotionName;
        this.Description = description;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.DiscountPercent = discountPercent;
    }

    public int getPromotionID() {
        return PromotionID;
    }

    public void setPromotionID(int promotionID) {
        PromotionID = promotionID;
    }

    public String getPromotionName() {
        return PromotionName;
    }

    public void setPromotionName(String promotionName) {
        PromotionName = promotionName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public int getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        DiscountPercent = discountPercent;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }
}
