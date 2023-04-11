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
        Cleaner.getCleaner().Clean();
        ProUserDTO proUserDTO = ProUserDTO.getProUserDO();
        ProductDTO productDTO = new ProductDTO();

        StringBuilder sql = new StringBuilder();
        sql.append("select * from product WHERE pmaker=" + "'" + proUserDTO.getUserName() + "' ;");
        System.out.println("===================================================================================== 分割线 === 分割线 ===============================================================================================================");
        //这个函数将sql语句传入，实现了比较灵活的对MySQL表内数据进行查找
        //同时通过对传入的数字进行判断用户是否具有资格查看商品的哈希编号
        //产品的发布者与购买过该产品的消费者才具有查看产品哈希的资格
        new MysqlServiceImpl().PrintProduct(productDTO, sql.toString(), 1);
        Cleaner.getCleaner().BackMain();

    }
}
