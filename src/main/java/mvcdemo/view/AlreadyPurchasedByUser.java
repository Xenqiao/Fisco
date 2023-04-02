package mvcdemo.view;

import mvcdemo.dao.mysql.impl.MysqlServiceImpl;
import mvcdemo.po.GetUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.service.Cleaner;

import javax.swing.*;
import java.util.Scanner;

/**
 * @author Xenqiao
 * @create 2023/4/2 11:33
 */
public class AlreadyPurchasedByUser {
    ProductDO productDO = new ProductDO();

    public void CheckingAlreadyPurchased(){
        Cleaner.Clean();
        System.out.println("===================================================================================== 分割线 == 分割线 ================================================================================================================");


        String userAlreadyPurchased = GetUserDO.getAlreadyPurchased();
        String productID = "";
        if(userAlreadyPurchased != null && !"".equals(userAlreadyPurchased)) {
            System.out.println();
            System.out.println("                                                                        以下是您曾经购买过的产品,您可通过此复制产品哈希：");
            System.out.println("                                                                        以下是您曾经购买过的产品,您可通过此复制产品哈希：");
            for (int i = 0; i < userAlreadyPurchased.length(); i++) {
                if (userAlreadyPurchased.charAt(i) >= 48 && userAlreadyPurchased.charAt(i) <= 57) {
                    productID += userAlreadyPurchased.charAt(i);

                }else if (productID != null && !"".equals(productID)){
                    StringBuilder sql = new StringBuilder();
                    sql.append("select * from product where pid="+"'"+productID+"' ;");
                    new MysqlServiceImpl().PrintProduct(productDO,sql.toString(),1);
                    productID = "";
                }
            }

        }else{
            System.out.println();
            System.out.println("                                                                        您未曾购买任何产品");
            System.out.println("                                                                        您未曾购买任何产品");
        }
        Cleaner.BackMain();
    }

    public void VerificationOfAuthenticity(){
        Cleaner.Clean();

        System.out.print("                                              请输入您要查询的商品哈希：");
        Scanner sc = new Scanner(System.in);
        String hash = sc.next();

        StringBuilder sql = new StringBuilder();
        sql.append("select * from product where phash="+"'"+hash+"' ;");
        boolean number = true;
        number = new MysqlServiceImpl().PrintProduct(productDO,sql.toString(),1);

        if (number == false) {
            System.out.println("                未查询到该商品信息");
            Cleaner.BackMain();
            return;
        }else {
            JOptionPane.showMessageDialog(null, "正品！品质有保障");
            Cleaner.BackMain();
        }
    }
}
