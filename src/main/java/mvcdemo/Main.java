package mvcdemo;


import mvcdemo.service.impl.MysqlServiceImpl;
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
        boolean a = new MysqlServiceImpl().addProUser(null);
        System.out.println ( a );
    }
}
