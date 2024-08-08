package goldiounes.com.vn.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
public class PointDTO {
    private int PointID;
    private int Points;
    @JsonIgnoreProperties("users")
    private UserDTO user;

    //constructor
    public PointDTO() {

    }
    public PointDTO(int PointID, int Points, UserDTO user) {
        this.PointID = PointID;
        this.Points = Points;
        this.user = user;
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

