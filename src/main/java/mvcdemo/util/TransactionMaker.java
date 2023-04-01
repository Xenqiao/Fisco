package mvcdemo.util;

import com.webank.blockchain.hsm.crypto.utils.Hex;
import mvcdemo.util.toolcontract.impl.RemoteSignProviderInterface;
import org.apache.commons.lang3.tuple.Pair;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.ABICodec;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.signature.ECDSASignatureResult;
import org.fisco.bcos.sdk.crypto.signature.SignatureResult;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.builder.TransactionBuilderInterface;
import org.fisco.bcos.sdk.transaction.builder.TransactionBuilderService;
import org.fisco.bcos.sdk.transaction.codec.encode.TransactionEncoderInterface;
import org.fisco.bcos.sdk.transaction.codec.encode.TransactionEncoderService;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.gas.DefaultGasProvider;
import org.fisco.bcos.sdk.transaction.model.po.RawTransaction;
import org.fisco.bcos.sdk.transaction.pusher.TransactionPusherInterface;
import org.fisco.bcos.sdk.transaction.pusher.TransactionPusherService;

import java.math.BigInteger;
import java.util.List;


/**
 * @author Xenqiao
 * @create 2023/3/22 22:39
 */
public class TransactionMaker
{
    /**
     * 创建ABICodec对象
     * @param client client对象
     * @return: 创建的ABICodec对象
     */
    public ABICodec createABICodec(Client client)
    {
        return new ABICodec(client.getCryptoSuite());
    }

