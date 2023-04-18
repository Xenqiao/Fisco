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

    String sql;
    /**  单例模式的双重检查  **/
    private static volatile MysqlServiceImpl mysqlService;
    private MysqlServiceImpl(){
    }
    public static MysqlServiceImpl getMysqlService(){
        if (mysqlService == null){
            synchronized (MysqlServiceImpl.class){
                if (mysqlService == null){
                    mysqlService = MysqlServiceImpl.getMysqlService();
                }
            }
        }
        return mysqlService;
    }


    /** 在MySQL数据库中添加新注册的消费者记录 **/
    @Override
    public boolean addUser(UserDTO userDTO) {
        sql = " insert into user(userName,pwd,hash,ubalance) values(?,?,?,?) ";
        Object[] param = {
                userDTO.getUserName(),
                userDTO.getPwd(),
                userDTO.getHash(),
                "0"
        };

        return MysqlDAO.executeUpdate(sql,param);
    }


    /**
     * 修改消费者的信息 **/
    @Override
    public boolean modifyUser(UserDTO userDTO){
        sql = "update user set pwd=?,home=?,name=?,phone=?,ubalance=?,AlreadyPurchased=? where userName=?";

        Object[] param = {
                userDTO.getPwd(),
                userDTO.getHome(),
                userDTO.getName(),
                userDTO.getPhone(),
                String.valueOf(userDTO.getBalance()),
                userDTO.getAlreadyPurchased(),
                userDTO.getUserName()
        };

        return MysqlDAO.executeUpdate(sql,param);
    }


    /**
     * 在MySQL数据库中添加新注册的生产者记录 **/
    @Override
    public boolean addProUser(ProUserDTO proUserDTO){
        sql = " insert into producer(id,pwd,hash,proBalance) values(?,?,?,?) ";

        Object[] param = {
                proUserDTO.getUserName(),
                proUserDTO.getPwd(),
                proUserDTO.getHash(),
                "0"
        };

        return MysqlDAO.executeUpdate(sql,param);
    }


    /**
     * 修改生产者的信息 **/
    @Override
    public boolean modifyProUser(ProUserDTO proUserDTO){
        sql = "update producer set pwd=?,proPhone=?,proHome=?,proManager=? where id=?";

        //请注意传入参数的顺序
        Object[] param={
                proUserDTO.getPwd(),
                proUserDTO.getProPhone(),
                proUserDTO.getProHome(),
                proUserDTO.getProManager(),
                proUserDTO.getUserName()
        };
        return MysqlDAO.executeUpdate(sql,param);
    }


    /**
     * 该函数用于添加商品 **/
    @Override
    public boolean addProduct(ProductDTO productDTO){
        sql = "insert into product(pname,pprice,phash,pid,pmaker,pphone,pclass) values(?,?,?,?,?,?,?);";

        Object[] param = {
                productDTO.getProductName(),
                String.valueOf(productDTO.getProductPrice()),
                productDTO.getProductHash(),

                productDTO.getProductId().toString(),
                productDTO.getProductConner(),
                productDTO.getProductMakePhone(),
                productDTO.getProductClass()
        };

        return MysqlDAO.executeUpdate(sql,param);

    }


    /**
     * 该函数用于修改商品的信息 **/
    @Override
    public boolean reviseProduct(ProductDTO productDTO){
        sql = "update product set pname=?,pprice=? where pid=?";

        Object[] param = {
                productDTO.getProductName(),
                String.valueOf(productDTO.getProductPrice()),
                String.valueOf(productDTO.getProductId())
        };
        return MysqlDAO.executeUpdate(sql,param);
    }


    /**
     * 从 MySQL数据库读取产品信息并且打印显示 **/
    @Override
    public boolean printProduct(ProductDTO productDTO, String sql, int branch) {
        boolean result = false;
        Connection conn = DBUtil.getConn();
        try {
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
                productDTO.setProductMakePhone(rs.getString("pphone"));
                productDTO.setMaker(rs.getString("pmaker"));

                if (branch == 1) {
                    System.out.println("商品哈希：" + productDTO.getProductHash());
                }
                Cleaner.getCleaner().printProduct(productDTO);
                System.out.println();
                System.out.println();

            }

            DBUtil.closeRs(rs);
            stmt.close();
            DBUtil.closeConn(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return result;
    }
}
