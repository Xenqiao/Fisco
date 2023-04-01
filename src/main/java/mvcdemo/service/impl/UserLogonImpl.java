package mvcdemo.service.impl;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.po.UserDO;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Xenqiao
 * @create 2023/3/19 16:50
 */
public class UserLogonImpl implements UserLogon {
    public UserLogonImpl(){

    }

    @Override
    public boolean add(UserDO userDO) {
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into user(userName,pwd,hash) ");
        sql.append(" values(?,?,?) ");
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());

            ps.setString(1,userDO.getUserName());
            ps.setString(2,userDO.getPwd());
            ps.setString(3,userDO.getHash());

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
