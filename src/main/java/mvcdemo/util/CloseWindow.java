package mvcdemo.util;

import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.UserDTO;
import mvcdemo.view.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Xenqiao
 * @create 2023/3/31 17:21
 */
public class CloseWindow extends WindowAdapter {

    private CreateGoods createGoods;
    private ChangeUser changeUser;
    private ChangeProUser changeProUser;
    private ReviseProductInformation reviseProductInformation;


    /** 本函数主要用于监听（来自各个 JDialog界面的关闭）鼠标事件，
     *  确保关闭界面以后控制台能够正常自动返回主菜单界面 **/
    public CloseWindow(
            CreateGoods createGoods,
            ChangeUser changeUser,
            ChangeProUser changeProUser,
            ReviseProductInformation reviseProductInformation
    ) {

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
        UserDTO userDTO = UserDTO.getUserDO();

        if ( proUserDTO.getUserName()==null ){
            Cleaner.getCleaner().Clean();
            new MainView().UserMain();
        }else if ( userDTO.getUserName()==null ){

            Cleaner.getCleaner().Clean();
            new MainView().ProductMain();
        }

    }
}
