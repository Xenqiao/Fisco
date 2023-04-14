package mvcdemo.service.impl;

import mvcdemo.dao.DBUtil;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.service.ProductLogonService;

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
        String sql = " insert into producer(id,pwd,hash) values(?,?,?) ";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql);

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
