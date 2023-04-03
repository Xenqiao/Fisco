package mvcdemo.view;

import mvcdemo.po.ProUserDO;
import mvcdemo.po.ProductDO;
import mvcdemo.service.CloseWindow;
import mvcdemo.service.CreateGoodsHandler;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

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
    JLabel classLabel = new JLabel("产品类别(数字)：",JLabel.RIGHT);
    JTextField classTxt = new JTextField();
    JLabel classTips1 = new JLabel("1.美妆类   2.数码产品类   3.食品生鲜类",JLabel.CENTER);
    JLabel classTips2 = new JLabel("4.服饰类   5.进口零食类   6.蔬菜类",JLabel.CENTER);


    JButton addBtn = new JButton("确认");

    public CreateGoods(ProUserDO proUserDO){

        Font font = new Font("微软雅黑",Font.PLAIN,20);

        ProductDO productDO = new ProductDO();
        productDO.setProductMakePhone(proUserDO.getProPhone());
        productDO.setProductMake(proUserDO.getProManager());
        productDO.setProductConner(proUserDO.getUserName());
        int heigth = 35;


        nameLabel.setPreferredSize(new Dimension(110,heigth));
        nameLabel.setFont(font);
        jPanel.add(nameLabel);
        nameTxt.setPreferredSize(new Dimension(260,heigth));
        jPanel.add(nameTxt);

        priceLabel.setPreferredSize(new Dimension(200,heigth));
        priceLabel.setFont(font);
        jPanel.add(priceLabel);
        priceTxt.setPreferredSize(new Dimension(170,heigth));
        jPanel.add(priceTxt);


        homeLabel.setPreferredSize(new Dimension(110,heigth));
        homeLabel.setFont(font);
        jPanel.add(homeLabel);
        homeTxt.setPreferredSize(new Dimension(260,heigth));
        jPanel.add(homeTxt);


        classLabel.setPreferredSize(new Dimension(200,heigth));
        classLabel.setFont(font);
        jPanel.add(classLabel);
        classTxt.setPreferredSize(new Dimension(170,heigth));
        jPanel.add(classTxt);

        classTips1.setPreferredSize(new Dimension(400,40));
        classTips1.setFont(font);
        jPanel.add(classTips1);

        classTips2.setPreferredSize(new Dimension(400,40));
        classTips2.setFont(font);
        jPanel.add(classTips2);


        //内容面板
        Container contentPane = getContentPane();
        setTitle("上传新的商品");

        contentPane.add(jPanel);

        setSize(420,500);
        setLocationRelativeTo(null);


        CreateGoodsHandler createGoodsHandler = new CreateGoodsHandler(this,productDO,proUserDO);
        addBtn.setFont(font);
        addBtn.addActionListener(createGoodsHandler);

        jPanel.add(addBtn);

        //DISPOSE_ON_CLOSE：关闭时只销毁当前窗口

        addWindowListener(new CloseWindow(null,proUserDO,this,null,null,null));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);


    }

    public boolean SetManage(ProductDO productDO){

        //判断输入是否为空
        boolean judge1 = nameTxt.getText()==null || "".equals(nameTxt.getText());
        boolean judge2 = nameTxt.getText()==null || "".equals(nameTxt.getText());
        boolean judge3 = nameTxt.getText()==null || "".equals(nameTxt.getText());
        boolean judge4 = nameTxt.getText()==null || "".equals(nameTxt.getText());
        //正则表达式判断字符串是否为数字
        Pattern pattern = compile("[0-9]*");
        if (judge1 || judge2 || judge3 || judge4){
            return false;
        }else if (!pattern.matcher(priceTxt.getText()).matches() || !pattern.matcher(classTxt.getText()).matches()){
            return false;
        }
        productDO.setProductName(nameTxt.getText());
        productDO.setProductPrice(Integer.valueOf(priceTxt.getText()));
        productDO.setProductPlace(homeTxt.getText());
        productDO.setProductClass(classTxt.getText());
        return true;
    }
}
