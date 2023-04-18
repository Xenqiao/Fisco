package mvcdemo.view;

import mvcdemo.dto.ProUserDTO;
import mvcdemo.util.Cleaner;
import mvcdemo.controller.handler.MainViewService;

/**
 * @author Xenqiao
 * @create 2023/3/18 17:24
 */
public class MainView {
    public void UserMain(){

        Cleaner.getCleaner().Clean();
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
        new MainViewService().UserMainViewHandler();

    }


    public void ProductMain(){
        ProUserDTO proUserDTO = ProUserDTO.getProUserDO();
        if ( "".equals(proUserDTO.getrMessage()) ){

            System.out.println("请注意，您有新的举报信息！！");
        }
        if("".equals(proUserDTO.getsMessage())){
            System.out.println("请注意，您有新的点赞信息！！");

        }
        System.out.println("                                                欢迎您，生产者！");
        System.out.println("                                        -----------------------------------");
        System.out.println("                                        选择1：查看我的用户信息");
        System.out.println("                                        选择2：修改用户信息");
        System.out.println("                                        选择3：上传商品信息");
        System.out.println("                                        选择4：修改我的商品信息");
        System.out.println("                                        选择5：查询或下架我的产品");
        System.out.println("                                        选择6：查看举报或点赞信息");
        System.out.println("                                        选择7：查看产品分类");
        System.out.println();
        System.out.println("                                        ------------------------------------");
        System.out.println();
        new MainViewService().ProUserMainViewHandler();
    }


}
