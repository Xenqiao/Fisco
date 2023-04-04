package mvcdemo.view;

import mvcdemo.service.impl.MysqlServiceImpl;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.ProductDTO;
import mvcdemo.util.Cleaner;

/**
 * @author Xenqiao
 * @create 2023/3/25 21:58
 */
public class CheckProductByProUser {

    public CheckProductByProUser() {
        Cleaner.Clean();
        ProUserDTO proUserDTO = ProUserDTO.getProUserDO();
        ProductDTO productDTO = new ProductDTO();

        StringBuilder sql = new StringBuilder();
        sql.append("select * from product WHERE pmaker=" + "'" + proUserDTO.getUserName() + "' ;");
        System.out.println("===================================================================================== 分割线 === 分割线 ===============================================================================================================");
        new MysqlServiceImpl().PrintProduct(productDTO, sql.toString(), 1);
        Cleaner.BackMain();

    }
}
