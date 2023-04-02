package mvcdemo.service;


import mvcdemo.dao.mysql.impl.MysqlService;
import mvcdemo.dao.mysql.impl.MysqlServiceImpl;
import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.util.contractRealize.GetBcosSDK;
import mvcdemo.util.toolcontract.Product;
import mvcdemo.view.ReviseProductInformation;
import mvcdemo.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author Xenqiao
 * @create 2023/3/26 20:21
 */
public class ReviseProductInformationHandler implements ActionListener {
    private ReviseProductInformation reviseProductInformation;
    private ProductDO productDO;
    private ProUserDO proUserDO;
    public ReviseProductInformationHandler(ReviseProductInformation reviseProductInformation, ProductDO productDO, ProUserDO proUserDO) {
        this.reviseProductInformation = reviseProductInformation;
        this.productDO = productDO;
        this.proUserDO = proUserDO;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();

        reviseProductInformation.SetManage(productDO);


        Product product = new Product(productDO.getProductHash(), GetBcosSDK.getClient(),GetBcosSDK.getKeyPair());
        product.setProduct(productDO.getProductHash(), productDO.getProductName(), productDO.getProductPrice(), productDO.getProductPlace(), productDO.getProductMake(), productDO.getProductId());

        if ("确认修改".equals(text)) {
            JOptionPane.showMessageDialog(reviseProductInformation,"数据将更新上传至区块链，请等待几分钟。");

            MysqlService mysqlService = new MysqlServiceImpl();
            boolean addResult = mysqlService.ReviseProduct(productDO);

            if (addResult) {
                JOptionPane.showMessageDialog(reviseProductInformation, "修改成功！您的商品哈希为："+productDO.getProductHash());
                reviseProductInformation.dispose();
            } else {
                JOptionPane.showMessageDialog(reviseProductInformation, "上传失败！");
            }
            Cleaner.Clean();
            new MainView().ProductMain(proUserDO);
        }
    }


}
