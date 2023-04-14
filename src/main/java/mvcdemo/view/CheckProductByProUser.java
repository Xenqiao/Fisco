package mvcdemo.view;

import mvcdemo.dao.DBUtil;
import mvcdemo.dao.MysqlDAO;
import mvcdemo.service.impl.MysqlServiceImpl;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.ProductDTO;
import mvcdemo.util.Cleaner;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * @author Xenqiao
 * @create 2023/3/25 21:58
 */
public class CheckProductByProUser {
    ProUserDTO proUserDTO = ProUserDTO.getProUserDO();

    /** 生产者查看自己的产品，选择相应的操作 **/
    public CheckProductByProUser() {}
    public void CheckProduct(){
        Cleaner.getCleaner().Clean();
        ProductDTO productDTO = new ProductDTO();

        StringBuilder sql = new StringBuilder();
        sql.append("select * from product WHERE pmaker=" + "'" + proUserDTO.getUserName() + "' ;");
        System.out.println("===================================================================================== 分割线 === 分割线 ===============================================================================================================");
        //这个函数将sql语句传入，实现了比较灵活的对MySQL表内数据进行查找
        //同时通过对传入的数字进行判断用户是否具有资格查看商品的哈希编号
        //产品的发布者与购买过该产品的消费者才具有查看产品哈希的资格
        new MysqlServiceImpl().PrintProduct(productDTO, sql.toString(), 1);

        System.out.print("                                        请选择您要进行的操作（1.下架产品       2.返回主菜单）：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);
        switch (select){
            case '1':

                boolean result = new CheckProductByProUser().OffShelfProduct();
                if (result){
                    System.out.println("                                        下架成功！！");
                    Cleaner.getCleaner().BackMain();
                }else {
                    JOptionPane.showMessageDialog(null, "下架失败！！该商品已被下架或者这不是您发布的商品");
                    Cleaner.getCleaner().BackMain();
                }
                break;

            case '2':
                Cleaner.getCleaner().BackMain();
            default:
                JOptionPane.showMessageDialog(null, "格式错误，请重新输入");
                new CheckProductByProUser().CheckProduct();
                break;
        }

    }


    /** 作用：下架产品 **/
    public boolean OffShelfProduct(){
        Connection conn = DBUtil.getConn();
        System.out.print("                                        请输入您需要下架的产品序号：");
        Scanner sc = new Scanner(System.in);
        StringBuilder select = new StringBuilder();
        String input = sc.next();
        long result = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= 48 && input.charAt(i) <= 57) {
                select.append( input.charAt(i) );
            }else {
                break;
            }
        }


        try {
            //确认是否为下架商品的发布者
            String sql = " select pmaker from product where pid=? ";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,select.toString());
            ResultSet rs = ps.executeQuery();
            if ( rs == null ){
                return false;
            }
            while (rs.next()){
                if ( rs.getString("pmaker").equals(proUserDTO.getUserName()) ){
                    sql = " delete from product where pid=? ";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1,select.toString());
                    result = ps.executeLargeUpdate();
                }else {
                    return false;
                }
            }

            DBUtil.closeRs(rs);
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if ( result==0 ){
            return false;
        }
        return true;
    }
}
