package mvcdemo.dto;


/**
 * @author Xenqiao
 * @create 2023/3/18 17:38
 */
public class UserDTO {

    private String userName;
    private String pwd;
    private String hash;
    private String name;
    private String phone;
    private String home;
    private Integer balance;
    private String AlreadyPurchased;

    /** 单例模式的双重检查 **/
    private static volatile UserDTO userDTO;
    private UserDTO(){
    }
    public static UserDTO getUserDO(){
        if (userDTO == null){
            synchronized (ProUserDTO.class){
                if (userDTO == null){
                    userDTO = new UserDTO();
                }
            }
        }
        return userDTO;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", hash='" + hash + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", home='" + home + '\'' +
                ", balance=" + balance +
                ", AlreadyPurchased='" + AlreadyPurchased + '\'' +
                '}';
    }

    public String getAlreadyPurchased() {
        return AlreadyPurchased;
    }

    public void setAlreadyPurchased(String alreadyPurchased) {
        AlreadyPurchased = alreadyPurchased;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }



    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
}
