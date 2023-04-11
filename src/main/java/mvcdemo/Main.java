package mvcdemo;


import mvcdemo.util.contractRealize.GetBcosSDK;
import mvcdemo.util.toolcontract.User;

public class Main {


    /**
     * 测试的方法
     * @param args
     */
    public static void main(String[] args) {
        String a = "0x14e25726cca3e9650dd73be8cfed68c510fdc785";
        User user = new User(a, GetBcosSDK.theGetBcosSDK().getClient(), GetBcosSDK.theGetBcosSDK().getKeyPair());
        System.out.println( user.setSender(a).toString() );
    }
}
