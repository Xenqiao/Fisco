package mvcdemo.service.impl;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.dto.ProUserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Xenqiao
 * @create 2023/3/20 12:14
 */
public class ProductLogonServiceServiceImpl implements ProductLogonService {
    public ProductLogonServiceServiceImpl(){
    }

    /** 在MySQL数据库中添加新注册的生产者记录 **/
    @Override
    public boolean addPro(ProUserDTO proUserDTO){
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into producer(id,pwd,hash) ");
        sql.append(" values(?,?,?) ");
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());

            ps.setString(1, proUserDTO.getUserName());
            ps.setString(2, proUserDTO.getPwd());
            ps.setString(3, proUserDTO.getHash());

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
