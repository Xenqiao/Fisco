package mvcdemo.service.impl;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.po.ProUserDO;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Xenqiao
 * @create 2023/3/20 12:14
 */
public class ProductLogonImpl implements ProductLogon{
    public ProductLogonImpl(){
    }

    @Override
    public boolean addPro(ProUserDO proUserDO){
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into producer(id,pwd,hash) ");
        sql.append(" values(?,?,?) ");
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());

            ps.setString(1, proUserDO.getUserName());
            ps.setString(2, proUserDO.getPwd());
            ps.setString(3, proUserDO.getHash());

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
