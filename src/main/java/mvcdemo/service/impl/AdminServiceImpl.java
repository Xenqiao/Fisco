package mvcdemo.service.impl;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.UserDTO;

import java.sql.*;

/**
 * @author 谢金桥
 */
public class AdminServiceImpl implements AdminService {

    Connection conn = null;
    ResultSet rs = null;
    Statement stmt;
    String sql;
    ProUserDTO proUserDTO = ProUserDTO.getProUserDO();
    UserDTO userDTO = UserDTO.getUserDO();

    @Override
    public boolean getUserHash(UserDTO userDTO) throws Exception{
        Connection conn = DBUtil.getConn();
        ResultSet rs = null;


        sql = "select * from user where userName = " + "'" + userDTO.getUserName() + "'";

        try{

            if (conn == null){
                return false;
            }
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){

                userDTO.setUserName(rs.getString("userName"));
                userDTO.setPwd(rs.getString("pwd"));
                userDTO.setHash(rs.getString("hash"));
                userDTO.setHome(rs.getString("home"));
                userDTO.setName(rs.getString("name"));
                userDTO.setPhone(rs.getString("phone"));
                userDTO.setBalance(Integer.valueOf(rs.getString("ubalance")));
                userDTO.setAlreadyPurchased(rs.getString("AlreadyPurchased"));
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
    public String getProducerHash(){
        try {
            conn = DBUtil.getConn();
            stmt = conn.createStatement();
            sql = "select * from producer where id =" + "'" + proUserDTO.getUserName() + "'";
            rs = stmt.executeQuery(sql);
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
            rs.close();
            stmt.close();
            DBUtil.closeConn(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
}
