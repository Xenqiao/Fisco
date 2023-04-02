package mvcdemo.service;

import mvcdemo.po.ProductDO;

import java.util.Scanner;

/**
 * @author Xenqiao
 * @create 2023/3/18 18:03
 */
public class Cleaner {
    /**
     * 清屏
     */
    public static void Clean() {
        for (int i = 0;i < 50;i++){
            System.out.println();
        }
    }

    public static void BackMain(){
        System.out.println();
        System.out.print("                                        输入任意键返回(请勿直接回车)：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);
    }

    public static void PrintProduct(ProductDO productDO){

        System.out.println("商品序号："+ productDO.getProductId());
        System.out.println("商品名称："+ productDO.getProductName());
        System.out.println("商品价格："+ productDO.getProductPrice()+"  ETH");
        System.out.println("产品委托商："+ productDO.getProductMake());
        System.out.println("产品委托商联系电话："+ productDO.getProductMakePhone());
        System.out.println("产品发货地址："+ productDO.getProductPlace());

    }
}
