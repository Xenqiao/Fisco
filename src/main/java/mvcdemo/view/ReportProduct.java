package mvcdemo.view;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.dao.mysql.impl.MysqlServiceImpl;
import mvcdemo.po.GetProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.service.Cleaner;

import java.sql.*;
import java.util.Scanner;

/**
 * @author Xenqiao
 * @create 2023/4/2 15:40
 */
public class ReportProduct {
    ProductDO productDO = new ProductDO();


    /** 举报产品的函数：前期准备  **/
    public void ReportProducts(){
        String Sql = "select * from product;";
        new MysqlServiceImpl().PrintProduct(productDO,Sql,0);


        System.out.print("                                        请选择输入您要点赞或者举报的商品序号：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);

        Cleaner.Clean();
        productDO.setProductId(Integer.valueOf(String.valueOf(select)));
        StringBuilder sql = new StringBuilder();
        sql.append("select * from product WHERE pid="+"'"+productDO.getProductId()+"' ;");

        boolean result = new MysqlServiceImpl().PrintProduct(productDO,sql.toString(),0);

        if (result == false){
            System.out.println("                                    商品不存在");
        }
        System.out.print("                                        请选择您要对该产品进行的操作（ a.点赞      b.举报    c.返回主菜单）：");

        sc = new Scanner(System.in);
        select = sc.next().charAt(0);
        switch (select){
            case 'a':
                this.support(productDO,"sMessage");
                Cleaner.BackMain();
                break;
            case'b':
                this.support(productDO,"rMessage");
                Cleaner.BackMain();
                break;
            default:
                return;
        }

    }


    Connection conn = null;
    /** 是一个可以点赞也可以举报的函数 **/
    public void support(ProductDO productDO, String mess){
        GetProUserDO.setUserName(productDO.getMaker());

        StringBuilder sql = new StringBuilder();

        Statement stmt = null;
        try {
            conn = DBUtil.getConn();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select "+mess+" from producer where id="+"'"+GetProUserDO.getUserName()+"' ;");
            while (rs.next()){
                GetProUserDO.setrMessage(rs.getString(mess));
            }
            if (GetProUserDO.getrMessage()==null || "".equals(GetProUserDO.getrMessage())){
                GetProUserDO.setrMessage("0,");
            }
            StringBuilder message = new StringBuilder();
            message.append(GetProUserDO.getrMessage()+productDO.getProductId()+",");
            GetProUserDO.setrMessage(message.toString());

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        sql.append(" update producer set " +
                mess+"="+"'"+GetProUserDO.getrMessage()+"' "+
                " where id="+"'"+GetProUserDO.getUserName()+"';");

        try {
            conn = DBUtil.getConn();
            PreparedStatement ps = conn.prepareStatement(sql.toString());

            if(ps.executeLargeUpdate() == 1){
                if ("sMessage".equals(mess)){
                    System.out.println("            点赞成功！感谢您的支持~");
                }else if ("rMessage".equals(mess)){
                    System.out.println("                举报成功！感谢您的反馈~");
                }
            }

            ps.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