    /**
     * 对合约部署类型的交易进行编码
     * @param abiCodec 用于编码交易内容的ABICodeC对象
     * @param abiContent 合约的abi字符串(需要读取第1节生成的abi文件获取)
     * @param binContent binary字符串(需要读取第1节生成的bin文件获取)
     * @param params 部署合约的初始化参数列表
     * @return 编码后的交易内容
     */
    public String encodeConstructor(ABICodec abiCodec, String abiContent, String binContent, List<Object> params)
    {
        try {
            return abiCodec.encodeConstructor(abiContent, binContent, params);
        } catch (ABICodecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对合约调用类型的交易进行编码
     * @param abiCodec 用于编码交易内容的ABICodeC对象
     * @param abiContent 合约的abi字符串(需要读取第1节生成的abi文件获取)
     * @param methodName 需要调用的合约方法名       调用合约的参数列表
     * @return 编码后的交易内容
     */
    public String encodeMethod(ABICodec abiCodec, String abiContent, String methodName, List<Object> params)
    {
        try {
            return abiCodec.encodeMethod(abiContent, methodName, params);
        } catch (ABICodecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据交易内容 txData构造RawTransaction，并指定交易发送的地址为to(部署合约时，to为全0的合约地址)
     * @param transactionBuilder 交易构造对象
     * @param client Client对象，用于获取BlockLimit
     * @param to 交易发送的目标地址(部署合约时，to为全0的合约地址)
     * @param txData 编码后的交易
     * @return 创建的RawTransaction
     */
    public RawTransaction createRawTransaction(TransactionBuilderInterface transactionBuilder, Client client, String to, String txData)
    {
        // 获取chainId和groupId
        Pair<String, Integer> chainIdAndGroupId = TransactionProcessorFactory.getChainIdAndGroupId(client);
        RawTransaction transaction = transactionBuilder.createTransaction(
                DefaultGasProvider.GAS_PRICE,
                DefaultGasProvider.GAS_LIMIT,
                to,
                txData, BigInteger.ZERO,
                new BigInteger(chainIdAndGroupId.getLeft()),
                new BigInteger(String.valueOf(chainIdAndGroupId.getRight())),
                "");
        return transaction;

    }
    /**
     * 对RawTransaction进行RLP编码，返回编码内容的哈希
     *
     * @param transactionEncoder 交易编码器
     * @param client client对象，用于确定使用的哈希算法类型
     * @param rawTransaction 需要编码的交易
     * @return 编码后交易的哈希
     */
    public byte[] encodeRawTransactionAndGetHash(
            TransactionEncoderInterface transactionEncoder,
            Client client,
            RawTransaction rawTransaction) {
        byte[] encodedTransaction = transactionEncoder.encode(rawTransaction, null);
        return client.getCryptoSuite().hash(encodedTransaction);
    }


    public SignatureResult decodeSign(String txSignature){
        return null;
    };

    // 若是非国密类型应用，从txSignature可反序列化出 v, r, s, 构造SignatureResult的方法如下:
    public SignatureResult decodeECDSASignature(byte v, byte[] r, byte[] s)
    {
        return new ECDSASignatureResult(v, r, s);
    }

    /**
     * 根据RawTransaction和签名结果产生带有签名的交易
     *
     * @param transactionEncoder 交易编码器
     * @param transaction 不带有签名的交易
     * @param signatureResult 签名服务器返回的反序列化的签名结果
     * @return 带有签名的交易编码
     */
    public byte[] createSignedTransaction(
            TransactionEncoderInterface transactionEncoder,
            RawTransaction transaction,
            SignatureResult signatureResult) {
        return transactionEncoder.encode(transaction, signatureResult);
    }

    /**
     * 发送带有签名的交易
     *
     * @param txPusher 交易发送器
     * @param signedTransaction 带有签名的交易
     * @return 交易回执
     */
    TransactionReceipt sendTransaction(
            TransactionPusherInterface txPusher, byte[] signedTransaction) {
        return txPusher.push(Hex.toHexString(signedTransaction));
    }
    
    
    // 获取配置文件路径
    public final String configFile = TransactionMaker.class.getClassLoader().getResource("src/main/resource/config.toml").getPath();

    /** 根据合约abi、合约方法、合约地址发送交易，其中交易签名通过签名服务产生
     *
     * @param abiContent
     * @param methodName
     * @param to
     * @param params
     * @return
     */
    public TransactionReceipt makeAndSendSignedTransaction(String abiContent, String methodName, String to, List<Object> params)
    {
        // 初始化BcosSDK
        BcosSDK sdk =  BcosSDK.build(configFile);
        // 为群组1初始化client
        Client client = sdk.getClient(Integer.valueOf(1));
        // 创建transactionEncoder
        TransactionEncoderInterface transactionEncoder = new TransactionEncoderService(client.getCryptoSuite());

        // 创建RawTransaction
        RawTransaction rawTransaction = makeTransaction(client, abiContent, methodName, to, params);

        // 请求签名服务签名
        SignatureResult signature = requestForTransactionSignature(transactionEncoder, rawTransaction, client);

        // 产生带有签名的交易
        byte[] signedTransaction = createSignedTransaction(transactionEncoder, rawTransaction, signature);

        // 发送签名交易
        TransactionPusherInterface txPusher = new TransactionPusherService(client);
        return sendTransaction(txPusher, signedTransaction);
    }

    // 构造交易
    public RawTransaction makeTransaction(Client client, String abiContent, String methodName, String to, List<Object> params)
    {
        //1.创建ABICodeC对象
        ABICodec abiCodec = createABICodec(client);

        //2.编码交易内容
        String txData =  encodeMethod(abiCodec, abiContent, methodName, params);

        //3. 创建TransactionBuilder，构造RawTransaction
        TransactionBuilderInterface transactionBuilder = new TransactionBuilderService(client);
        return createRawTransaction(transactionBuilder, client, to, txData);

    }

    // 请求签名服务
    public SignatureResult requestForTransactionSignature(TransactionEncoderInterface transactionEncoder, RawTransaction rawTransaction, Client client)
    {
        // 获取RawTransaction的哈希
        byte[] rawTxHash = encodeRawTransactionAndGetHash(transactionEncoder, client, rawTransaction);
        // 请求签名服务，获取交易签名requestForSign
        String signature = RemoteSignProviderInterface.requestForSign(rawTxHash,1);

        // 对签名结果进行反序列化
        return decodeSign(signature);
    }



}