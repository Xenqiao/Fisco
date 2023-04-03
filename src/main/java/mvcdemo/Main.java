package mvcdemo;


import mvcdemo.po.ProUserDO;
import mvcdemo.view.CreateGoods;

public class Main {


    /**
     * 测试的方法
     * @param args
     */
    public static void main(String[] args) {
        new CreateGoods(new ProUserDO());

    }
}
