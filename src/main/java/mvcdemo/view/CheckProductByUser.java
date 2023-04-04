package mvcdemo.view;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.service.impl.MysqlServiceImpl;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.ProductDTO;
import mvcdemo.dto.UserDTO;
import mvcdemo.util.Cleaner;
import mvcdemo.util.contractRealize.GetBcosSDK;
import mvcdemo.util.toolcontract.Erc20;
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

    ProductDTO productDTO = new ProductDTO();

    private UserDTO userDTO;
    public CheckProductByUser(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
    Connection conn = DBUtil.getConn();

    public void CheckProduct(){
        Cleaner.Clean();

        String sql = "select * from product;";
        new MysqlServiceImpl().PrintProduct(productDTO,sql,0);

        System.out.print("                                        请选择输入您要进行的操作（ a.查询产品      b.购买产品      c.返回主菜单 ）：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);
        switch (select){
            case 'a':
                new CheckProductByUser(userDTO).lookingForProduct();
                break;
            case 'b':
                new CheckProductByUser(userDTO).purchaseProducts();
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
        
        Cleaner.Clean();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from product where pname like"+"'%"+select+"%' ;");
        boolean number = true;
        number = new MysqlServiceImpl().PrintProduct(productDTO,sql.toString(),0);
            if (number == false) {
                System.out.println("没有找到哦，输入一个字进行查找试试？ ");
                Cleaner.BackMain();
                return;
            }
    }


    /**  b.负责购买产品的函数  */
    public void purchaseProducts(){
        System.out.print("                                        请选择输入您要购买的商品序号：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);

        productDTO.setProductId(Integer.valueOf(String.valueOf(select)));
        ProUserDTO proUserDTO = ProUserDTO.getProUserDO();


        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from product WHERE pid="+"'"+ productDTO.getProductId()+"' ;");

            while (rs.next()){
                if (rs != null){
                    productDTO.setProductName(rs.getString("pname"));
                    productDTO.setProductHash(rs.getString("phash"));
                    productDTO.setProductMake(rs.getString("pmaker"));
                    productDTO.setProductMakePhone(rs.getString("pphone"));
                    productDTO.setProductPrice(Integer.valueOf(rs.getString("pprice")));
                }else {
                    JOptionPane.showMessageDialog(null, "该序号不存在！");
                    return;
                }
            }

            rs = stmt.executeQuery("select * from producer where id="+"'"+ productDTO.getProductMake()+"' ;");
            while (rs.next()){
                proUserDTO.setUserName(rs.getString("id"));
                proUserDTO.setHash(rs.getString("hash"));
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //在合约上完成的转账操作
        String contractAddress = "0x3fb7e04e2ff1a5796ff62348c6dc2b05f684118c";
        Erc20 erc20 = new Erc20(contractAddress, GetBcosSDK.theGetBcosSDK().getClient(), GetBcosSDK.theGetBcosSDK().getKeyPair());

        try {
            userDTO.setBalance(Integer.valueOf(erc20.balanceOf(userDTO.getHash()).toString()));
            if (userDTO.getBalance() < productDTO.getProductPrice()){

                JOptionPane.showMessageDialog(null, "余额不足！");
                return;
            }
            //转账操作
            erc20.transferFrom(userDTO.getHash(), proUserDTO.getHash(), BigInteger.valueOf(productDTO.getProductPrice()));
            userDTO.setBalance(Integer.valueOf(erc20.balanceOf(userDTO.getHash()).toString()));

            System.out.println("购买成功！商品哈希为："+ productDTO.getProductHash());
            System.out.println("您的账户余额为："+ userDTO.getBalance()+"   ETH");
        } catch (ContractException e) {
            e.printStackTrace();
        }


        StringBuilder stringBuilder = new StringBuilder();
        if (userDTO.getAlreadyPurchased()==null){
            userDTO.setAlreadyPurchased("0,");
        }
        stringBuilder.append(userDTO.getAlreadyPurchased()+ productDTO.getProductId()+",");
        userDTO.setAlreadyPurchased(stringBuilder.toString());
        proUserDTO.setProAlreadyPurchased(stringBuilder.toString());

        //把余额数据上传至MySQL
        new MysqlServiceImpl().add(userDTO);
        Cleaner.BackMain();
    }


}
