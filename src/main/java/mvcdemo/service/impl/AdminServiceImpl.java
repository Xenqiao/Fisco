package mvcdemo.service.impl;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.po.GetProUserDO;
import mvcdemo.po.GetUserDO;
import mvcdemo.po.ProUserDO;
import mvcdemo.po.UserDO;

import java.sql.*;

/**
 * @author 谢金桥
 */
public class AdminServiceImpl implements AdminService {

    Connection conn = null;
    ResultSet rs = null;
    Statement stmt;
    String sql;
    
    @Override
    public boolean getUserHash(UserDO userDO) throws Exception{
        Connection conn = DBUtil.getConn();
        ResultSet rs = null;


        sql = "select * from user where userName = " + "'" + userDO.getUserName() + "'";

        try{

            if (conn == null){
                return false;
            }
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                userDO.setHash(rs.getString("hash"));
                userDO.setHome(rs.getString("home"));
                userDO.setName(rs.getString("name"));
                userDO.setPhone(rs.getString("phone"));
                userDO.setBalance(Integer.valueOf(rs.getString("ubalance")));
                userDO.setAlreadyPurchased(rs.getString("AlreadyPurchased"));

                GetUserDO.setUserName(rs.getString("userName"));
                GetUserDO.setPwd(rs.getString("pwd"));
                GetUserDO.setHash(rs.getString("hash"));
                GetUserDO.setHome(rs.getString("home"));
                GetUserDO.setName(rs.getString("name"));
                GetUserDO.setPhone(rs.getString("phone"));
                GetUserDO.setBalance(Integer.valueOf(rs.getString("ubalance")));
                GetUserDO.setAlreadyPurchased(rs.getString("AlreadyPurchased"));
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rs.close();
            stmt.close();
            DBUtil.closeConn(conn);
        }
        return false;
    }

    @Override
    public boolean adminLogon(String useName,int change)throws Exception{
        conn = DBUtil.getConn();
        if (conn == null){
            return false;
        }
        stmt = conn.createStatement();
        if (change == 2){
            sql = "select id from producer where id =" + "'" + useName + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("id").equals(useName)) {
                    return true;
                }
            }
        }else if (change == 1) {
            sql = "select userName from user where userName =" + "'" + useName + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("userName").equals(useName)) {
                    return true;
                }
            }
        }
        rs.close();
        stmt.close();
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

        Statement stmt = conn.createStatement();
        if (change == 1){
            sql = "select pwd from user where userName =" + "'" + lName + "'";
            rs = stmt.executeQuery(sql);
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
            sql = "select pwd from producer where id =" + "'" + lName + "'";
            rs = stmt.executeQuery(sql);
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
        rs.close();
        stmt.close();
        DBUtil.closeConn(conn);
        return false;
    }

    @Override
    public String getProducerHash(ProUserDO proUserDO){
        try {
            conn = DBUtil.getConn();
            stmt = conn.createStatement();
            sql = "select * from producer where id =" + "'" + proUserDO.getUserName() + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                if (rs.getString("pwd").equals(proUserDO.getPwd())){
                    proUserDO.setHash(rs.getString("hash"));
                    proUserDO.setProPhone(rs.getString("proPhone"));
                    proUserDO.setProHome(rs.getString("proHome"));
                    proUserDO.setProManager(rs.getString("proManager"));
                    proUserDO.setBalance(Integer.valueOf(rs.getString("proBalance")));
                    proUserDO.setProAlreadyPurchased(rs.getString("AlreadyPurchased"));

                    GetProUserDO.setUserName(rs.getString("id"));
                    GetProUserDO.setPwd(rs.getString("pwd"));
                    GetProUserDO.setHash(rs.getString("hash"));
                    GetProUserDO.setProPhone(rs.getString("proPhone"));
                    GetProUserDO.setProHome(rs.getString("proHome"));
                    GetProUserDO.setProManager(rs.getString("proManager"));
                    GetProUserDO.setBalance(Integer.valueOf(rs.getString("proBalance")));
                    GetProUserDO.setProAlreadyPurchased(rs.getString("AlreadyPurchased"));
                }
            }
            rs.close();
            stmt.close();
            DBUtil.closeConn(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
}
