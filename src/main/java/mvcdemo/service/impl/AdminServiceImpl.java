package mvcdemo.service.impl;

import mvcdemo.dao.DBUtil;
import mvcdemo.dao.MysqlDAO;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.UserDTO;
import mvcdemo.service.AdminService;

import java.sql.*;

/**
 * @author 谢金桥
 */
public class AdminServiceImpl implements AdminService {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    String sql = null;
    ProUserDTO proUserDTO = ProUserDTO.getProUserDO();

    @Override
    public boolean getUserHash(UserDTO userDTO) throws Exception {
        conn = DBUtil.getConn();

        sql = "select * from user where userName=?";
        Object[] param = {userDTO.getUserName()};
        rs = MysqlDAO.executeSQL(conn, sql, param);

        if (rs == null) {
            DBUtil.closeConn(conn);
            return false;
        }
        while (rs.next()) {

            userDTO.setUserName(rs.getString("userName"));
            userDTO.setPwd(rs.getString("pwd"));
            userDTO.setHash(rs.getString("hash"));
            userDTO.setHome(rs.getString("home"));
            userDTO.setName(rs.getString("name"));
            userDTO.setPhone(rs.getString("phone"));
            userDTO.setBalance(Integer.valueOf(rs.getString("ubalance")));
            userDTO.setAlreadyPurchased(rs.getString("AlreadyPurchased"));
        }
        DBUtil.closeRs(rs);
        DBUtil.closeConn(conn);
        return true;
    }

    @Override
    public boolean adminLogon(String useName,int change)throws Exception{
        conn = DBUtil.getConn();
        if (conn == null){
            return false;
        }

        Object[] param = {useName};
        if (change == 2){
            sql = "select id from producer where id =?";
            rs = MysqlDAO.executeSQL(conn,sql,param);

            if (rs.next()){
                return true;
            }
        }else if (change == 1) {
            sql = "select userName from user where userName =?";
            rs = MysqlDAO.executeSQL(conn,sql,param);

            if (rs.next()){
                return true;
            }
        }
        DBUtil.closeRs(rs);
        DBUtil.closePs(ps);
        DBUtil.closeConn(conn);
        return false;
    }

    /**
     * 登录时查询 MySQL的函数，负责验证密码
     * @param lName
     * @param lPassword
     * @param change
     * @return
     * @throws Exception
     */
    @Override
    public boolean loGin(String lName,String lPassword, int change) throws Exception{
        conn = DBUtil.getConn();
        if (conn == null){
            return false;
        }

        Object[] param = {lName};
        if (change == 1){
            sql = "select pwd from user where userName =?";
            rs = MysqlDAO.executeSQL(conn,sql,param);

            while (rs.next()) {
                if (rs.getString("pwd").equals(lPassword)) {
                    System.out.println("密码正确！");
                    return true;
                } else {
                    System.out.println("密码错误！");
                    return false;
                }
            }
        }else if (change == 2){
            sql = "select pwd from producer where id =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,lName);
            rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString("pwd").equals(lPassword)) {
                    System.out.println("密码正确！");
                    return true;
                } else {
                    System.out.println("密码错误！");
                    return false;
                }
            }
        }
        DBUtil.closeRs(rs);
        DBUtil.closePs(ps);
        DBUtil.closeConn(conn);
        return false;
    }

    @Override
    public void getProducerHash(){
        try {
            conn = DBUtil.getConn();
            sql = "select * from producer where id =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,proUserDTO.getUserName());
            rs = ps.executeQuery();

            while (rs.next()){
                if (rs.getString("pwd").equals(proUserDTO.getPwd())){
                    proUserDTO.setHash(rs.getString("hash"));
                    proUserDTO.setProPhone(rs.getString("proPhone"));
                    proUserDTO.setProHome(rs.getString("proHome"));
                    proUserDTO.setProManager(rs.getString("proManager"));
                    proUserDTO.setBalance(Integer.valueOf(rs.getString("proBalance")));
                    proUserDTO.setProAlreadyPurchased(rs.getString("AlreadyPurchased"));
                    proUserDTO.setrMessage(rs.getString("rMessage"));
                    proUserDTO.setsMessage(rs.getString("sMessage"));
                }
            }
            DBUtil.closeRs(rs);
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
