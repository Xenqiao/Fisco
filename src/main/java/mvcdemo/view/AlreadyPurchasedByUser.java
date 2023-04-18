package mvcdemo.view;

import mvcdemo.service.impl.MysqlServiceImpl;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.ProductDTO;
import mvcdemo.util.Cleaner;

import javax.swing.*;
import java.util.Scanner;

/**
 * @author Xenqiao
 * @create 2023/4/2 11:33
 */
public class AlreadyPurchasedByUser {
    ProductDTO productDTO = new ProductDTO();
    ProUserDTO proUserDTO = ProUserDTO.getProUserDO();
    /** 展示购买过的产品以及对应哈希值供用户核实 **/
    public void CheckingAlreadyPurchased(){
        Cleaner.getCleaner().Clean();
        System.out.println("===================================================================================== 分割线 == 分割线 ================================================================================================================");


        String userAlreadyPurchased = proUserDTO.getProAlreadyPurchased();
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
                    MysqlServiceImpl.getMysqlService().printProduct(productDTO,sql.toString(),1);
                    productID = "";
                }
            }

        }else{
            System.out.println();
            System.out.println("                                                                        您未曾购买任何产品");
            System.out.println("                                                                        您未曾购买任何产品");
        }
        Cleaner.getCleaner().BackMain();
    }


    /** 查询产品哈希以验证真伪的函数 */
    public void VerificationOfAuthenticity(){
        Cleaner.getCleaner().Clean();

        System.out.print("                                              请输入您要查询的商品哈希：");
        Scanner sc = new Scanner(System.in);
        String hash = sc.next();

        StringBuilder sql = new StringBuilder();
        sql.append("select * from product where phash="+"'"+hash+"' ;");

        boolean number = MysqlServiceImpl.getMysqlService().printProduct(productDTO,sql.toString(),1);

        if (number == false) {
            System.out.println("                未查询到该商品信息");
            Cleaner.getCleaner().BackMain();
            return;
        }else {
            JOptionPane.showMessageDialog(null, "正品！品质有保障");
            Cleaner.getCleaner().BackMain();
        }
    }


}
