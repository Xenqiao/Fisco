package mvcdemo.service;

import mvcdemo.dao.mysql.impl.MysqlService;
import mvcdemo.dao.mysql.impl.MysqlServiceImpl;
import mvcdemo.po.ProUserDO;
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
public class ChangeProUserHandler implements ActionListener{
    private ChangeProUser changeProUser;
    private ProUserDO proUserDO;
    public ChangeProUserHandler(ChangeProUser changeProUser, ProUserDO proUserDO) {
        this.changeProUser = changeProUser;
        this.proUserDO = proUserDO;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();
        changeProUser.SetManage(proUserDO);
        new ChangeOnFisco().ChangeProUserOnFisco(proUserDO);
        if ("确认修改".equals(text)) {
            MysqlService mysqlService = new MysqlServiceImpl();
            boolean addResult = mysqlService.addProUser(proUserDO);
            JOptionPane.showMessageDialog(changeProUser,"数据将更新上传至区块链，请等待几分钟。");
            if (addResult) {
                JOptionPane.showMessageDialog(changeProUser, "修改成功！");
                changeProUser.dispose();
            } else {
                JOptionPane.showMessageDialog(changeProUser, "修改失败！");
            }
            Cleaner.Clean();
            new MainView().ProductMain(proUserDO);
        }
    }
}
