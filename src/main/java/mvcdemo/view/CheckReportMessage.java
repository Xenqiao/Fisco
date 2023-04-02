package mvcdemo.view;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.dao.mysql.impl.MysqlServiceImpl;
import mvcdemo.po.GetProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.service.Cleaner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Xenqiao
 * @create 2023/4/2 17:15
 */
public class CheckReportMessage {

    ProductDO productDO = new ProductDO();
    Connection conn = null;

    public void CheckSupport(){
        Cleaner.Clean();
        System.out.println("===================================================================================== 分割线 == 分割线 ================================================================================================================");

        String proUserSupport = GetProUserDO.getsMessage();
        String productID = "";
        if(proUserSupport != null && !"".equals(proUserSupport)) {
            System.out.println();
            System.out.println("                                                                        以下是您被点赞支持的产品,您将由此获得奖励金：");
            System.out.println("                                                                        以下是您被点赞支持的产品,您将由此获得奖励金：");
            for (int i = 0; i < proUserSupport.length(); i++) {
                if (proUserSupport.charAt(i) >= 48 && proUserSupport.charAt(i) <= 57) {
                    productID += proUserSupport.charAt(i);

                } else if (productID != null && !"".equals(productID)) {
                    StringBuilder sql = new StringBuilder();
                    sql.append("select * from product where pid=" + "'" + productID + "' ;");
                    new MysqlServiceImpl().PrintProduct(productDO, sql.toString(), 1);
                    productID = "";
                }
            }

            conn = DBUtil.getConn();
            try {
                PreparedStatement ps = conn.prepareStatement("update producer set sMessage=null where id="+"'"+GetProUserDO.getUserName()+"' ;");
                int checked = ps.executeUpdate();
                ps.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void CheckReport(){
        System.out.println("===================================================================================== 分割线 == 分割线 ================================================================================================================");

        String proUserSupport = GetProUserDO.getrMessage();
        String productID = "";
        if(proUserSupport != null && !"".equals(proUserSupport)) {
            System.out.println();
            System.out.println("                                                                        以下是您被举报的产品,您将由此扣除一部分质押金：");
            System.out.println("                                                                        以下是您被举报的产品,您将由此扣除一部分质押金：");
            for (int i = 0; i < proUserSupport.length(); i++) {
                if (proUserSupport.charAt(i) >= 48 && proUserSupport.charAt(i) <= 57) {
                    productID += proUserSupport.charAt(i);

                } else if (productID != null && !"".equals(productID)) {
                    StringBuilder sql = new StringBuilder();
                    sql.append("select * from product where pid=" + "'" + productID + "' ;");
                    new MysqlServiceImpl().PrintProduct(productDO, sql.toString(), 1);
                    productID = "";
                }
            }

            conn = DBUtil.getConn();
            try {
                PreparedStatement ps = conn.prepareStatement("update producer set rMessage=null where id="+"'"+GetProUserDO.getUserName()+"' ;");
                int checked = ps.executeUpdate();
                ps.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
