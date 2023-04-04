package mvcdemo.util;

import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.UserDTO;
import mvcdemo.view.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Xenqiao
 * @create 2023/3/31 17:21
 */
public class CloseWindow extends WindowAdapter {
    private UserDTO userDTO;
    private CreateGoods createGoods;
    private ChangeUser changeUser;
    private ChangeProUser changeProUser;
    private ReviseProductInformation reviseProductInformation;

    public CloseWindow(
            UserDTO userDTO,
            CreateGoods createGoods,
            ChangeUser changeUser,
            ChangeProUser changeProUser,
            ReviseProductInformation reviseProductInformation
    ) {
        this.userDTO = userDTO;
        this.createGoods = createGoods;
        this.changeUser = changeUser;
        this.changeProUser = changeProUser;
        this.reviseProductInformation = reviseProductInformation;
    }


    @Override
    public void windowClosing(WindowEvent e){
        // 在这里实现需要执行的操作
        if (changeProUser==null && changeUser==null && reviseProductInformation==null){
            createGoods.dispose();
        }else if (createGoods==null && changeUser==null && reviseProductInformation==null){
            changeProUser.dispose();
        }else if (changeProUser==null && createGoods==null && reviseProductInformation==null){
            changeUser.dispose();
        }else if (changeProUser==null && createGoods==null && changeUser==null){
            reviseProductInformation.dispose();
        }

        ProUserDTO proUserDTO = ProUserDTO.getProUserDO();
        if (proUserDTO ==null){
            Cleaner.Clean();
            new MainView().UserMain();
        }else if (userDTO ==null){

            Cleaner.Clean();
            new MainView().ProductMain();
        }

    }
}
