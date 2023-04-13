package mvcdemo.service.impl;

import mvcdemo.dao.DBUtil;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.ProductDTO;
import mvcdemo.dto.UserDTO;
import mvcdemo.util.Cleaner;
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
    public boolean add(UserDTO userDTO){
        StringBuilder sql = new StringBuilder();
        sql.append("update user set pwd=?,home=?,name=?,phone=?,ubalance=?,AlreadyPurchased=? where userName=?");
        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());

            ps.setString(1,userDTO.getPwd());
            ps.setString(2,userDTO.getHome());
            ps.setString(3,userDTO.getName());
            ps.setString(4,userDTO.getPhone());
            ps.setString(5, String.valueOf(userDTO.getBalance()));
            ps.setString(6,userDTO.getAlreadyPurchased());
            ps.setString(7,userDTO.getUserName());
            
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
    public boolean addProUser(ProUserDTO proUserDTO){
        StringBuilder sql = new StringBuilder();
        sql.append("update producer set pwd=?,proPhone=?,proHome=?,proManager=? where id=?");

        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1,proUserDTO.getPwd());
            ps.setString(2,proUserDTO.getProPhone());
            ps.setString(3,proUserDTO.getProHome());
            ps.setString(4,proUserDTO.getProManager());
            ps.setString(5,proUserDTO.getUserName());

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
    public boolean addProduct(ProductDTO productDTO){
        StringBuilder sql = new StringBuilder();
        sql.append("insert into product(pname,pprice,phash,pid,pmaker,pphone,pclass) values(?,?,?,?,?,?,?);");

        try {
            conn = DBUtil.getConn();

            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, productDTO.getProductName());
            ps.setString(2, String.valueOf(productDTO.getProductPrice()));
            ps.setString(3, productDTO.getProductHash());
            ps.setString(4, productDTO.getProductId().toString());
            ps.setString(5, productDTO.getProductConner());
            ps.setString(6, productDTO.getProductMakePhone());
            ps.setString(7, productDTO.getProductClass());
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
    public boolean ReviseProduct(ProductDTO productDTO){
        StringBuilder sql = new StringBuilder();
        sql.append("update product set pname=?,pprice=? where pid=?");
        conn = DBUtil.getConn();

        try {
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1,productDTO.getProductName());
            ps.setString(1, String.valueOf(productDTO.getProductPrice()));
            ps.setString(1, String.valueOf(productDTO.getProductId()));

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
    public boolean PrintProduct(ProductDTO productDTO, String sql, int branch) {
        boolean result = false;
        try {
            conn = DBUtil.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                result = true;
                productDTO.setProductId(Integer.valueOf(rs.getString("pid")));
                productDTO.setProductHash(rs.getString("phash"));
                productDTO.setProductName(rs.getString("pname"));
                Product product = new Product(productDTO.getProductHash(), GetBcosSDK.theGetBcosSDK().getClient(), GetBcosSDK.theGetBcosSDK().getKeyPair());

                productDTO.setProductPrice(product.getProduct(productDTO.getProductHash()).getValue3().intValue());
                productDTO.setProductPlace(product.getProduct(productDTO.getProductHash()).getValue4());
                productDTO.setProductMake(product.getProduct(productDTO.getProductHash()).getValue5());
                productDTO.setProductId(product.getProduct(productDTO.getProductHash()).getValue6().intValue());
                productDTO.setProductMakePhone(rs.getString("pphone"));
                productDTO.setMaker(rs.getString("pmaker"));

                if (branch == 1) {
                    System.out.println("商品哈希：" + productDTO.getProductHash());
                }
                Cleaner.getCleaner().PrintProduct(productDTO);
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
