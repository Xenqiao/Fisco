package com.ashin;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 你好，世界
 *
 * @author ashinnotfound
 * @date 2023/03/12
 */
public class HelloWorld {
    public static void main(String[] args) throws Exception {
        String configFile = "src/main/resources/FiscoBcosConfig.toml";
        BcosSDK sdk = BcosSDK.build(configFile);
        Client client = sdk.getClient(1);
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "src/main/resources/solidity/abi", "src/main/resources/solidity/bin");

        // 部署HelloWorld合约。第一个参数为合约名称，第二个参数为合约构造函数的列表，是List<Object>类型。
        TransactionResponse response = transactionProcessor.deployByContractLoader("HelloWorld", new ArrayList<>());
        //合约地址
        String helloWorldAddress = response.getContractAddress();

        //调用setWord方法
        //创建调用交易函数的参数，此处传入一个参数word
        List<Object> params = new ArrayList<>();
        params.add("TopView");
        TransactionResponse transactionResponse = transactionProcessor.sendTransactionAndGetResponseByContractLoader("HelloWorld", helloWorldAddress, "setWord", params);
        System.out.println("调用setWord方法结果：" + transactionResponse.getReturnMessage());

        //调用sayWord方法
        CallResponse callResponse = transactionProcessor.sendCallByContractLoader("HelloWorld", helloWorldAddress, "sayWord", new ArrayList<>());
        System.out.println("调用sayWord方法结果：" + callResponse.getValues());
    }
}
