package mvcdemo.service;


import mvcdemo.dao.mysql.impl.UserService;
import mvcdemo.dao.mysql.impl.UserServiceImpl;
import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.util.toolcontract.Product;
import mvcdemo.view.ReviseProductInformation;
import mvcdemo.view.UserMain;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;

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


    String configFile = "src/main/resource/config.toml";
    BcosSDK sdk = BcosSDK.build(configFile);
    Client client = sdk.getClient(1);
    CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();



    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();

        reviseProductInformation.SetManage(productDO);


        Product product = new Product(productDO.getProductHash(),client,keyPair);
        product.setProduct(productDO.getProductHash(), productDO.getProductName(), productDO.getProductPrice(), productDO.getProductPlace(), productDO.getProductMake(), productDO.getProductId());

        if ("确认修改".equals(text)) {
            JOptionPane.showMessageDialog(reviseProductInformation,"数据将更新上传至区块链，请等待几分钟。");

            UserService userService = new UserServiceImpl();
            boolean addResult = userService.ReviseProduct(productDO);

            if (addResult) {
                JOptionPane.showMessageDialog(reviseProductInformation, "修改成功！您的商品哈希为："+productDO.getProductHash());
                reviseProductInformation.dispose();
            } else {
                JOptionPane.showMessageDialog(reviseProductInformation, "上传失败！");
            }
            new Cleaner();
            new UserMain().ProductMain(proUserDO);
        }
    }


}
