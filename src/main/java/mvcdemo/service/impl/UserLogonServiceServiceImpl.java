package mvcdemo.service.impl;

import mvcdemo.dao.DBUtil;
import mvcdemo.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Xenqiao
 * @create 2023/3/19 16:50
 */
public class UserLogonServiceServiceImpl implements UserLogonService {
    public UserLogonServiceServiceImpl(){

    }

    /** 在MySQL数据库中添加新注册的消费者记录 **/
    @Override
    public boolean add(UserDTO userDTO) {
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into user(userName,pwd,hash) ");
        sql.append(" values(?,?,?) ");
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());

            ps.setString(1, userDTO.getUserName());
            ps.setString(2, userDTO.getPwd());
            ps.setString(3, userDTO.getHash());

            return ps.executeLargeUpdate() == 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
        return false;
    }
}
