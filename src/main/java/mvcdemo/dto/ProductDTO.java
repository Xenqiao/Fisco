package mvcdemo.dto;

/**
 * @author Xenqiao
 * @create 2023/3/20 20:54
 * 产品对象，记录了产品的各种参数
 */
public class ProductDTO {
    private String productName;
    private Integer productId;
    private String productHash;
    private String productPlace;
    private Integer productPrice;
    private String productMake;
    private String productMakePhone;
    private String productConner;
    private String productClass;
    private String maker;

    @Override
    public String toString() {
        return "ProductDO{" +
                "productName='" + productName + '\'' +
                ", productId=" + productId +
                ", productHash='" + productHash + '\'' +
                ", productPlace='" + productPlace + '\'' +
                ", productPrice=" + productPrice +
                ", productMake='" + productMake + '\'' +
                ", productMakePhone='" + productMakePhone + '\'' +
                ", productConner='" + productConner + '\'' +
                ", productClass='" + productClass + '\'' +
                ", maker='" + maker + '\'' +
                '}';
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getProductConner() {
        return productConner;
    }

    public void setProductConner(String productConner) {
        this.productConner = productConner;
    }


    public String getProductMakePhone() {
        return productMakePhone;
    }

    public void setProductMakePhone(String productMakePhone) {
        this.productMakePhone = productMakePhone;
    }

    public String getProductMake() {
        return productMake;
    }

    public void setProductMake(String productMake) {
        this.productMake = productMake;
    }

    public String getProductPlace() {
        return productPlace;
    }

    public void setProductPlace(String productPlace) {
        this.productPlace = productPlace;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductHash() {
        return productHash;
    }

    public void setProductHash(String productHash) {
        this.productHash = productHash;
    }
}
