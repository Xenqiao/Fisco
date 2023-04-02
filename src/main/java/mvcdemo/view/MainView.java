package mvcdemo.view;

import mvcdemo.po.GetProUserDO;
import mvcdemo.po.ProUserDO;
import mvcdemo.po.UserDO;
import mvcdemo.service.Cleaner;
import mvcdemo.service.MainViewHandler;

import java.util.Scanner;

/**
 * @author Xenqiao
 * @create 2023/3/18 17:24
 */
public class MainView {
    public void UserMain(UserDO userDO){

        Cleaner.Clean();
        System.out.println("                                                欢迎光临，尊敬的顾客！");
        System.out.println("                                        -----------------------------------");
        System.out.println("                                        选择1：查看我的用户信息");
        System.out.println("                                        选择2：修改用户信息");
        System.out.println("                                        选择3：购买或查询商品");
        System.out.println("                                        选择4：查看已购买产品信息");
        System.out.println("                                        选择5：举报或点赞产品");
        System.out.println("                                        选择6：退货");
        System.out.println("                                        选择7：查看产品分类");
        System.out.println("                                        选择8：真伪验证");
        System.out.println("                                        ------------------------------------");
        System.out.println();
        new MainViewHandler().UserMainViewHandler(userDO);

    }


    public void ProductMain(ProUserDO proUserDO){
        if ( "".equals(GetProUserDO.getrMessage()) ){

            System.out.println("请注意，您有新的举报信息！！");
        }
        if("".equals(GetProUserDO.getsMessage())){
            System.out.println("请注意，您有新的点赞信息！！");

        }
        System.out.println("                                                欢迎您，生产者！");
        System.out.println("                                        -----------------------------------");
        System.out.println("                                        选择1：查看我的用户信息");
        System.out.println("                                        选择2：修改用户信息");
        System.out.println("                                        选择3：上传商品信息");
        System.out.println("                                        选择4：修改我的商品信息");
        System.out.println("                                        选择5：查询我的产品");
        System.out.println("                                        选择6：查看举报或点赞信息");
        System.out.println("                                        选择7：查看产品分类");
        System.out.println();
        System.out.println("                                        ------------------------------------");
        System.out.println();
        System.out.print("                                        请选择您要进行的操作（请输入数字作为您的选择）：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);
        new MainViewHandler().ProUserMainViewHandler(select,proUserDO);
    }


}
