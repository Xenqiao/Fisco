package mvcdemo.util;

import mvcdemo.dto.ProductDTO;
import org.fisco.bcos.sdk.crypto.hash.Keccak256;

import java.util.Scanner;

/**
 * @author Xenqiao
 * @create 2023/3/18 18:03
 */
public class Cleaner {
    /** 单例模式的双重检查 **/
    private static volatile Cleaner cleaner;
    private Cleaner(){
    }
    public static Cleaner getCleaner(){
        if (cleaner == null){
            synchronized (Cleaner.class){
                if (cleaner == null){
                    cleaner = new Cleaner();
                }
            }
        }
        return cleaner;
    }

    /**
     * 清屏
     */
    public void Clean() {
        for (int i = 0;i < 50;i++){
            System.out.println();
        }

    }

    /** 在用户查看信息后等待用户的确定以返回主菜单的一个辗转过渡函数 **/
    public void BackMain(){
        System.out.println();
        System.out.print("                                        输入任意键返回(请勿直接回车)：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);
    }


    /** 打印产品的各种信息，一个通用的工具函数 **/
    public void PrintProduct(ProductDTO productDTO){

        System.out.println("商品序号："+ productDTO.getProductId());
        System.out.println("商品名称："+ productDTO.getProductName());
        System.out.println("商品价格："+ productDTO.getProductPrice()+"  ETH");
        System.out.println("产品委托商："+ productDTO.getProductMake());
        System.out.println("产品委托商联系电话："+ productDTO.getProductMakePhone());
        System.out.println("产品发货地址："+ productDTO.getProductPlace());

    }

    public String EncryptedInformation(String data){
        Keccak256 keccak256 = new Keccak256();
        String encrypted = keccak256.hash(data);
        return encrypted;
    }
}
