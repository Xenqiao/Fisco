package mvcdemo.view;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.dao.mysql.impl.UserService;
import mvcdemo.dao.mysql.impl.UserServiceImpl;
import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.po.UserDO;
import mvcdemo.service.Cleaner;
import mvcdemo.util.toolcontract.Erc20;
import mvcdemo.util.toolcontract.Product;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.swing.*;
import java.math.BigInteger;
import java.sql.*;
import java.util.Scanner;

/**
 * @author Xenqiao
 * @create 2023/3/27 12:28
 */
public class CheckProductByUser {
    String configFile = "src/main/resources/config.toml";
    BcosSDK sdk = BcosSDK.build(configFile);
    Client client = sdk.getClient(1);
    CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
    ProductDO productDO = new ProductDO();

    private UserDO userDO;
    public CheckProductByUser(UserDO userDO) {
        this.userDO = userDO;
    }
    Connection conn = DBUtil.getConn();

    public void CheckProduct(){
        new Cleaner();


        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from product;");
            System.out.println("===================================================================================== 分割线 == 分割线 ================================================================================================================");

            while (rs.next()){
                productDO.setProductHash(rs.getString("phash"));
                productDO.setProductName(rs.getString("pname"));
                Product product = new Product(productDO.getProductHash(),client,keyPair);

                productDO.setProductPrice(product.getProduct(productDO.getProductHash()).getValue3().intValue());
                productDO.setProductPlace(product.getProduct(productDO.getProductHash()).getValue4());
                productDO.setProductMake(product.getProduct(productDO.getProductHash()).getValue5());
                productDO.setProductId(product.getProduct(productDO.getProductHash()).getValue6().intValue());
                productDO.setProductMakePhone(rs.getString("pphone"));

                System.out.println();
                System.out.println("商品序号："+ productDO.getProductId());
                System.out.println("商品名称："+ productDO.getProductName());
                System.out.println("商品价格："+ productDO.getProductPrice()+"  ETH");
                System.out.println("产品委托商："+ productDO.getProductMake());
                System.out.println("产品委托商联系电话："+ productDO.getProductMakePhone());
                System.out.println("产品发货地址："+ productDO.getProductPlace());
                System.out.println();
                System.out.println();
            }

            stmt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (ContractException e) {
            e.printStackTrace();
        }

        System.out.print("                                        请选择输入您要进行的操作（ a.查询产品信息      b.购买产品      c.返回主菜单 ）：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);
        switch (select){
            case 'a':
                new CheckProductByUser(userDO).lookingForProduct();
                break;
            case 'b':
                new CheckProductByUser(userDO).purchaseProducts();
                break;
            default:
                JOptionPane.showMessageDialog(null,"接下来将返回主菜单！");
                break;
        }
    }



    /** 查询产品的函数 */
    public void lookingForProduct(){
        System.out.print("                                        请选择输入您要查询的商品名称：");
        Scanner sc = new Scanner(System.in);
        String select = sc.next();
        System.out.println("你写的啥？"+select);
        new Cleaner();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from product;");
            System.out.println("===================================================================================== 分割线 == 分割线 ================================================================================================================");

            while (rs.next()){

                productDO.setProductHash(rs.getString("phash"));
                productDO.setProductName(rs.getString("pname"));
                Product product = new Product(productDO.getProductHash(),client,keyPair);

                productDO.setProductPrice(product.getProduct(productDO.getProductHash()).getValue3().intValue());
                productDO.setProductPlace(product.getProduct(productDO.getProductHash()).getValue4());
                productDO.setProductMake(product.getProduct(productDO.getProductHash()).getValue5());
                productDO.setProductId(product.getProduct(productDO.getProductHash()).getValue6().intValue());
                productDO.setProductMakePhone(rs.getString("pphone"));

                System.out.println();
                System.out.println("商品序号："+ productDO.getProductId());
                System.out.println("商品名称："+ productDO.getProductName());
                System.out.println("商品价格："+ productDO.getProductPrice()+"  ETH");
                System.out.println("产品委托商："+ productDO.getProductMake());
                System.out.println("产品委托商联系电话："+ productDO.getProductMakePhone());
                System.out.println("产品发货地址："+ productDO.getProductPlace());
                System.out.println();
                System.out.println();
            }


            stmt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (ContractException e) {
            e.printStackTrace();
        }
    }


    /**  b.负责购买产品的函数  */
    public void purchaseProducts(){
        System.out.print("                                        请选择输入您要购买的商品序号：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);

        productDO.setProductId(Integer.valueOf(String.valueOf(select)));
        ProUserDO proUserDO = new ProUserDO();


        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from product WHERE pid="+"'"+productDO.getProductId()+"' ;");

            while (rs.next()){
                if (rs != null){
                    productDO.setProductName(rs.getString("pname"));
                    productDO.setProductHash(rs.getString("phash"));
                    productDO.setProductMake(rs.getString("pmaker"));
                    productDO.setProductMakePhone(rs.getString("pphone"));
                    productDO.setProductPrice(Integer.valueOf(rs.getString("pprice")));
                }else {
                    JOptionPane.showMessageDialog(null, "该序号不存在！");
                    return;
                }
            }

            rs = stmt.executeQuery("select * from producer where id="+"'"+productDO.getProductMake()+"' ;");
            while (rs.next()){
                proUserDO.setUserName(rs.getString("id"));
                proUserDO.setHash(rs.getString("hash"));
            }
            rs.close();
            stmt.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //在合约上完成的转账操作
        String contractAddress = "0x3fb7e04e2ff1a5796ff62348c6dc2b05f684118c";
        Erc20 erc20 = new Erc20(contractAddress, client, keyPair);

        try {
            userDO.setBalance(Integer.valueOf(erc20.balanceOf(userDO.getHash()).toString()));
            if (userDO.getBalance() < productDO.getProductPrice()){

                JOptionPane.showMessageDialog(null, "余额不足！");
                return;
            }
            //转账操作
            erc20.transferFrom(userDO.getHash(),proUserDO.getHash(), BigInteger.valueOf(productDO.getProductPrice()));
            userDO.setBalance(Integer.valueOf(erc20.balanceOf(userDO.getHash()).toString()));

            System.out.println("购买成功！商品哈希为："+productDO.getProductHash());
            System.out.println("您的账户余额为："+userDO.getBalance()+"   ETH");
        } catch (ContractException e) {
            e.printStackTrace();
        }


        StringBuilder stringBuilder = new StringBuilder();
        if (userDO.getAlreadyPurchased()==null){
            userDO.setAlreadyPurchased("0");
        }
        stringBuilder.append(userDO.getAlreadyPurchased()+","+productDO.getProductId());
        userDO.setAlreadyPurchased(stringBuilder.toString());
        //把余额数据上传至MySQL

        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" update producer set " +
                    " proBalance="+"'"+proUserDO.getBalance()+"' "+
                    " where id="+"'"+productDO.getProductMake()+"' ;");
            
            System.out.println(sql.toString());

            PreparedStatement ps = conn.prepareStatement(sql.toString());

            new UserServiceImpl().add(userDO);

            ps.close();
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println();
        System.out.print("                                        输入任意键返回主菜单(请勿直接回车)：");
        sc = new Scanner(System.in);
        select =sc.next().charAt(0);
    }


}
