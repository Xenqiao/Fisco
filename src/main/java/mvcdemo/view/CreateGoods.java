package mvcdemo.view;

import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.service.CloseWindow;
import mvcdemo.service.CreateGoodsHandler;

import javax.swing.*;
import java.awt.*;

/**
 * @author Xenqiao
 * @create 2023/3/24 23:07
 */
public class CreateGoods extends JDialog{

    //new FlowLayout(FlowLayout.CENTER,10,20)用于调节组件间的间隔距离
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));

    JLabel nameLabel = new JLabel("商品名称：",JLabel.RIGHT);
    JTextField nameTxt = new JTextField();
    JLabel priceLabel = new JLabel("商品价格(数字)：",JLabel.RIGHT);
    JTextField priceTxt = new JTextField();
    JLabel homeLabel = new JLabel("发货地址：",JLabel.RIGHT);
    JTextField homeTxt = new JTextField();

    JButton addBtn = new JButton("确认");

    public CreateGoods(ProUserDO proUserDO){

        ProductDO productDO = new ProductDO();
        productDO.setProductMakePhone(proUserDO.getProPhone());
        productDO.setProductMake(proUserDO.getProManager());
        productDO.setProductConner(proUserDO.getUserName());
        int heigth = 35;


        nameLabel.setPreferredSize(new Dimension(80,heigth));
        jPanel.add(nameLabel);
        nameTxt.setPreferredSize(new Dimension(240,heigth));
        jPanel.add(nameTxt);

        priceLabel.setPreferredSize(new Dimension(100,heigth));
        jPanel.add(priceLabel);
        priceTxt.setPreferredSize(new Dimension(220,heigth));
        jPanel.add(priceTxt);


        homeLabel.setPreferredSize(new Dimension(80,heigth));
        jPanel.add(homeLabel);
        homeTxt.setPreferredSize(new Dimension(240,heigth));
        jPanel.add(homeTxt);

        //内容面板
        Container contentPane = getContentPane();
        setTitle("上传新的商品");
        contentPane.add(jPanel);

        setSize(420,500);
        setLocationRelativeTo(null);

        CreateGoodsHandler createGoodsHandler = new CreateGoodsHandler(this,productDO,proUserDO);
        addBtn.addActionListener(createGoodsHandler);
        jPanel.add(addBtn);

        //DISPOSE_ON_CLOSE：关闭时只销毁当前窗口

        addWindowListener(new CloseWindow(null,proUserDO,this,null,null,null));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);


    }

    public void SetManage(ProductDO productDO){
        productDO.setProductName(nameTxt.getText());
        productDO.setProductPrice(Integer.valueOf(priceTxt.getText()));
        productDO.setProductPlace(homeTxt.getText());
    }
}
