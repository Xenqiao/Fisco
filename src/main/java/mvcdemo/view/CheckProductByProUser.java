package mvcdemo.view;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.service.Cleaner;
import mvcdemo.util.contractRealize.GetBcosSDK;
import mvcdemo.util.toolcontract.Product;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Xenqiao
 * @create 2023/3/25 21:58
 */
public class CheckProductByProUser {

    public CheckProductByProUser(ProUserDO proUserDO){
        Cleaner.Clean();
        ProductDO productDO = new ProductDO();
        Connection conn = DBUtil.getConn();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from product WHERE pmaker="+"'"+proUserDO.getUserName()+"' ;");
            System.out.println("===================================================================================== 分割线 === 分割线 ===============================================================================================================");

            while (rs.next()){
                productDO.setProductHash(rs.getString("phash"));
                productDO.setProductName(rs.getString("pname"));
                Product product = new Product(productDO.getProductHash(), GetBcosSDK.getClient(), GetBcosSDK.getKeyPair());

                productDO.setProductPrice(product.getProduct(productDO.getProductHash()).getValue3().intValue());
                productDO.setProductPlace(product.getProduct(productDO.getProductHash()).getValue4());
                productDO.setProductMake(product.getProduct(productDO.getProductHash()).getValue5());
                productDO.setProductId(product.getProduct(productDO.getProductHash()).getValue6().intValue());
                productDO.setProductMakePhone(proUserDO.getProPhone());


                System.out.println("商品哈希："+ productDO.getProductHash());
                Cleaner.PrintProduct(productDO);
                System.out.println();
                System.out.println();
            }


            conn.close();
            stmt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (ContractException e) {
            e.printStackTrace();
        }
    }


}
