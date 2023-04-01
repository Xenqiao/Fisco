package mvcdemo.view;


import mvcdemo.dao.mysql.DBUtil;
import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.service.CloseWindow;
import mvcdemo.service.ReviseProductInformationHandler;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Xenqiao
 * @create 2023/3/26 20:00
 */
public class ReviseProductInformation extends JDialog{
    //new FlowLayout(FlowLayout.CENTER,10,20)用于调节组件间的间隔距离
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));

    JLabel IDLabel = new JLabel("需要修改的产品编号：",JLabel.RIGHT);
    JTextField IDTxt = new JTextField();
    JLabel nameLabel = new JLabel("新的商品名称：",JLabel.RIGHT);
    JTextField nameTxt = new JTextField();
    JLabel priceLabel = new JLabel("新的商品价格(数字)：",JLabel.RIGHT);
    JTextField priceTxt = new JTextField();
    JLabel homeLabel = new JLabel("新的发货地址：",JLabel.RIGHT);
    JTextField homeTxt = new JTextField();
    JButton addBtn = new JButton("确认修改");


    ProductDO productDO = new ProductDO();

    public ReviseProductInformation(ProUserDO proUserDO){

        productDO.setProductMake(proUserDO.getProManager());
        productDO.setProductConner(proUserDO.getUserName());
        int heigth = 35;
        IDLabel.setPreferredSize(new Dimension(130,heigth));
        jPanel.add(IDLabel);
        IDTxt.setPreferredSize(new Dimension(190,heigth));
        jPanel.add(IDTxt);

        nameLabel.setPreferredSize(new Dimension(100,heigth));
        jPanel.add(nameLabel);
        nameTxt.setPreferredSize(new Dimension(220,heigth));
        jPanel.add(nameTxt);

        priceLabel.setPreferredSize(new Dimension(130,heigth));
        jPanel.add(priceLabel);
        priceTxt.setPreferredSize(new Dimension(190,heigth));
        jPanel.add(priceTxt);


        homeLabel.setPreferredSize(new Dimension(100,heigth));
        jPanel.add(homeLabel);
        homeTxt.setPreferredSize(new Dimension(220,heigth));
        jPanel.add(homeTxt);

        //内容面板
        Container contentPane = getContentPane();
        setTitle("修改商品信息");
        contentPane.add(jPanel);

        setSize(420,500);
        setLocationRelativeTo(null);


        ReviseProductInformationHandler reviseProductInformationHandler = new ReviseProductInformationHandler(this,productDO,proUserDO);
        addBtn.addActionListener(reviseProductInformationHandler);
        jPanel.add(addBtn);

        //DISPOSE_ON_CLOSE：关闭时只销毁当前窗口
        addWindowListener(new CloseWindow(null,proUserDO,null,null,null,this));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }


    public void SetManage(ProductDO productDO){
        Connection conn = DBUtil.getConn();
        productDO.setProductId(Integer.valueOf(IDTxt.getText()));
        //判断是否为产品的拥有者，是否具有修改资格
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from product WHERE pid="+"'"+IDTxt.getText()+"' ;");
            while (rs.next()){

                if (productDO.getProductConner().equals(rs.getString("pmaker"))){
                    productDO.setProductHash(rs.getString("phash"));

                }else {

                    JOptionPane.showMessageDialog(this, "修改失败，您并非该产品的创建者！");
                    //DISPOSE_ON_CLOSE：关闭时只销毁当前窗口
                    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    return;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        productDO.setProductName(nameTxt.getText());
        productDO.setProductPrice(Integer.valueOf(priceTxt.getText()));
        productDO.setProductPlace(homeTxt.getText());
    }


}
