package mvcdemo.po;

/**
 * @author Xenqiao
 * @create 2023/4/2 11:37
 */
public class GetProUserDO {
    private static String userName;
    private static String pwd;
    private static String hash;
    private static String proManager;
    private static String proPhone;
    private static String proHome;
    private static Integer balance;
    private static String proAlreadyPurchased;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        GetProUserDO.userName = userName;
    }

    public static String getPwd() {
        return pwd;
    }

    public static void setPwd(String pwd) {
        GetProUserDO.pwd = pwd;
    }

    public static String getHash() {
        return hash;
    }

    public static void setHash(String hash) {
        GetProUserDO.hash = hash;
    }

    public static String getProManager() {
        return proManager;
    }

    public static void setProManager(String proManager) {
        GetProUserDO.proManager = proManager;
    }

    public static String getProPhone() {
        return proPhone;
    }

    public static void setProPhone(String proPhone) {
        GetProUserDO.proPhone = proPhone;
    }

    public static String getProHome() {
        return proHome;
    }

    public static void setProHome(String proHome) {
        GetProUserDO.proHome = proHome;
    }

    public static Integer getBalance() {
        return balance;
    }

    public static void setBalance(Integer balance) {
        GetProUserDO.balance = balance;
    }

    public static String getProAlreadyPurchased() {
        return proAlreadyPurchased;
    }

    public static void setProAlreadyPurchased(String proAlreadyPurchased) {
        GetProUserDO.proAlreadyPurchased = proAlreadyPurchased;
    }
}
