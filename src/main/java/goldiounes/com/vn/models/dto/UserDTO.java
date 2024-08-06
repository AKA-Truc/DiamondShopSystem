package goldiounes.com.vn.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class UserDTO {
    private int UserID;
    private String UserName;
    private String Password;
    private String Email;
    private String Address;
    private String Role;

    @JsonIgnoreProperties("users")
    private List<WarrantyDTO> Warranties;

    @JsonIgnoreProperties("users")
    private List<OrderDTO> Orders;

    @JsonIgnoreProperties("users")
    private List<ProductDTO> Products;

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

    public List<OrderDTO> getOrders() {
        return Orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        Orders = orders;
    }

    public List<WarrantyDTO> getWarranties() {
        return Warranties;
    }

    public void setWarranties(List<WarrantyDTO> warranties) {
        Warranties = warranties;
    }

    public List<ProductDTO> getProducts() {
        return Products;
    }

    public void setProducts(List<ProductDTO> products) {
        Products = products;
    }
}