package goldiounes.com.vn.models.dto;

import lombok.Data;

@Data
public class DiamondDetailDTO {
    private int diamondDetailID;
    private DiamondDTO diamond;
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
