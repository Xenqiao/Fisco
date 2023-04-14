package mvcdemo.service.handler;

import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.UserDTO;
import mvcdemo.util.Cleaner;
import mvcdemo.util.contractRealize.ChangeOnFisco;
import mvcdemo.view.*;

import javax.swing.*;
import java.util.Scanner;

/**
 * @author Xenqiao
 * @create 2023/3/20 22:55
 */
public class MainViewService {
    ProUserDTO proUserDTO = ProUserDTO.getProUserDO();
    UserDTO userDTO = UserDTO.getUserDO();


    public void UserMainViewHandler(){

        System.out.print("                                        请选择您要进行的操作（请输入数字作为您的选择）：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);
        switch (select){
            case '1':
                //选择1：查看我的用户信息
                new MainViewService().CheckUser();
                Cleaner.getCleaner().Clean();
                new MainView().UserMain();
                break;
            case '2':
                //选择2：修改用户信息
                new ChangeUser();
                break;
            case '3':
                //选择3：购买或查询商品
                System.out.println();
                System.out.println("                                        正在加载中，不要退出控制台界面，请耐心等待！");
                new CheckProductByUser().CheckProduct();
                Cleaner.getCleaner().Clean();
                new MainView().UserMain();
                break;
            case '4':
                //选择4：查看已购买产品信息
                new AlreadyPurchasedByUser().CheckingAlreadyPurchased();
                Cleaner.getCleaner().Clean();
                new MainView().UserMain();
                break;
            case '5':
                //选择5：举报或点赞产品
                Cleaner.getCleaner().Clean();
                new ReportProduct().ReportProducts();
                new MainView().UserMain();
                break;
            case'6':
                //选择6：退货
                break;
            case '7':
                //选择7：查看产品分类
                new ProductClassification();
                Cleaner.getCleaner().Clean();
                new MainView().UserMain();
                break;
            case '8':
                //选择8：真伪验证
                new AlreadyPurchasedByUser().VerificationOfAuthenticity();
                Cleaner.getCleaner().Clean();
                new MainView().UserMain();
                break;
            default:
                JOptionPane.showMessageDialog(null,"格式错误,请重新输入！");
                Cleaner.getCleaner().Clean();
                new MainView().UserMain();
                break;
        }

    }


    public void ProUserMainViewHandler(){
        System.out.print("                                        请选择您要进行的操作（请输入数字作为您的选择）：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);
        switch (select){
            case '1':
                //选择1：查看我的用户信息
                new MainViewService().CheckProUser();
                Cleaner.getCleaner().Clean();
                new MainView().ProductMain();
                break;
            case '2':
                //选择2：修改用户信息
                new ChangeProUser();
                break;
            case '3':
                //选择3：上传商品信息
                new CreateGoods();
                break;
            case '4':
                //选择4：修改我的商品信息
                System.out.println();
                System.out.println("                                        正在加载中，不要退出控制台界面，请耐心等待！");
                new ReviseProductInformation(proUserDTO);
                break;
            case '5':
                //查看我的商品信息
                System.out.println();
                System.out.println("                                        正在加载中，不要退出控制台界面，请耐心等待！");
                new CheckProductByProUser().CheckProduct();

                Cleaner.getCleaner().Clean();
                new MainView().ProductMain();
                break;
            case '6':
                //选择6：查看举报或点赞信息
                new CheckReportMessage().CheckSupport();
                new CheckReportMessage().CheckReport();

                Cleaner.getCleaner().BackMain();
                Cleaner.getCleaner().Clean();
                new MainView().ProductMain();
                break;
            case '7':
                //选择7：查看产品分类
                new ProductClassification();
                Cleaner.getCleaner().BackMain();
                Cleaner.getCleaner().Clean();
                new MainView().ProductMain();
                break;
            default:
                JOptionPane.showMessageDialog(null,"格式错误,请重新输入！");
                Cleaner.getCleaner().Clean();
                new MainView().ProductMain();
                break;
        }

    }


    public void CheckProUser(){

        Cleaner.getCleaner().Clean();
        proUserDTO.setBalance(Integer.valueOf(new ChangeOnFisco().CreateErc20(proUserDTO.getHash())));
        System.out.println("                                        您的账户信息为：");
        System.out.println("                                        =======================================================");
        System.out.println("                                        账号："+ proUserDTO.getUserName());
        System.out.println("                                        账号哈希："+ proUserDTO.getHash());
        System.out.println("                                        账户余额："+ proUserDTO.getBalance()+"  ETH");
        System.out.println("                                        委托商："+ proUserDTO.getProManager());
        System.out.println("                                        联系电话："+ proUserDTO.getProPhone());
        System.out.println("                                        收货地址："+ proUserDTO.getProHome());
        System.out.println("                                        =======================================================");
        Cleaner.getCleaner().BackMain();

    }


    public void CheckUser(){
        Cleaner.getCleaner().Clean();
        userDTO.setBalance(Integer.valueOf(new ChangeOnFisco().CreateErc20(userDTO.getHash())));
        System.out.println("                                        您的账户信息为：");
        System.out.println("                                        =======================================================");
        System.out.println("                                        账号："+ userDTO.getUserName());
        System.out.println("                                        账号哈希："+ userDTO.getHash());
        System.out.println("                                        账户余额："+ userDTO.getBalance()+"   ETH");
        System.out.println("                                        姓名："+ userDTO.getName());
        System.out.println("                                        联系电话："+ userDTO.getPhone());
        System.out.println("                                        收货地址："+ userDTO.getHome());
        System.out.println("                                        =======================================================");
        Cleaner.getCleaner().BackMain();

    }
}
