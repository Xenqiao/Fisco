package mvcdemo.service;

import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.dao.mysql.impl.MysqlService;
import mvcdemo.dao.mysql.impl.MysqlServiceImpl;
import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.util.contractRealize.ChangeOnFisco;
import mvcdemo.view.CreateGoods;
import mvcdemo.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Xenqiao
 * @create 2023/3/20 20:48
 */
public class CreateGoodsHandler implements ActionListener{
    private CreateGoods createGoods;
    private ProductDO productDO;
    private ProUserDO proUserDO;
    public CreateGoodsHandler(CreateGoods createGoods, ProductDO productDO, ProUserDO proUserDO) {
        this.createGoods = createGoods;
        this.productDO = productDO;
        this.proUserDO = proUserDO;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();
        boolean judge = createGoods.SetManage(productDO);

        if (judge == false){
            JOptionPane.showMessageDialog(createGoods,"您可能输入为空或者格式错误，请重新输入");
            return;
        }

        try {
            int totalCount = 0;
            Connection conn = DBUtil.getConn();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) as pid from product;");
            while (rs.next()) {
                totalCount = rs.getInt("pid");
                productDO.setProductId(totalCount+1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if ("确认".equals(text)) {
            JOptionPane.showMessageDialog(createGoods,"数据将更新上传至区块链，请等待几分钟。");
            new ChangeOnFisco().GetProductHash(productDO);
            MysqlService mysqlService = new MysqlServiceImpl();
            boolean addResult = mysqlService.addProduct(productDO);

            if (addResult) {
                JOptionPane.showMessageDialog(createGoods, "添加成功！您的商品哈希为："+productDO.getProductHash());
                createGoods.dispose();
            } else {
                JOptionPane.showMessageDialog(createGoods, "上传失败！");
            }
            Cleaner.Clean();
            new MainView().ProductMain(proUserDO);
        }
    }
}
