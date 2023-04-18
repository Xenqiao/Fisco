package mvcdemo.view;


import mvcdemo.controller.handler.UserLoginService;
import mvcdemo.util.contractRealize.GetBcosSDK;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

/**
 * @author Xenqiao
 */
public class UserLogin extends JFrame{
    JLabel nameLabel = new JLabel("欢迎使用！登录时请耐心等待");


    /**
     * 弹簧布局设置
     */
    SpringLayout springLayout = new SpringLayout();
    JPanel centerPanel = new JPanel(springLayout);

    /**
     * 用户名以及密码的提示信息和输入框
     */
    JLabel userNameLabel = new JLabel("用户名：");
    JTextField userTxt = new JTextField();
    JLabel pwdLabel = new JLabel("密码：");
    JPasswordField pwdField = new JPasswordField();

    /**
     * 两个按钮：登录以及注册
     */
    JButton loginBtn = new JButton("登录");
    JButton resetBtn = new JButton("注册");

    ButtonGroup group = new ButtonGroup();
    JRadioButton producerLogin = new JRadioButton("生产商登录");
    JRadioButton consumerLogin = new JRadioButton("消费者登录");

    /**
     * 将程序图标放入系统托盘
     */
    SystemTray systemTray;
    TrayIcon trayIcon;
    UserLoginService userLoginService;

