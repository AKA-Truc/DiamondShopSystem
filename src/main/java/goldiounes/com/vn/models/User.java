package goldiounes.com.vn.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
//Pojo : Plain Old Java Object
@Data
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "UserID")
    private int UserID;

    @Column(name = "UserName", nullable = false, unique = false)
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
    private List<Warranty> Warranties;

    @OneToMany(mappedBy = "User")
    private List<Order> Orders;

    @OneToOne(mappedBy = "User")
    private Cart Cart;

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

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public List<Warranty> getWarranties() { return Warranties; }

    public void setWarranties(List<Warranty> warranties) {
        Warranties = warranties;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }

    public Cart getCart() { return Cart; }

    public void setCart(Cart cart) { Cart = cart; }
}
