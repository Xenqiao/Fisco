package mvcdemo.util.contractRealize;

import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;

/**
 * @author Xenqiao
 * @create 2023/4/1 22:55
 */
public class GetBcosSDK {

    static String configFile = "src/main/resources/config.toml";
    static org.fisco.bcos.sdk.BcosSDK sdk = org.fisco.bcos.sdk.BcosSDK.build(configFile);
    static Client client = sdk.getClient(1);
    static CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();

    public static Client getClient(){
        return client;
    }
    public static CryptoKeyPair getKeyPair(){
        return keyPair;
    }


}
