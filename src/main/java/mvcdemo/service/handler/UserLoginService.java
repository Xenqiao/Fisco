package mvcdemo.service.handler;

import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.UserDTO;
import mvcdemo.service.AdminService;
import mvcdemo.service.impl.AdminServiceImpl;
import mvcdemo.service.impl.ProductLogonServiceServiceImpl;
import mvcdemo.service.impl.UserLogonServiceServiceImpl;
import mvcdemo.util.Cleaner;
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
public class UserLoginService extends KeyAdapter implements ActionListener {
    private UserLogin loginView;


    public UserLoginService(UserLogin loginView){
        this.loginView = loginView;
    }
    UserDTO userDTO = UserDTO.getUserDO();
    ProUserDTO proUserDTO = ProUserDTO.getProUserDO();

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


    /** 用于管理注册的函数，能够接管和识别登录者的身份，同时根据此进行不同的操作 **/
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
        String hash = new CreateUserService().GetUserHash();
        boolean require = false;

        //  =1为进入消费者注册程序
        //  =2为进入生产者注册程序
        if (chance == 1){

            userDTO.setUserName(userName);
            userDTO.setPwd(pwd);
            userDTO.setHash(hash);
            require = new UserLogonServiceServiceImpl().add(userDTO);
        }else if (chance == 2){
            proUserDTO.setUserName(userName);
            proUserDTO.setPwd(pwd);
            proUserDTO.setHash(hash);
            require = new ProductLogonServiceServiceImpl().addPro(proUserDTO);
        }

        if (require) {
            JOptionPane.showMessageDialog(loginView, "注册成功,您的账号哈希为：" + hash);
            JOptionPane.showMessageDialog(loginView, "接下来请转入控制台界面！");
            loginView.dispose();
            if (chance == 1){
                Cleaner.getCleaner().Clean();
                new MainView().UserMain();
            }else{
                Cleaner.getCleaner().Clean();
                new MainView().ProductMain();
            }
        }else {
            JOptionPane.showMessageDialog(loginView, "注册失败！");
            return;
        }
    }


    /** 用于管理登录的函数  **/
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
            userDTO.setUserName(user);
            userDTO.setPwd(pwd);
            try {
                flag = adminService.loGin(userDTO.getUserName(), userDTO.getPwd(), change);
                adminService.getUserHash(userDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //生产者的登录
        }else if (change == 2){
            proUserDTO.setUserName(user);
            proUserDTO.setPwd(pwd);

            try {
                flag = adminService.loGin(proUserDTO.getUserName(), proUserDTO.getPwd(),change);
            } catch (Exception e) {
                e.printStackTrace();
            }
            adminService.getProducerHash();
        }


        if (flag){
            //跳转到主界面并且销毁登陆界面
            //new MainView();

            System.out.println(userDTO.getUserName()==null);
            System.out.println(proUserDTO.getUserName()==null);
            JOptionPane.showMessageDialog(loginView,"登陆成功，等待三分钟后将进入控制台界面！");
            GetBcosSDK.theGetBcosSDK().getClient();
            GetBcosSDK.theGetBcosSDK().getKeyPair();
            loginView.dispose();

            if (change == 1){
                Cleaner.getCleaner().Clean();
                new MainView().UserMain();
            }else{
                Cleaner.getCleaner().Clean();
                new MainView().ProductMain();
            }

        }else {
            //一个错误提示
            JOptionPane.showMessageDialog(loginView,"用户名密码错误,请重新输入！");
            return;
        }

    }

    /** 鼠标事件监听，点击一次即可触发 **/
    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.VK_ENTER == e.getKeyCode()){
            int i = 1;
            login(1);
        }
    }
}
