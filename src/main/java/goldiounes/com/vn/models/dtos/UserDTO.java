package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private int UserID;
    private String UserName;
    private String Password;
    private String Email;
    private String Address;
    private String Role;


    @JsonManagedReference
    private List<WarrantyDTO> warranties;

    @JsonManagedReference
    private List<OrderDTO> orders;

    @JsonBackReference
    private PointDTO point;

    @JsonBackReference
    private CartDTO cart;

    public UserDTO(){
        //default constructor
    }

    public UserDTO(int userID, String userName, String password, String email, String role, String address) {
        UserID = userID;
        UserName = userName;
        Password = password;
        Email = email;
        Role = role;
        Address = address;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
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

    public List<WarrantyDTO> getWarranties() {
        return warranties;
    }

    public void setWarranties(List<WarrantyDTO> warranties) {
        this.warranties = warranties;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }



    public PointDTO getPoint() {
        return point;
    }

    public void setPoint(PointDTO point) {
        this.point = point;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }
}