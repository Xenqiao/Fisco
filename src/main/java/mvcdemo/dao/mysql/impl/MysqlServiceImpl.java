package mvcdemo.dao.mysql.impl;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.po.UserDO;
import mvcdemo.service.Cleaner;
import mvcdemo.util.contractRealize.GetBcosSDK;
import mvcdemo.util.toolcontract.Product;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.sql.*;

/**
 * @author Xenqiao
 * @create 2023/3/20 22:32
 */
public class MysqlServiceImpl implements MysqlService {
    Connection conn = null;
    PreparedStatement ps = null;

    public MysqlServiceImpl(){

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

    @Override
    public boolean PrintProduct(ProductDO productDO, String sql, int branch) {
        boolean result = false;
        try {
            conn = DBUtil.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                result = true;
                productDO.setProductId(Integer.valueOf(rs.getString("pid")));
                productDO.setProductHash(rs.getString("phash"));
                productDO.setProductName(rs.getString("pname"));
                Product product = new Product(productDO.getProductHash(), GetBcosSDK.getClient(), GetBcosSDK.getKeyPair());

                productDO.setProductPrice(product.getProduct(productDO.getProductHash()).getValue3().intValue());
                productDO.setProductPlace(product.getProduct(productDO.getProductHash()).getValue4());
                productDO.setProductMake(product.getProduct(productDO.getProductHash()).getValue5());
                productDO.setProductId(product.getProduct(productDO.getProductHash()).getValue6().intValue());
                productDO.setProductMakePhone(rs.getString("pphone"));

                if (branch == 1) {
                    System.out.println("商品哈希：" + productDO.getProductHash());
                }
                Cleaner.PrintProduct(productDO);
                System.out.println();
                System.out.println();

            }

            rs.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ContractException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
        }
        return result;
    }
}
