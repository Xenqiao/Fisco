package mvcdemo.service;

import mvcdemo.dto.UserDTO;

/**
 * @author 谢金桥
 */
public interface AdminService {
    boolean getUserHash(UserDTO userDTO) throws Exception;
    boolean loGin(String lname,String lpassword, int change) throws Exception;
    boolean adminLogon(String useName,int change)throws Exception;
    void getProducerHash();
}
