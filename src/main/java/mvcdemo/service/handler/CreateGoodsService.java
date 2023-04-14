package mvcdemo.service.handler;

import mvcdemo.dao.DBUtil;
import mvcdemo.service.MysqlService;
import mvcdemo.service.impl.MysqlServiceImpl;
import mvcdemo.dto.ProductDTO;
import mvcdemo.util.Cleaner;
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
public class CreateGoodsService implements ActionListener{
    private CreateGoods createGoods;
    private ProductDTO productDTO;

    public CreateGoodsService(CreateGoods createGoods, ProductDTO productDTO) {
        this.createGoods = createGoods;
        this.productDTO = productDTO;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();
        boolean judge = createGoods.SetManage(productDTO);

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
                productDTO.setProductId(totalCount+1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if ("确认".equals(text)) {
            JOptionPane.showMessageDialog(createGoods,"数据将更新上传至区块链，请等待几分钟。");
            new ChangeOnFisco().GetProductHash(productDTO);
            MysqlService mysqlService = new MysqlServiceImpl();
            boolean addResult = mysqlService.addProduct(productDTO);

            if (addResult) {
                JOptionPane.showMessageDialog(createGoods, "添加成功！您的商品哈希为："+ productDTO.getProductHash());
                createGoods.dispose();
            } else {
                JOptionPane.showMessageDialog(createGoods, "上传失败！");
            }
            Cleaner.getCleaner().Clean();
            new MainView().ProductMain();
        }
    }
}
