package mvcdemo.service;

import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.ProductDTO;
import mvcdemo.dto.UserDTO;

/**
 * @author Xenqiao
 * @create 2023/3/20 22:32
 */
public interface MysqlService {
    boolean add(UserDTO userDTO);
    boolean addProUser(ProUserDTO proUserDTO);
    boolean addProduct(ProductDTO productDTO);
    boolean ReviseProduct(ProductDTO productDTO);
    boolean PrintProduct(ProductDTO productDTO, String sql, int branch);
}
