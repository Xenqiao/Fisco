package mvcdemo.service;

import mvcdemo.po.ProUserDO;
import mvcdemo.po.UserDO;
import mvcdemo.service.impl.AdminService;
import mvcdemo.service.impl.AdminServiceImpl;
import mvcdemo.service.impl.ProductLogonImpl;
import mvcdemo.service.impl.UserLogonImpl;
import mvcdemo.util.contractRealize.GetBcosSDK;
import mvcdemo.view.UserLogin;
import mvcdemo.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Xenqiao
 * @create 2023/3/16 22:23
 */
public class UserLoginHandler extends KeyAdapter implements ActionListener {
    private UserLogin loginView;


    public UserLoginHandler(UserLogin loginView){
        this.loginView = loginView;
    }
    UserDO userDO = new UserDO();
    ProUserDO proUserDO = new ProUserDO();

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取源，并且强转为JButton
        JButton jButton = (JButton)e.getSource();
        String text = jButton.getText();
        String enable=loginView.getGroup();
        if ("注册".equals(text) && "消费者登录".equals(enable)){
            logon(1);

        }else if("登录".equals(text) && "消费者登录".equals(enable)){
            login(1);

        }else if ("注册".equals(text) && "生产商登录".equals(enable)){
            logon(2);

        }else if ("登录".equals(text) && "生产商登录".equals(enable)){
            login(2);

        }

    }


    private void logon(int chance){
        String userName = loginView.getUserTxt().getText();
        char[] chars = loginView.getPwdField().getPassword();
        String pwd = new String(chars);

        AdminService adminService = new AdminServiceImpl();
        boolean flag = false;
        try {
            flag = adminService.adminLogon(userName,chance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag == true || chars == null ||userName == null || "".equals(userName.trim()) || pwd == null || "".equals(pwd.trim())){
            JOptionPane.showMessageDialog(loginView,"用户名已存在或输入为空，请重新填写");
            return;
        }
        JOptionPane.showMessageDialog(loginView,"请不要关闭窗口，账户合约的创建需要2分钟的时间！");
        String hash = new CreateUser().GetUserHash();
        boolean require = false;

        //  =1为进入消费者注册程序
        //  =2为进入生产者注册程序
        if (chance == 1){

            userDO.setUserName(userName);
            userDO.setPwd(pwd);
            userDO.setHash(hash);
            require = new UserLogonImpl().add(userDO);
        }else if (chance == 2){
            proUserDO.setUserName(userName);
            proUserDO.setPwd(pwd);
            proUserDO.setHash(hash);
            require = new ProductLogonImpl().addPro(proUserDO);
        }

        if (require) {
            JOptionPane.showMessageDialog(loginView, "注册成功,您的账号哈希为：" + hash);
            JOptionPane.showMessageDialog(loginView, "接下来请转入控制台界面！");
            loginView.dispose();
            if (chance == 1){
                Cleaner.Clean();
                new MainView().UserMain(userDO);
            }else{
                Cleaner.Clean();
                new MainView().ProductMain(proUserDO);
            }
        }else {
            JOptionPane.showMessageDialog(loginView, "注册失败！");
            return;
        }
    }

    private void login(int change) {
        String user = loginView.getUserTxt().getText();
        char[] chars = loginView.getPwdField().getPassword();
        String pwd = new String(chars);



        if (chars == null ||user == null || "".equals(user.trim()) || pwd == null || "".equals(pwd.trim())){
            JOptionPane.showMessageDialog(loginView,"用户名或密码错误，请重新填写");
            return ;
        }

        AdminService adminService = new AdminServiceImpl();
        boolean flag = false;
        if(change == 1) {
            userDO.setUserName(user);
            userDO.setPwd(pwd);
            try {
                flag = adminService.loGin(userDO.getUserName(), userDO.getPwd(), change);
                adminService.getUserHash(userDO);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //生产者的登录
        }else if (change == 2){
            proUserDO.setUserName(user);
            proUserDO.setPwd(pwd);

            try {
                flag = adminService.loGin(proUserDO.getUserName(), proUserDO.getPwd(),change);
            } catch (Exception e) {
                e.printStackTrace();
            }
            adminService.getProducerHash(proUserDO);
        }


        if (flag){
            //跳转到主界面并且销毁登陆界面
            //new MainView();

            System.out.println(userDO.getUserName()==null);
            System.out.println(proUserDO.getUserName()==null);
            JOptionPane.showMessageDialog(loginView,"登陆成功，等待三分钟后将进入控制台界面！");
            GetBcosSDK.getClient();
            GetBcosSDK.getKeyPair();
            loginView.dispose();

            if (change == 1){
                Cleaner.Clean();
                new MainView().UserMain(userDO);
            }else{
                Cleaner.Clean();
                new MainView().ProductMain(proUserDO);
            }

        }else {
            //一个错误提示
            JOptionPane.showMessageDialog(loginView,"用户名密码错误,请重新输入！");
            return;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.VK_ENTER == e.getKeyCode()){
            int i = 1;
            login(1);
        }
    }
}
