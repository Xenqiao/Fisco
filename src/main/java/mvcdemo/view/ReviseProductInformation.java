package mvcdemo.view;


import mvcdemo.dao.DBUtil;
import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.ProductDTO;
import mvcdemo.util.CloseWindow;
import mvcdemo.controller.handler.ReviseProductInformationService;

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


    ProductDTO productDTO = new ProductDTO();

    public ReviseProductInformation(ProUserDTO proUserDTO){

        productDTO.setProductMake(proUserDTO.getProManager());
        productDTO.setProductConner(proUserDTO.getUserName());
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


        ReviseProductInformationService reviseProductInformationService = new ReviseProductInformationService(this, productDTO);
        addBtn.addActionListener(reviseProductInformationService);
        jPanel.add(addBtn);

        //DISPOSE_ON_CLOSE：关闭时只销毁当前窗口
        addWindowListener(new CloseWindow(null,null,null,this));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }


    public void SetManage(ProductDTO productDTO){
        Connection conn = DBUtil.getConn();
        productDTO.setProductId(Integer.valueOf(IDTxt.getText()));
        //判断是否为产品的拥有者，是否具有修改资格
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from product WHERE pid="+"'"+IDTxt.getText()+"' ;");
            while (rs.next()){

                if (productDTO.getProductConner().equals(rs.getString("pmaker"))){
                    productDTO.setProductHash(rs.getString("phash"));

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

        productDTO.setProductName(nameTxt.getText());
        productDTO.setProductPrice(Integer.valueOf(priceTxt.getText()));
        productDTO.setProductPlace(homeTxt.getText());
    }


}
