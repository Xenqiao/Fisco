package mvcdemo;


import org.fisco.bcos.sdk.crypto.hash.Keccak256;

/**
 * @author
 */
public class Main {


    /**
     * 测试的方法
     * @param args
     */
    public static void main(String[] args) {
        //账户密码的加密
        //主键自增的添加
        //指定交易账户的设置

        Keccak256 hasher = new Keccak256();
        String se = hasher.hash("13");
        System.out.println(se);

    }


}
