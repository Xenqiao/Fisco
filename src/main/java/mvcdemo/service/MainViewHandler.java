package mvcdemo.service;

import mvcdemo.po.ProUserDO;
import mvcdemo.po.UserDO;
import mvcdemo.util.contractRealize.ChangeOnFisco;
import mvcdemo.view.*;

import javax.swing.*;
import java.util.Scanner;

/**
 * @author Xenqiao
 * @create 2023/3/20 22:55
 */
public class MainViewHandler {
    public void UserMainViewHandler(UserDO userDO){

        System.out.print("                                        请选择您要进行的操作（请输入数字作为您的选择）：");
        Scanner sc = new Scanner(System.in);
        char select =sc.next().charAt(0);
        switch (select){
            case '1':
                new MainViewHandler().CheckUser(userDO);
                Cleaner.Clean();
                new MainView().UserMain(userDO);
                break;
            case '2':
                new ChangeUser(userDO);
                break;
            case '3':
                System.out.println();
                System.out.println("                                        正在加载中，不要退出控制台界面，请耐心等待！");
                new CheckProductByUser(userDO).CheckProduct();
                Cleaner.Clean();
                new MainView().UserMain(userDO);
                break;
            case '4':
                new AlreadyPurchasedByUser().CheckingAlreadyPurchased();
                Cleaner.Clean();
                new MainView().UserMain(userDO);
                break;
            case '5':
                Cleaner.Clean();
                new ReportProduct().ReportProducts();
                new MainView().UserMain(userDO);
                break;
            case'6':
                break;
            case '7':
                break;
            case '8':
                new AlreadyPurchasedByUser().VerificationOfAuthenticity();
                Cleaner.Clean();
                new MainView().UserMain(userDO);
                break;
            default:
                JOptionPane.showMessageDialog(null,"格式错误,请重新输入！");
                Cleaner.Clean();
                new MainView().UserMain(userDO);
                break;
        }

    }


    public void ProUserMainViewHandler(char select, ProUserDO proUserDO){
        switch (select){
            case '1':
                new MainViewHandler().CheckProUser(proUserDO);
                Cleaner.Clean();
                new MainView().ProductMain(proUserDO);
                break;
            case '2':
                new ChangeProUser(proUserDO);
                break;
            case '3':
                new CreateGoods(proUserDO);
                break;
            case '4':
                System.out.println();
                System.out.println("                                        正在加载中，不要退出控制台界面，请耐心等待！");
                new ReviseProductInformation(proUserDO);
                break;
            case '5':
                //查看我的商品信息
                System.out.println();
                System.out.println("                                        正在加载中，不要退出控制台界面，请耐心等待！");
                new CheckProductByProUser(proUserDO);
                Cleaner.BackMain();
                Cleaner.Clean();
                new MainView().ProductMain(proUserDO);

                break;
            default:
                JOptionPane.showMessageDialog(null,"格式错误,请重新输入！");
                Cleaner.Clean();
                new MainView().ProductMain(proUserDO);
                break;
        }

    }


    public void CheckProUser(ProUserDO proUserDO){
        Cleaner.Clean();
        proUserDO.setBalance(Integer.valueOf(new ChangeOnFisco().CreateErc20(proUserDO.getHash())));
        System.out.println("                                        您的账户信息为：");
        System.out.println("                                        =======================================================");
        System.out.println("                                        账号："+proUserDO.getUserName());
        System.out.println("                                        账号哈希："+proUserDO.getHash());
        System.out.println("                                        账户余额："+proUserDO.getBalance());
        System.out.println("                                        委托商："+proUserDO.getProManager());
        System.out.println("                                        联系电话："+proUserDO.getProPhone());
        System.out.println("                                        收货地址："+proUserDO.getProHome());
        System.out.println("                                        =======================================================");
        Cleaner.BackMain();

    }


    public void CheckUser(UserDO userDO){
        Cleaner.Clean();
        userDO.setBalance(Integer.valueOf(new ChangeOnFisco().CreateErc20(userDO.getHash())));
        System.out.println("                                        您的账户信息为：");
        System.out.println("                                        =======================================================");
        System.out.println("                                        账号："+userDO.getUserName());
        System.out.println("                                        账号哈希："+userDO.getHash());
        System.out.println("                                        账户余额："+userDO.getBalance());
        System.out.println("                                        姓名："+userDO.getName());
        System.out.println("                                        联系电话："+userDO.getPhone());
        System.out.println("                                        收货地址："+userDO.getHome());
        System.out.println("                                        =======================================================");
        Cleaner.BackMain();

    }
}
