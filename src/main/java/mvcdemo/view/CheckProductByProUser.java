package mvcdemo.view;

import mvcdemo.dao.mysql.impl.MysqlServiceImpl;
import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.service.Cleaner;

/**
 * @author Xenqiao
 * @create 2023/3/25 21:58
 */
public class CheckProductByProUser {

    public CheckProductByProUser(ProUserDO proUserDO) {
        Cleaner.Clean();
        ProductDO productDO = new ProductDO();

        StringBuilder sql = new StringBuilder();
        sql.append("select * from product WHERE pmaker=" + "'" + proUserDO.getUserName() + "' ;");
        System.out.println("===================================================================================== 分割线 === 分割线 ===============================================================================================================");
        new MysqlServiceImpl().PrintProduct(productDO, sql.toString(), 1);
        Cleaner.BackMain();

    }
}
