package mvcdemo.service;

import mvcdemo.po.ProUserDO;
import mvcdemo.po.UserDO;
import mvcdemo.view.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Xenqiao
 * @create 2023/3/31 17:21
 */
public class CloseWindow extends WindowAdapter {
    private UserDO userDO;
    private ProUserDO proUserDO;
    private CreateGoods createGoods;
    private ChangeUser changeUser;
    private ChangeProUser changeProUser;
    private ReviseProductInformation reviseProductInformation;

    public CloseWindow(
            UserDO userDO,
            ProUserDO proUserDO,
            CreateGoods createGoods,
            ChangeUser changeUser,
            ChangeProUser changeProUser,
            ReviseProductInformation reviseProductInformation
    ) {
        this.userDO = userDO;
        this.proUserDO = proUserDO;

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

        if (proUserDO==null){

            new Cleaner();
            new UserMain().UserMain(userDO);
        }else if (userDO==null){

            new Cleaner();
            new UserMain().ProductMain(proUserDO);
        }

    }
}
