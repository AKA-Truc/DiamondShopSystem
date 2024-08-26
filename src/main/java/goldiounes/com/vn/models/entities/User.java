package goldiounes.com.vn.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
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

    @Column(name = "Gender")
    private String Gender;

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
        //Constructor
    }

    public User(String userName, String password, String email, String address, String role, String gender) {
        UserName = userName;
        Password = password;
        Email = email;
        Address = address;
        Role = role;
        Gender = gender;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().toUpperCase()));
        return authorityList;
    }
}