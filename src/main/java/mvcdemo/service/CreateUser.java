package mvcdemo.service;

import mvcdemo.util.contractRealize.GetBcosSDK;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.ECDSAKeyPair;
import org.fisco.bcos.sdk.crypto.keystore.KeyTool;
import org.fisco.bcos.sdk.crypto.keystore.PEMKeyStore;
import org.fisco.bcos.sdk.model.CryptoType;

import java.security.KeyPair;

/**
 * @author Xenqiao
 * @create 2023/3/18 22:43
 */
public class CreateUser {

    /**创建非国密类型的 CryptoSuite */
    CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
    /** 随机生成非国密公私钥对 */
    CryptoKeyPair cryptoKeyPair = cryptoSuite.getCryptoKeyPair();
    /** 获取账户地址 */
    String accountAddress = cryptoKeyPair.getAddress();

    public String GetUserHash() {
        StringBuilder pemFilePath = new StringBuilder();
        pemFilePath.append("src/main/resource/solidity/pem/"+accountAddress+".pem");

        saveAccountWithPem(cryptoKeyPair,pemFilePath.toString());
        loadPemAccount(GetBcosSDK.getClient(),pemFilePath.toString());
        saveAccountWithPemToKeyStoreDir(cryptoKeyPair);
        loadPem(pemFilePath.toString());
        return accountAddress;

    }

    /**
    直接加载非国密私钥
     */
    public CryptoKeyPair loadECDAAccountFromHexPrivateKey(String hexPrivateKey){
        //创建国密类型的keyFacotry
        ECDSAKeyPair keyFacotry = new ECDSAKeyPair();
        //从十六进制字符串加载hexPrivateKey
        return keyFacotry.createKeyPair(hexPrivateKey);
    }


    /** 从 pemAccountFilePath指定路径加载 pem账户文件，并将其设置为交易发送账户 */
    public void loadPemAccount(Client client, String pemAccountFilePath){
        //通过client获取CryptoSuite对象
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        //加载pem账户文件
        cryptoSuite.loadAccount("pem",pemAccountFilePath,null);
    }


    /** 将随机生成的账户信息保存在pemFilePath指定的路径 */
    public void saveAccountWithPem(CryptoKeyPair cryptoKeyPair, String pemFilePath)
    {
        // 以pem的格式保存账户文件到pemFilePath路径
        cryptoKeyPair.storeKeyPairWithPem(pemFilePath);
    }

    /** 将随机生成的账户信息保存在账户配置${keyStoreDir}指定的目录下 */
    public void saveAccountWithPemToKeyStoreDir(CryptoKeyPair cryptoKeyPair)
    {
        // 账户文件名为${accountAddress}.pem
        cryptoKeyPair.storeKeyPairWithPemFormat();
    }

    /** 从指定文件加载 pem文件 */
    public KeyTool loadPem(String pemFilePath)
    {
        return new PEMKeyStore(pemFilePath);
    }

    /** KeyTool中维护的是非国密公私钥信息 */
    public CryptoKeyPair loadKeyStore(KeyTool keyTool)
    {

        KeyPair keyPair = keyTool.getKeyPair();
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        return cryptoSuite.createKeyPair(keyPair);
    }
}
