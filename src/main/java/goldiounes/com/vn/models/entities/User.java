package goldiounes.com.vn.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
//Pojo : Plain Old Java Object
@Data
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserID")
    private int UserID;

    @Column(name = "UserName", nullable = false)
    private String UserName;

    @Column(name = "Password", nullable = false)
    private String Password;

    @Column(name = "Email", nullable = false)
    private String Email;

    @Column(name = "Address")
    private String Address;

    @Column(name = "Role", nullable = false)
    private String Role;

    @OneToMany(mappedBy = "User")
    @JsonManagedReference
    private List<Warranty> Warranties;

    @OneToMany(mappedBy = "User")
    @JsonManagedReference
    private List<Order> Orders;

    @OneToOne(mappedBy = "User", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Cart cart;

    @OneToOne(mappedBy = "User", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Point point;

    public User() {
        //cstor
    }

    public User(String userName, String password, String email, String address, String role) {
        UserName = userName;
        Password = password;
        Email = email;
        Address = address;
        Role = role;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public List<Warranty> getWarranties() {
        return Warranties;
    }

    public void setWarranties(List<Warranty> warranties) {
        Warranties = warranties;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
