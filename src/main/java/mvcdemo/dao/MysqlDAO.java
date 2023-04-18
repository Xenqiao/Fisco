package mvcdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Xenqiao
 * @create 2023/4/13 13:21
 */
public class MysqlDAO {


    public static boolean executeUpdate(String preparedSql, Object[] param){
        Connection conn = DBUtil.getConn();
        PreparedStatement ps = null;
        try {
            // 得到PreparedStatement对象
            ps = conn.prepareStatement(preparedSql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    // 为预编译sql设置参数
                    ps.setObject(i + 1, param[i]);
                }
            }
            return ps.executeLargeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
        return false;
    }

}
