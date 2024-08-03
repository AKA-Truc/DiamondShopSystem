package goldiounes.com.vn.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "WARRANTIES")
public class Warranty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "WarrantyID")
    private int WarrantyID;

    @ManyToOne()
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "UserID", nullable = false)
    private User User;

    @Column(name = "WarrantyDetails", nullable = false)
    private String WarrantyDetails;

    @Column(name = "StartDate", nullable = false)
    private Date StartDate;

    @Column(name = "EndDate", nullable = false)
    private Date EndDate;

    public Warranty() {
        //cstor
    }

    public Warranty(Product product, User user, String warrantyDetails, Date startDate, Date endDate) {
        this.product = product;
        User = user;
        WarrantyDetails = warrantyDetails;
        StartDate = startDate;
        EndDate = endDate;
    }

    public int getWarrantyID() {
        return WarrantyID;
    }

    public void setWarrantyID(int warrantyID) {
        WarrantyID = warrantyID;
    }

    public goldiounes.com.vn.models.Product getProduct() {
        return product;
    }

    public void setProduct(goldiounes.com.vn.models.Product product) {
        this.product = product;
    }

    public goldiounes.com.vn.models.User getUser() {
        return User;
    }

    public void setUser(goldiounes.com.vn.models.User user) {
        User = user;
    }

    public String getWarrantyDetails() {
        return WarrantyDetails;
    }

    public void setWarrantyDetails(String warrantyDetails) {
        WarrantyDetails = warrantyDetails;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }
}
