package mvcdemo.service;

import mvcdemo.dao.mysql.impl.MysqlService;
import mvcdemo.dao.mysql.impl.MysqlServiceImpl;
import mvcdemo.po.UserDO;
import mvcdemo.util.contractRealize.ChangeOnFisco;
import mvcdemo.view.ChangeUser;
import mvcdemo.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Xenqiao
 * @create 2023/3/20 20:48
 */
public class ChangeUserHandler implements ActionListener{
    private ChangeUser changeUser;
    private UserDO userDO;
    public ChangeUserHandler(ChangeUser changeUser,UserDO userDO) {
        this.changeUser = changeUser;
        this.userDO = userDO;

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();
        changeUser.SetManage(userDO);
        JOptionPane.showMessageDialog(changeUser,"数据将更新上传至区块链，请等待几分钟。");

        new ChangeOnFisco().ChangeUserOnFisco(userDO);
        if (userDO.getPwd().isEmpty() || "".equals(userDO.getPwd().trim())){

            JOptionPane.showMessageDialog(changeUser, "密码不能为空！请重新输入");
            return;
        }

        if ("确认修改".equals(text)) {
            MysqlService mysqlService = new MysqlServiceImpl();
            boolean addResult = mysqlService.add(userDO);

            if (addResult) {
                JOptionPane.showMessageDialog(changeUser, "修改成功！");
                changeUser.dispose();
            } else {
                JOptionPane.showMessageDialog(changeUser, "修改失败！");
            }
            Cleaner.Clean();
            new MainView().UserMain(userDO);

        }
    }
}