    public UserLogin(){
        /*
        窗口标题的命名
         */
        super("链上产品溯源系统");
        Container contentPane = getContentPane();



        //引用loginHandler类里的方法
        userLoginService = new UserLoginService(this);

        /*
          设置标题大小以及字体,整个标题的宽高
         */
        nameLabel.setFont(new Font("华文行楷",Font.PLAIN,40));
        nameLabel.setPreferredSize(new Dimension(0,80));

        /*
         设置标题之外的其他字体
         */
        Font centerFont = new Font("楷体",Font.PLAIN,20);
        userNameLabel.setFont(centerFont);
        pwdLabel.setFont(centerFont);
        pwdField.setFont(centerFont);
        loginBtn.setFont(centerFont);
        resetBtn.setFont(centerFont);

        /*
          设置输入框大小以及尺寸
         */
        userTxt.setPreferredSize(new Dimension(200,30));
        pwdField.setPreferredSize(new Dimension(200,30));

        /*
          将定义好的组件（按钮以及输入框）添加到窗口中
         */
        centerPanel.add(userNameLabel);
        centerPanel.add(userTxt);
        centerPanel.add(pwdLabel);
        centerPanel.add(pwdField);
        group.add(producerLogin);
        group.add(consumerLogin);

        centerPanel.add(producerLogin);
        producerLogin.addActionListener(userLoginService);

        centerPanel.add(consumerLogin);
        consumerLogin.addActionListener(userLoginService);

        //增加事件：鼠标点击登录键
        loginBtn.addActionListener(userLoginService);
        //增加回车时默认键为登录
        loginBtn.addKeyListener(userLoginService);
        centerPanel.add(loginBtn);

        //鼠标点击注册键
        resetBtn.addActionListener(userLoginService);
        centerPanel.add(resetBtn);

        //设置窗体图标
        Image image = Toolkit.getDefaultToolkit().getImage("src/main/resources/picture/sIcon.png");
        setIconImage(image);

        /*
          定义每一个组件的位置
          1.布局 userNameLabel的位置
         */
        Spring childWidth = Spring.sum(Spring.sum(Spring.width(userNameLabel),Spring.width(userTxt)),Spring.constant(20));
        int offsetX = childWidth.getValue()/2;
        springLayout.putConstraint(SpringLayout.WEST,userNameLabel,-offsetX,SpringLayout.HORIZONTAL_CENTER,centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH,userNameLabel,20,SpringLayout.NORTH,centerPanel);

        /*
          2.布局 userTxt（输入框）的位置, pad=0表示对齐
         */
        springLayout.putConstraint(SpringLayout.WEST,userTxt,20,SpringLayout.EAST,userNameLabel);
        springLayout.putConstraint(SpringLayout.NORTH,userTxt,0,SpringLayout.NORTH,userNameLabel);
        /*
          3.布局 pwdLabel位置
         */
        springLayout.putConstraint(SpringLayout.EAST,pwdLabel,0,SpringLayout.EAST,userNameLabel);
        springLayout.putConstraint(SpringLayout.NORTH,pwdLabel,20,SpringLayout.SOUTH,userNameLabel);

        /*
          4.布局 pwdField （输入框）
         */
        springLayout.putConstraint(SpringLayout.WEST,pwdField,20,SpringLayout.EAST,pwdLabel);
        springLayout.putConstraint(SpringLayout.NORTH,pwdField,0,SpringLayout.NORTH,pwdLabel);
        /*
          5.布局loginBtn
         */
        springLayout.putConstraint(SpringLayout.WEST,loginBtn,80,SpringLayout.WEST,pwdLabel);
        springLayout.putConstraint(SpringLayout.NORTH,loginBtn,50,SpringLayout.SOUTH,pwdLabel);
        /*
          6.布局resetBtn
         */
        springLayout.putConstraint(SpringLayout.WEST,resetBtn,50,SpringLayout.EAST,loginBtn);
        springLayout.putConstraint(SpringLayout.NORTH,resetBtn,0,SpringLayout.NORTH,loginBtn);

        //布局选择按钮
        springLayout.putConstraint(SpringLayout.WEST,producerLogin,0,SpringLayout.WEST,userTxt);
        springLayout.putConstraint(SpringLayout.SOUTH,producerLogin,100,SpringLayout.NORTH,userTxt);

        springLayout.putConstraint(SpringLayout.WEST,consumerLogin,110,SpringLayout.WEST,userTxt);
        springLayout.putConstraint(SpringLayout.SOUTH,consumerLogin,100,SpringLayout.NORTH,userTxt);

        contentPane.add(nameLabel,BorderLayout.NORTH);
        contentPane.add(centerPanel,BorderLayout.CENTER);

        //将图标放入系统托盘前先判断系统是否支持,需要抛出一个异常
        if(SystemTray.isSupported()){
            systemTray = SystemTray.getSystemTray();

            Image imgUrl = Toolkit.getDefaultToolkit().getImage("JDBC/pictures/icon.png");
            trayIcon = new TrayIcon(new ImageIcon(imgUrl).getImage());

            //设置托盘图片大小自动缩放
            trayIcon.setImageAutoSize(true);

            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            //增加最小化时销毁资源
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowIconified(WindowEvent e) {
                    UserLogin.this.dispose();
                }
            });
            //增加一个鼠标监听，托盘事件监听
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int clickCount = e.getClickCount();
                    if(clickCount == 1){
                        UserLogin.this.setExtendedState(JFrame.NORMAL);
                    }
                    //需要这一行，确保最小化以后还能从系统托盘点开
                    UserLogin.this.setVisible(true);
                }
            });
        }

        //设置loginBtn为默认按钮
        getRootPane().setDefaultButton(loginBtn);

        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        //fisco链的初始化
        GetBcosSDK.theGetBcosSDK().getClient();
        GetBcosSDK.theGetBcosSDK().getKeyPair();
    }

    public String getGroup(){
        String enable="false";
        Enumeration<AbstractButton> radioBtns=group.getElements();
        while (radioBtns.hasMoreElements()) {
            AbstractButton btn = radioBtns.nextElement();
            if(btn.isSelected()){
                enable=btn.getText();
                break;
            }
        }
        return enable;
    }

    public static void main(String[] args) {
        new UserLogin();
    }

    public JPasswordField getPwdField() {
        return pwdField;
    }

    public void setPwdField(JPasswordField pwdField) {
        this.pwdField = pwdField;
    }

    public JTextField getUserTxt() {
        return userTxt;
    }

    public void setUserTxt(JTextField userTxt) {
        this.userTxt = userTxt;
    }
}
