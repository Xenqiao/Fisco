package mvcdemo;


import mvcdemo.service.impl.MysqlServiceImpl;
import mvcdemo.util.contractRealize.ChangeOnFisco;
import mvcdemo.util.contractRealize.GetBcosSDK;
import mvcdemo.util.toolcontract.User;

/**
 * @author
 */
public class Main {


    /**
     * 测试的方法
     * @param args
     */
    public static void main(String[] args) {

        String a = new ChangeOnFisco().CreateErc20("0x14e25726cca3e9650dd73be8cfed68c510fdc785");
        System.out.println(a);
    }
}
