package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class PointDTO {
    private int PointID;
    private int Points;

    @JsonBackReference
    private UserDTO user;

    //constructor
    public PointDTO() {

    }
    public PointDTO(int PointID, int Points) {
        this.PointID = PointID;
        this.Points = Points;
    }

    //getter and setter
    public int getPointID() {
        return PointID;
    }
    public void setPointID(int pointID) {
        PointID = pointID;
    }
    public int getPoints() {
        return Points;
    }
    public void setPoints(int points) {
        Points = points;
    }
    public UserDTO getUser() {
        return user;
    }
    public void setUser(UserDTO user) {
        this.user = user;
    }
}

