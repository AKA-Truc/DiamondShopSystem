package goldiounes.com.vn.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class ProductDTO {

    private int productID;
    private String productName;
    private String imageURL;
    private double markupRate;
    private double laborCost;
    private double sellingPrice;
    private double warrantyPeriod;
    private int inventory;

    @JsonIgnoreProperties("products")
    private CategoryDTO category;

    @JsonIgnoreProperties("products")
    private List<ProductDetailDTO> ProductDetails;

    @JsonIgnoreProperties("products")
    private List<CartItemDTO> CartItems;

    @JsonIgnoreProperties("products")
    private List<OrderDetailDTO> OrderDetails;

    @JsonIgnoreProperties("products")
    private List<ReceiptDTO> Receipt;


    public ProductDTO() {
        //default
    }

    public ProductDTO(int productID, String productName, String imageURL, double markupRate, double laborCost, double sellingPrice, double warrantyPeriod, int inventory, CategoryDTO category) {
        this.productID = productID;
        this.productName = productName;
        this.imageURL = imageURL;
        this.markupRate = markupRate;
        this.laborCost = laborCost;
        this.sellingPrice = sellingPrice;
        this.warrantyPeriod = warrantyPeriod;
        this.inventory = inventory;
        this.category = category;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getMarkupRate() {
        return markupRate;
    }

    public void setMarkupRate(double markupRate) {
        this.markupRate = markupRate;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(double warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public List<ProductDetailDTO> getProductDetails() {
        return ProductDetails;
    }

    public void setProductDetails(List<ProductDetailDTO> productDetails) {
        ProductDetails = productDetails;
    }

    public List<CartItemDTO> getCartItems() {
        return CartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        CartItems = cartItems;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        OrderDetails = orderDetails;
    }

    public List<ReceiptDTO> getReceipt() {
        return Receipt;
    }

    public void setReceipt(List<ReceiptDTO> receipt) {
        Receipt = receipt;
    }
}
