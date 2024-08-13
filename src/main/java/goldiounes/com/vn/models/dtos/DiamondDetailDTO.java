package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class DiamondDetailDTO {
    private int diamondDetailID;

    @JsonBackReference
    private DiamondDTO diamond;

    @JsonBackReference
    private ProductDetailDTO productDetail;
    private int quantity;



    public int getDiamondDetailID() {
        return diamondDetailID;
    }

    public void setDiamondDetailID(int diamondDetailID) {
        this.diamondDetailID = diamondDetailID;
    }

    public DiamondDTO getDiamond() {
        return diamond;
    }

    public void setDiamond(DiamondDTO diamond) {
        this.diamond = diamond;
    }

    public ProductDetailDTO getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetailDTO productDetail) {
        this.productDetail = productDetail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
