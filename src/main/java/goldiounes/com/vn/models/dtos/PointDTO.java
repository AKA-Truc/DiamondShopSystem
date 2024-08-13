package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
public class PointDTO {
    private int PointID;
    private int Points;

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
}

