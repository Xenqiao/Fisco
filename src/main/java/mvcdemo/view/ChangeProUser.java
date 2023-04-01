package mvcdemo.view;

import mvcdemo.po.ProUserDO;
import mvcdemo.service.ChangeProUserHandler;
import mvcdemo.service.CloseWindow;
import mvcdemo.service.CopyJLabel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Xenqiao
 * @create 2023/3/24 22:08
 */
public class ChangeProUser extends JDialog{

    //new FlowLayout(FlowLayout.CENTER,10,20)用于调节组件间的间隔距离
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));

    JLabel newPwdLabel = new JLabel("新的密码：",JLabel.RIGHT);
    JTextField newPwdTxt = new JTextField();
    JLabel nameLabel = new JLabel("委托商名称：",JLabel.RIGHT);
    JTextField nameTxt = new JTextField();
    JLabel homeLabel = new JLabel("生产地址：",JLabel.RIGHT);
    JTextField homeTxt = new JTextField();
    JLabel phoneLabel = new JLabel("联系电话：",JLabel.RIGHT);
    JTextField phoneTxt = new JTextField();
    JButton addBtn = new JButton("确认修改");

    ChangeProUserHandler changeUserHandler;
    public ChangeProUser(ProUserDO proUserDO){
        int heigth = 35;
        //super(mainView,"修改用户信息",true);
        StringBuilder id = new StringBuilder();
        id.append("当前账号："+proUserDO.getUserName());
        JLabel numLabel = new JLabel(id.toString(),JLabel.RIGHT);
        numLabel.setFont(new Font("楷体",Font.PLAIN,20));
        numLabel.setPreferredSize(new Dimension(110,heigth));
        jPanel.add(numLabel);

        StringBuilder hash = new StringBuilder();
        hash.append("账号哈希(双击可复制)："+proUserDO.getHash());
        JLabel hashLabel = new JLabel(hash.toString(),JLabel.RIGHT);
        hashLabel.setPreferredSize(new Dimension(350,heigth));
        //事件监听，由于复制哈希值
        hashLabel.addMouseListener(new CopyJLabel(hashLabel));
        jPanel.add(hashLabel);


        newPwdLabel.setPreferredSize(new Dimension(80,heigth));
        jPanel.add(newPwdLabel);
        newPwdTxt.setPreferredSize(new Dimension(240,heigth));
        jPanel.add(newPwdTxt);

        nameLabel.setPreferredSize(new Dimension(80,heigth));
        jPanel.add(nameLabel);
        nameTxt.setPreferredSize(new Dimension(240,heigth));
        jPanel.add(nameTxt);

        homeLabel.setPreferredSize(new Dimension(80,heigth));
        jPanel.add(homeLabel);
        homeTxt.setPreferredSize(new Dimension(240,heigth));
        jPanel.add(homeTxt);

        phoneLabel.setPreferredSize(new Dimension(80,heigth));
        jPanel.add(phoneLabel);
        phoneTxt.setPreferredSize(new Dimension(240, heigth));
        jPanel.add(phoneTxt);


        //内容面板
        Container contentPane = getContentPane();
        contentPane.add(jPanel);

        setSize(420,500);
        setLocationRelativeTo(null);


        new CopyJLabel(hashLabel);

        changeUserHandler = new ChangeProUserHandler(this,proUserDO);
        addBtn.addActionListener(changeUserHandler);
        jPanel.add(addBtn);

        //DISPOSE_ON_CLOSE：关闭时只销毁当前窗口
        addWindowListener(new CloseWindow(null,proUserDO,null,null,this,null));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    }

    public void SetManage(ProUserDO proUserDO){
        proUserDO.setPwd(newPwdTxt.getText());
        proUserDO.setProManager(nameTxt.getText());
        proUserDO.setProHome(homeTxt.getText());
        proUserDO.setProPhone(phoneTxt.getText());

    }
}
