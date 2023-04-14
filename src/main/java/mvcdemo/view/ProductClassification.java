package mvcdemo.view;

import mvcdemo.dto.ProductDTO;
import mvcdemo.service.MysqlService;
import mvcdemo.service.impl.MysqlServiceImpl;
import mvcdemo.util.Cleaner;

import java.util.Scanner;

/**
 * @author Xenqiao
 * @create 2023/4/2 21:19
 */
public class ProductClassification {
    public ProductClassification(){
        Cleaner.getCleaner().Clean();
        System.out.println("1.美妆类   2.数码产品类   3.食品生鲜类   4.服饰类   5.进口零食类   6.蔬菜类");
        System.out.print("请输入你需要查看的产品类别: ");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);

        String s = String.valueOf(select);
        MysqlService mysqlService = new MysqlServiceImpl();
        ProductDTO productDTO = new ProductDTO();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from product where pclass="+s+";");
        mysqlService.PrintProduct(productDTO, sql.toString() ,0);

        Cleaner.getCleaner().BackMain();
    }
}
