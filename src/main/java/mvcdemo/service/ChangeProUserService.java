package mvcdemo.service;

import mvcdemo.service.impl.MysqlService;
import mvcdemo.service.impl.MysqlServiceImpl;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.util.Cleaner;
import mvcdemo.util.contractRealize.ChangeOnFisco;
import mvcdemo.view.ChangeProUser;
import mvcdemo.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Xenqiao
 * @create 2023/3/20 20:48
 */
public class ChangeProUserService implements ActionListener{
    private ChangeProUser changeProUser;
    public ChangeProUserService(ChangeProUser changeProUser) {
        this.changeProUser = changeProUser;
    }
    ProUserDTO proUserDTO = ProUserDTO.getProUserDO();

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();

        //此处负责将数据传入fisco链
        changeProUser.SetManage();
        new ChangeOnFisco().ChangeProUserOnFisco(proUserDTO);

        if ("确认修改".equals(text)) {

            //此处将数据传入MySQL的数据库同时返回boolean值判断是否成功
            MysqlService mysqlService = new MysqlServiceImpl();
            boolean addResult = mysqlService.addProUser(proUserDTO);

            if (addResult) {
                JOptionPane.showMessageDialog(changeProUser, "修改成功！");
                changeProUser.dispose();
            } else {
                JOptionPane.showMessageDialog(changeProUser, "修改失败！");
            }
            Cleaner.getCleaner().Clean();
            new MainView().ProductMain();
        }
    }
}
