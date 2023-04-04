package mvcdemo.service;


import mvcdemo.service.impl.MysqlService;
import mvcdemo.service.impl.MysqlServiceImpl;
import mvcdemo.dto.ProductDTO;
import mvcdemo.util.Cleaner;
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
public class ReviseProductInformationService implements ActionListener {
    private ReviseProductInformation reviseProductInformation;
    private ProductDTO productDTO;

    public ReviseProductInformationService(ReviseProductInformation reviseProductInformation, ProductDTO productDTO) {
        this.reviseProductInformation = reviseProductInformation;
        this.productDTO = productDTO;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();

        reviseProductInformation.SetManage(productDTO);


        Product product = new Product(productDTO.getProductHash(), GetBcosSDK.theGetBcosSDK().getClient(),GetBcosSDK.theGetBcosSDK().getKeyPair());
        product.setProduct(productDTO.getProductHash(), productDTO.getProductName(), productDTO.getProductPrice(), productDTO.getProductPlace(), productDTO.getProductMake(), productDTO.getProductId());

        if ("确认修改".equals(text)) {
            JOptionPane.showMessageDialog(reviseProductInformation,"数据将更新上传至区块链，请等待几分钟。");

            MysqlService mysqlService = new MysqlServiceImpl();
            boolean addResult = mysqlService.ReviseProduct(productDTO);

            if (addResult) {
                JOptionPane.showMessageDialog(reviseProductInformation, "修改成功！您的商品哈希为："+ productDTO.getProductHash());
                reviseProductInformation.dispose();
            } else {
                JOptionPane.showMessageDialog(reviseProductInformation, "上传失败！");
            }
            Cleaner.Clean();
            new MainView().ProductMain();
        }
    }


}
