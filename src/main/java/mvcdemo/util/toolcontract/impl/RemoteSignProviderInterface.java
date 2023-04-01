package mvcdemo.util.toolcontract.impl;

import org.fisco.bcos.sdk.transaction.signer.RemoteSignCallbackInterface;

/**
 * @author Xenqiao
 * @create 2023/3/28 22:04
 */
public interface RemoteSignProviderInterface {
    /**
     * request for signature provider service, and return the signature.
     * 请求签名提供程序服务，并返回签名。
     *
     * @param dataToSign  data to be signed
     * @param cryptoType: ECDSA=0,SM=1, or self defined
     * @return signature result
     */
    static String requestForSign(byte[] dataToSign, int cryptoType){
        return null;
    };

    /**
     * request for signature provider service asynchronously
     * 异步请求签名提供程序服务
     * @param dataToSign data to be signed
     * @param cryptoType: ECDSA=0,SM=1, or self defined
     * @param callback transaction sign callback
     */
     void requestForSignAsync(byte[] dataToSign, int cryptoType, RemoteSignCallbackInterface callback);

}
