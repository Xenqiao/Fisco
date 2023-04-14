package mvcdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Xenqiao
 * @create 2023/4/13 13:21
 */
public class MysqlDAO {

    public static ResultSet executeSQL(Connection conn, String preparedSql, Object[] param){
        /* 处理SQL,执行SQL */
        // 得到数据库连接

        try {
            // 得到PreparedStatement对象
            PreparedStatement ps = conn.prepareStatement(preparedSql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    // 为预编译sql设置参数
                    ps.setObject(i + 1, param[i]);
                }
            }
            // 执行SQL语句
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
