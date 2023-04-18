package mvcdemo.controller.handler;

import mvcdemo.service.MysqlService;
import mvcdemo.service.impl.MysqlServiceImpl;
import mvcdemo.dto.UserDTO;
import mvcdemo.util.Cleaner;
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
public class ChangeUserService implements ActionListener{
    private ChangeUser changeUser;
    public ChangeUserService(ChangeUser changeUser) {
        this.changeUser = changeUser;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();

        UserDTO userDTO = UserDTO.getUserDO();
        changeUser.SetManage(userDTO);
        JOptionPane.showMessageDialog(changeUser,"数据将更新上传至区块链，请等待几分钟。");

        new ChangeOnFisco().ChangeUserOnFisco(userDTO);
        if (userDTO.getPwd().isEmpty() || "".equals(userDTO.getPwd().trim())){

            JOptionPane.showMessageDialog(changeUser, "密码不能为空！请重新输入");
            return;
        }

        if ("确认修改".equals(text)) {
            MysqlService mysqlService = MysqlServiceImpl.getMysqlService();
            boolean addResult = mysqlService.modifyUser(userDTO);

            if (addResult) {
                JOptionPane.showMessageDialog(changeUser, "修改成功！");
                changeUser.dispose();
            } else {
                JOptionPane.showMessageDialog(changeUser, "修改失败！");
            }
            Cleaner.getCleaner().Clean();
            new MainView().UserMain();

        }
    }
}
