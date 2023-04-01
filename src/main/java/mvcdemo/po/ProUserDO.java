package mvcdemo.po;

/**
 * @author Xenqiao
 * @create 2023/3/19 18:11
 */
public class ProUserDO {
    private String userName;
    private String pwd;
    private String hash;
    private String proManager;
    private String proPhone;
    private String proHome;
    private Integer balance;
    private String proAlreadyPurchased;

    @Override
    public String toString() {
        return "ProUserDO{" +
                "userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", hash='" + hash + '\'' +
                ", proManager='" + proManager + '\'' +
                ", proPhone='" + proPhone + '\'' +
                ", proHome='" + proHome + '\'' +
                ", balance=" + balance +
                ", proAlreadyPurchased='" + proAlreadyPurchased + '\'' +
                '}';
    }

    public String getProAlreadyPurchased() {
        return proAlreadyPurchased;
    }

    public void setProAlreadyPurchased(String proAlreadyPurchased) {
        this.proAlreadyPurchased = proAlreadyPurchased;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getProManager() {
        return proManager;
    }

    public void setProManager(String proManager) {
        this.proManager = proManager;
    }

    public String getProPhone() {
        return proPhone;
    }

    public void setProPhone(String proPhone) {
        this.proPhone = proPhone;
    }

    public String getProHome() {
        return proHome;
    }

    public void setProHome(String proHome) {
        this.proHome = proHome;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
