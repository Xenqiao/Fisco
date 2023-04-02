package mvcdemo.dao.mysql.impl;

import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.po.UserDO;

/**
 * @author Xenqiao
 * @create 2023/3/20 22:32
 */
public interface MysqlService {
    boolean add(UserDO userDO);
    boolean addProUser(ProUserDO proUserDO);
    boolean addProduct(ProductDO productDO);
    boolean ReviseProduct(ProductDO productDO);
    boolean PrintProduct(ProductDO productDO, String sql, int branch);
}