package mvcdemo.util.contractRealize;

import mvcdemo.dto.ProUserDTO;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;

/**
 * @author Xenqiao
 * @create 2023/4/1 22:55
 */
public class GetBcosSDK {

    private String configFile = "src/main/resources/config.toml";
    private org.fisco.bcos.sdk.BcosSDK sdk = org.fisco.bcos.sdk.BcosSDK.build(configFile);
    private Client client = sdk.getClient(1);
    private CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();

    public Client getClient(){
        return client;
    }
    public CryptoKeyPair getKeyPair(){
        return keyPair;
    }


    /**  单例模式的双重检查  **/
    private static volatile GetBcosSDK getBcosSDK;
    private GetBcosSDK(){ }

    public static GetBcosSDK theGetBcosSDK(){
        if (getBcosSDK == null){
            synchronized (ProUserDTO.class){
                if (getBcosSDK == null){
                    getBcosSDK = new GetBcosSDK();
                }
            }
        }
        return getBcosSDK;
    }
}
