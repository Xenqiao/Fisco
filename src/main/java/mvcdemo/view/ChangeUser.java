package mvcdemo.view;

import mvcdemo.dto.UserDTO;
import mvcdemo.service.ChangeUserService;
import mvcdemo.util.CloseWindow;
import mvcdemo.util.CopyJLabel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Xenqiao
 * @create 2023/3/20 20:46
 */
public class ChangeUser extends JDialog {

    //new FlowLayout(FlowLayout.CENTER,10,20)用于调节组件间的间隔距离
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    //JPanel jPanel = new JPanel(new BorderLayout(10,20));

    JLabel newPwdLabel = new JLabel("新的密码：",JLabel.RIGHT);
    JTextField newPwdTxt = new JTextField();
    JLabel nameLabel = new JLabel("姓名：",JLabel.RIGHT);
    JTextField nameTxt = new JTextField();
    JLabel homeLabel = new JLabel("收货地址：",JLabel.RIGHT);
    JTextField homeTxt = new JTextField();
    JLabel phoneLabel = new JLabel("电话号码：",JLabel.RIGHT);
    JTextField phoneTxt = new JTextField();
    JButton addBtn = new JButton("确认修改");

    ChangeUserService changeUserService;
    UserDTO userDTO = UserDTO.getUserDO();


    public  ChangeUser() {

        setTitle("修改用户信息");
        int heigth = 35;
        StringBuilder id = new StringBuilder();
        id.append("当前账号：" + userDTO.getUserName());
        JLabel numLabel = new JLabel(id.toString(), JLabel.RIGHT);
        numLabel.setFont(new Font("楷体", Font.PLAIN, 20));
        numLabel.setPreferredSize(new Dimension(110, heigth));
        jPanel.add(numLabel);

        StringBuilder hash = new StringBuilder();
        hash.append("账号哈希(双击可复制)：" + userDTO.getHash());
        JLabel hashLabel = new JLabel(hash.toString(), JLabel.RIGHT);
        hashLabel.setPreferredSize(new Dimension(350, heigth));
        //事件监听，由于复制哈希值
        hashLabel.addMouseListener(new CopyJLabel(hashLabel));
        jPanel.add(hashLabel);


        newPwdLabel.setPreferredSize(new Dimension(80, heigth));
        jPanel.add(newPwdLabel);
        newPwdTxt.setPreferredSize(new Dimension(240, heigth));
        jPanel.add(newPwdTxt);

        nameLabel.setPreferredSize(new Dimension(80, heigth));
        jPanel.add(nameLabel);
        nameTxt.setPreferredSize(new Dimension(240, heigth));
        jPanel.add(nameTxt);

        homeLabel.setPreferredSize(new Dimension(80, heigth));
        jPanel.add(homeLabel);
        homeTxt.setPreferredSize(new Dimension(240, heigth));
        jPanel.add(homeTxt);

        phoneLabel.setPreferredSize(new Dimension(80, heigth));
        jPanel.add(phoneLabel);
        phoneTxt.setPreferredSize(new Dimension(240, heigth));
        jPanel.add(phoneTxt);


        //内容面板
        Container contentPane = getContentPane();
        contentPane.add(jPanel);

        setSize(420, 500);
        setLocationRelativeTo(null);


        changeUserService = new ChangeUserService(this);
        addBtn.addActionListener(changeUserService);
        jPanel.add(addBtn);

        //DISPOSE_ON_CLOSE：关闭时只销毁当前窗口
        addWindowListener(new CloseWindow(userDTO,null,this,null,null));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    }


    public void SetManage(UserDTO userDTO){
        userDTO.setPwd(newPwdTxt.getText());
        userDTO.setName(nameTxt.getText());
        userDTO.setHome(homeTxt.getText());
        userDTO.setPhone(phoneTxt.getText());

    }

}
