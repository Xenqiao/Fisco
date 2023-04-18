package mvcdemo.service;

import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.ProductDTO;
import mvcdemo.dto.UserDTO;

/**
 * @author Xenqiao
 * @create 2023/3/20 22:32
 */
public interface MysqlService {
    /**
     * 修改消费者的信息
     * @param userDTO
     * @return
     */
    boolean modifyUser(UserDTO userDTO);

    /**
     * 修改生产者的信息
     * @param proUserDTO
     * @return
     */
    boolean modifyProUser(ProUserDTO proUserDTO);

    /**
     * 该函数用于添加商品
     * @param productDTO
     * @return
     */
    boolean addProduct(ProductDTO productDTO);
    boolean reviseProduct(ProductDTO productDTO);
    boolean printProduct(ProductDTO productDTO, String sql, int branch);
    boolean addProUser(ProUserDTO proUserDTO);
    boolean addUser(UserDTO userDTO);
}
