package mvcdemo.service.impl;

import mvcdemo.dao.DBUtil;
import mvcdemo.dao.MysqlDAO;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.ProductDTO;
import mvcdemo.dto.UserDTO;
import mvcdemo.service.MysqlService;
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
    String sql;
    long result;
    public MysqlServiceImpl(){

    }

    @Override
    public boolean add(UserDTO userDTO){

        sql = "update user set pwd=?,home=?,name=?,phone=?,ubalance=?,AlreadyPurchased=? where userName=?";
        conn = DBUtil.getConn();

        Object[] param = {
                userDTO.getPwd(),
                userDTO.getHome(),
                userDTO.getName(),
                userDTO.getPhone(),
                String.valueOf(userDTO.getBalance()),
                userDTO.getAlreadyPurchased(),
                userDTO.getUserName()
        };

        result = MysqlDAO.executeUpdate(conn,sql,param);
        if (result != 0){
            DBUtil.closeConn(conn);
            return true;
        }
        DBUtil.closeConn(conn);
        return false;
    }


    @Override
    public boolean addProUser(ProUserDTO proUserDTO){
        sql = "update producer set pwd=?,proPhone=?,proHome=?,proManager=? where id=?";
        conn = DBUtil.getConn();
        //请注意传入参数的顺序
        Object[] param={
                proUserDTO.getPwd(),
                proUserDTO.getProPhone(),
                proUserDTO.getProHome(),
                proUserDTO.getProManager(),
                proUserDTO.getUserName()
        };

        result = MysqlDAO.executeUpdate(conn,sql,param);
        if (result != 0){
            DBUtil.closeConn(conn);
            return true;
        }
        DBUtil.closeConn(conn);
        return false;
    }

    @Override
    public boolean addProduct(ProductDTO productDTO){
        sql = "insert into product(pname,pprice,phash,pid,pmaker,pphone,pclass) values(?,?,?,?,?,?,?);";
        conn = DBUtil.getConn();
        Object[] param = {
                productDTO.getProductName(),
                String.valueOf(productDTO.getProductPrice()),
                productDTO.getProductHash(),

                productDTO.getProductId().toString(),
                productDTO.getProductConner(),
                productDTO.getProductMakePhone(),
                productDTO.getProductClass()
        };

        result = MysqlDAO.executeUpdate(conn,sql,param);
        if (result != 0){
            DBUtil.closeConn(conn);
            return true;
        }
        DBUtil.closeConn(conn);
        return false;
    }

    @Override
    public boolean ReviseProduct(ProductDTO productDTO){
        sql = "update product set pname=?,pprice=? where pid=?";
        conn = DBUtil.getConn();

        Object[] param = {
                productDTO.getProductName(),
                String.valueOf(productDTO.getProductPrice()),
                String.valueOf(productDTO.getProductId())
        };
        result = MysqlDAO.executeUpdate(conn,sql,param);
        if (result != 0){
            DBUtil.closeConn(conn);
            return true;
        }
        DBUtil.closeConn(conn);
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

            DBUtil.closeRs(rs);
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
