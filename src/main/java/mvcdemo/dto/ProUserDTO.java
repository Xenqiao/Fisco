package mvcdemo.dto;

/**
 * @author Xenqiao
 * @create 2023/3/19 18:11
 * 生产商对象，记录了生产者对应的各种参数
 */
public class ProUserDTO {
    private String userName;
    private String pwd;
    private String hash;
    private String proManager;
    private String proPhone;
    private String proHome;
    private Integer balance;
    private String proAlreadyPurchased;
    private String rMessage;
    private String sMessage;

    /**  单例模式的双重检查  **/
    private static volatile ProUserDTO proUserDTO;
    private ProUserDTO(){
    }
    public static ProUserDTO getProUserDO(){
        if (proUserDTO == null){
            synchronized (ProUserDTO.class){
                if (proUserDTO == null){
                    proUserDTO = new ProUserDTO();
                }
            }
        }
        return proUserDTO;
    }

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
                ", rMessage='" + rMessage + '\'' +
                ", sMessage='" + sMessage + '\'' +
                '}';
    }

    public String getrMessage() {
        return rMessage;
    }

    public void setrMessage(String rMessage) {
        this.rMessage = rMessage;
    }

    public String getsMessage() {
        return sMessage;
    }

    public void setsMessage(String sMessage) {
        this.sMessage = sMessage;
    }

    public static void setProUserDO(ProUserDTO proUserDTO) {
        ProUserDTO.proUserDTO = proUserDTO;
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
