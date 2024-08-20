package goldiounes.com.vn.models.entities;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User User;

    @Column(name = "EndDate", nullable = false)
    private Date EndDate;

    public Warranty() {
        //Constructor
    }

    public Warranty(Product product, User user, Date endDate) {
        this.product = product;
        User = user;
        EndDate = endDate;
    }
}
