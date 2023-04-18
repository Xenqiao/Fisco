package mvcdemo.view;

import mvcdemo.dao.DBUtil;
import mvcdemo.service.impl.MysqlServiceImpl;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.ProductDTO;
import mvcdemo.util.Cleaner;

import java.sql.*;
import java.util.Scanner;

/**
 * @author Xenqiao
 * @create 2023/4/2 15:40
 */
public class ReportProduct {
    ProductDTO productDTO = new ProductDTO();


    /** 举报产品的函数：前期准备  **/
    public void ReportProducts(){
        String Sql = "select * from product;";
        MysqlServiceImpl.getMysqlService().printProduct(productDTO,Sql,0);


        System.out.print("                                        请选择输入您要点赞或者举报的商品序号：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);

        Cleaner.getCleaner().Clean();
        productDTO.setProductId(Integer.valueOf(String.valueOf(select)));
        StringBuilder sql = new StringBuilder();
        sql.append("select * from product WHERE pid="+"'"+ productDTO.getProductId()+"' ;");

        boolean result = MysqlServiceImpl.getMysqlService().printProduct(productDTO,sql.toString(),0);

        if (result == false){
            System.out.println("                                    商品不存在");
        }
        System.out.print("                                        请选择您要对该产品进行的操作（ a.点赞      b.举报    c.返回主菜单）：");

        sc = new Scanner(System.in);
        select = sc.next().charAt(0);
        switch (select){
            case 'a':
                this.support(productDTO,"sMessage");
                Cleaner.getCleaner().BackMain();
                break;
            case'b':
                this.support(productDTO,"rMessage");
                Cleaner.getCleaner().BackMain();
                break;
            default:
                return;
        }

    }


    Connection conn = null;
    /** 是一个可以点赞也可以举报的函数 **/
    public void support(ProductDTO productDTO, String mess){
        ProUserDTO proUserDTO = ProUserDTO.getProUserDO();
        proUserDTO.setUserName(productDTO.getMaker());

        StringBuilder sql = new StringBuilder();

        Statement stmt = null;
        try {
            conn = DBUtil.getConn();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select "+mess+" from producer where id="+"'"+ proUserDTO.getUserName()+"' ;");
            while (rs.next()){
                proUserDTO.setrMessage(rs.getString(mess));
            }
            if (proUserDTO.getrMessage()==null || "".equals(proUserDTO.getrMessage())){
                proUserDTO.setrMessage("0,");
            }
            StringBuilder message = new StringBuilder();
            message.append(proUserDTO.getrMessage()+ productDTO.getProductId()+",");
            proUserDTO.setrMessage(message.toString());

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        sql.append(" update producer set " +
                mess+"="+"'"+ proUserDTO.getrMessage()+"' "+
                " where id="+"'"+ proUserDTO.getUserName()+"';");

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
