package mvcdemo.dao.mysql.impl;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.po.UserDO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Xenqiao
 * @create 2023/3/20 22:32
 */
public class UserServiceImpl implements UserService{
    Connection conn = null;
    PreparedStatement ps = null;

    public UserServiceImpl(){

    }

    @Override
    public boolean add(UserDO userDO){
        StringBuilder sql = new StringBuilder();
        //update user set pwd="222" where userName="a";
        sql.append(" update user set " +
                "pwd="+"'"+userDO.getPwd()+"' ,"+
                "home="+"'"+userDO.getHome()+"' ,"+
                "name="+"'"+userDO.getName()+"' ,"+
                "phone="+"'"+userDO.getPhone()+"' ,"+
                "ubalance="+"'"+userDO.getBalance()+"' ,"+
                "AlreadyPurchased="+"'"+userDO.getAlreadyPurchased()+"' "+
                "where userName="+"'"+userDO.getUserName()+"';");

        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());

            
            return ps.executeLargeUpdate() == 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
        return false;
    }


    @Override
    public boolean addProUser(ProUserDO proUserDO){
        StringBuilder sql = new StringBuilder();
        sql.append(" update producer set " +
                "pwd="+"'"+proUserDO.getPwd()+"' ,"+
                "proPhone="+"'"+proUserDO.getProPhone()+"' ,"+
                "proHome="+"'"+proUserDO.getProHome()+"' ,"+
                "proManager="+"'"+proUserDO.getProManager()+"' "+
                "where id="+"'"+proUserDO.getUserName()+"';");

        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());

            return ps.executeLargeUpdate() == 1 ;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
        return false;
    }

    @Override
    public boolean addProduct(ProductDO productDO){
        StringBuilder sql = new StringBuilder();
        sql.append("insert into product(pname,pprice,phash,pid,pmaker,pphone) values(?,?,?,?,?,?);");

        try {
            conn = DBUtil.getConn();

            ps = conn.prepareStatement(sql.toString());
            ps.setString(1,productDO.getProductName());
            ps.setString(2, String.valueOf(productDO.getProductPrice()));
            ps.setString(3,productDO.getProductHash());
            ps.setString(4,productDO.getProductId().toString());
            ps.setString(5,productDO.getProductConner());
            ps.setString(6,productDO.getProductMakePhone());
            return ps.executeLargeUpdate() == 1 ;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
        return false;
    }

    @Override
    public boolean ReviseProduct(ProductDO productDO){
        StringBuilder sql = new StringBuilder();
        sql.append(" update product set " +
                "pname="+"'"+productDO.getProductName()+"' ,"+
                "pprice="+"'"+productDO.getProductPrice()+"' "+
                "where pid="+"'"+productDO.getProductId()+"';");
        conn = DBUtil.getConn();

        try {
            ps = conn.prepareStatement(sql.toString());

            return ps.executeLargeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeConn(conn);
            DBUtil.closePs(ps);
        }
        return false;
    }
}
