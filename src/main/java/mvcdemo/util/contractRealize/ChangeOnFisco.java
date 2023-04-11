package mvcdemo.util.contractRealize;

import mvcdemo.dto.ProUserDTO;
import mvcdemo.dto.ProductDTO;
import mvcdemo.dto.UserDTO;
import mvcdemo.util.toolcontract.Erc20;
import mvcdemo.util.toolcontract.ProUser;
import mvcdemo.util.toolcontract.Product;
import mvcdemo.util.toolcontract.User;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.util.ArrayList;

/**
 * @author Xenqiao
 * @create 2023/3/21 21:44
 */
public class ChangeOnFisco {
    public ChangeOnFisco() { }
    /**区块链的初始化
     *
     */

    public String CreatingBlocks(String contractName){
        try {
            AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(GetBcosSDK.theGetBcosSDK().getClient(), GetBcosSDK.theGetBcosSDK().getKeyPair(), "src/main/resources/solidity/abi", "src/main/resources/solidity/bin");
            TransactionResponse response = transactionProcessor.deployByContractLoader(contractName, new ArrayList<>());
            String Address = response.getContractAddress();
            return Address;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /** 一个在链上改变用户信息的函数 */
    public void ChangeUserOnFisco(UserDTO userDTO){

        String Address = userDTO.getHash();
        User user = new User(Address, GetBcosSDK.theGetBcosSDK().getClient(), GetBcosSDK.theGetBcosSDK().getKeyPair());
        String msgSender = user.setSender(Address).getOutput();
        System.out.println("msg.sender:  "+msgSender);

        user.setUser(
                userDTO.getHash(),
                userDTO.getUserName(),
                userDTO.getName(),
                userDTO.getPhone(),
                userDTO.getHome()
        );
    }


    /** 一个在链上改变生产商信息的函数 */
    public void ChangeProUserOnFisco(ProUserDTO proUserDTO){

            String Address = proUserDTO.getHash();
            ProUser proUser = new ProUser(Address,GetBcosSDK.theGetBcosSDK().getClient(),GetBcosSDK.theGetBcosSDK().getKeyPair());
            proUser.setProUser(
                    proUserDTO.getHash(),
                    proUserDTO.getUserName(),
                    proUserDTO.getProManager(),
                    proUserDTO.getProPhone(),
                    proUserDTO.getProHome()
            );

    }


    /** 一个在链上创建产品信息的函数 */
    public void GetProductHash(ProductDTO productDTO){

        String Address = new ChangeOnFisco().CreatingBlocks("Product");
        Product product = new Product(Address, GetBcosSDK.theGetBcosSDK().getClient(), GetBcosSDK.theGetBcosSDK().getKeyPair());

        productDTO.setProductHash(product.getContractAddress());
        product.setProduct(
                productDTO.getProductHash(),
                productDTO.getProductName(),
                productDTO.getProductPrice(),
                productDTO.getProductPlace(),
                productDTO.getProductMake(),
                productDTO.getProductId()
        );
    }


    /** 在 erc20合约上完成各种操作的函数，留存的地址用于返回最初创建的合约，或者访问合约的管理员账户  **/
    public String CreateErc20(String hash){

        String a = "0x10e47f5aa26b58f53e055878148ed47529a24099";
        String contractAddress = "0x3fb7e04e2ff1a5796ff62348c6dc2b05f684118c";

        //String contractAddress = new ChangeOnFisco().CreatingBlocks("Erc20");

        Erc20 erc20 = new Erc20(contractAddress, GetBcosSDK.theGetBcosSDK().getClient(), GetBcosSDK.theGetBcosSDK().getKeyPair());
        try {
            //erc20.transferFrom(a,hash,BigInteger.valueOf(500));
            return erc20.balanceOf(hash).toString();

        } catch (ContractException e) {
            e.printStackTrace();
        }
        return "";

    }
}
