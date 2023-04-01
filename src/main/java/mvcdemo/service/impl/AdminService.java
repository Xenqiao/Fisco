package mvcdemo.service.impl;

import mvcdemo.po.ProUserDO;
import mvcdemo.po.UserDO;

/**
 * @author 谢金桥
 */
public interface AdminService {
    boolean getUserHash(UserDO userDO) throws Exception;
    boolean loGin(String lname,String lpassword, int change) throws Exception;
    boolean adminLogon(String useName,int change)throws Exception;
    String getProducerHash(ProUserDO proUserDO);
}
