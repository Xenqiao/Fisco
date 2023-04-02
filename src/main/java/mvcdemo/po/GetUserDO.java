package mvcdemo.po;

/**
 * @author Xenqiao
 * @create 2023/4/2 11:34
 */
public class GetUserDO {
    private static String userName;
    private static String pwd;
    private static String hash;
    private static String name;
    private static String phone;
    private static String home;
    private static Integer balance;
    private static String AlreadyPurchased;


    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        GetUserDO.userName = userName;
    }

    public static String getPwd() {
        return pwd;
    }

    public static void setPwd(String pwd) {
        GetUserDO.pwd = pwd;
    }

    public static String getHash() {
        return hash;
    }

    public static void setHash(String hash) {
        GetUserDO.hash = hash;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        GetUserDO.name = name;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        GetUserDO.phone = phone;
    }

    public static String getHome() {
        return home;
    }

    public static void setHome(String home) {
        GetUserDO.home = home;
    }

    public static Integer getBalance() {
        return balance;
    }

    public static void setBalance(Integer balance) {
        GetUserDO.balance = balance;
    }

    public static String getAlreadyPurchased() {
        return AlreadyPurchased;
    }

    public static void setAlreadyPurchased(String alreadyPurchased) {
        AlreadyPurchased = alreadyPurchased;
    }
}
