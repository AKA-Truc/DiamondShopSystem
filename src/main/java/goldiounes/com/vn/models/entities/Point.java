package goldiounes.com.vn.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "POINTS")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PointID")
    private int PointID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    @JsonBackReference
    private User User;

    @Column(name = "Points", nullable = false)
    private int Points;

    public Point() {
        //cstor
    }

    public Point(int Points, User User) {
        this.Points = Points;
        this.User = User;
    }

    public int getPointID() {
        return PointID;
    }

    public void setPointID(int pointID) {
        PointID = pointID;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }
}
