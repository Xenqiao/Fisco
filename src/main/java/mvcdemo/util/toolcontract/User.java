package mvcdemo.util.toolcontract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class User extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50610f65806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063502c9bd5146100675780636f77926b146100d45780637a60dd3514610307578063ced32b0c14610462575b600080fd5b34801561007357600080fd5b50610092600480360381019080803590602001909291905050506104e5565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156100e057600080fd5b50610115600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610523565b604051808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001806020018060200180602001858103855289818151815260200191508051906020019080838360005b83811015610193578082015181840152602081019050610178565b50505050905090810190601f1680156101c05780820380516001836020036101000a031916815260200191505b50858103845288818151815260200191508051906020019080838360005b838110156101f95780820151818401526020810190506101de565b50505050905090810190601f1680156102265780820380516001836020036101000a031916815260200191505b50858103835287818151815260200191508051906020019080838360005b8381101561025f578082015181840152602081019050610244565b50505050905090810190601f16801561028c5780820380516001836020036101000a031916815260200191505b50858103825286818151815260200191508051906020019080838360005b838110156102c55780820151818401526020810190506102aa565b50505050905090810190601f1680156102f25780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b34801561031357600080fd5b50610460600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610919565b005b34801561046e57600080fd5b506104a3600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610dc9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000818154811015156104f457fe5b906000526020600020016000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000606080606080600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600360008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600101600360008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600201600360008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600301600360008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600401838054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561072b5780601f106107005761010080835404028352916020019161072b565b820191906000526020600020905b81548152906001019060200180831161070e57829003601f168201915b50505050509350828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107c75780601f1061079c576101008083540402835291602001916107c7565b820191906000526020600020905b8154815290600101906020018083116107aa57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108635780601f1061083857610100808354040283529160200191610863565b820191906000526020600020905b81548152906001019060200180831161084657829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108ff5780601f106108d4576101008083540402835291602001916108ff565b820191906000526020600020905b8154815290600101906020018083116108e257829003601f168201915b505050505090509450945094509450945091939590929450565b8473ffffffffffffffffffffffffffffffffffffffff16600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151561097557600080fd5b60018490806001815401808255809150509060018203906000526020600020016000909192909190915090805190602001906109b2929190610e14565b505060008590806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505060a0604051908101604052808673ffffffffffffffffffffffffffffffffffffffff16815260200185815260200184815260200183815260200182815250600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001019080519060200190610afa929190610e94565b506040820151816002019080519060200190610b17929190610e94565b506060820151816003019080519060200190610b34929190610e94565b506080820151816004019080519060200190610b51929190610e94565b509050507f3b31cbf791a2c554326a7787c29af45b5dd89b7ecbb70d904a9d7f83e569edf5600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168686868686604051808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001806020018060200180602001858103855289818151815260200191508051906020019080838360005b83811015610c4e578082015181840152602081019050610c33565b50505050905090810190601f168015610c7b5780820380516001836020036101000a031916815260200191505b50858103845288818151815260200191508051906020019080838360005b83811015610cb4578082015181840152602081019050610c99565b50505050905090810190601f168015610ce15780820380516001836020036101000a031916815260200191505b50858103835287818151815260200191508051906020019080838360005b83811015610d1a578082015181840152602081019050610cff565b50505050905090810190601f168015610d475780820380516001836020036101000a031916815260200191505b50858103825286818151815260200191508051906020019080838360005b83811015610d80578082015181840152602081019050610d65565b50505050905090810190601f168015610dad5780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390a15050505050565b600081600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550339050919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610e5557805160ff1916838001178555610e83565b82800160010185558215610e83579182015b82811115610e82578251825591602001919060010190610e67565b5b509050610e909190610f14565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610ed557805160ff1916838001178555610f03565b82800160010185558215610f03579182015b82811115610f02578251825591602001919060010190610ee7565b5b509050610f109190610f14565b5090565b610f3691905b80821115610f32576000816000905550600101610f1a565b5090565b905600a165627a7a723058206a269983bc1a1bc92f75e7f41244e921d63db5952bce33dcb609e1bd956cae3d0029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50610f65806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680635e5f004214610067578063d587d683146100ea578063e17be8ad1461031d578063e9e094391461038a575b600080fd5b34801561007357600080fd5b506100a8600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506104e5565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156100f657600080fd5b5061012b600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610530565b604051808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001806020018060200180602001858103855289818151815260200191508051906020019080838360005b838110156101a957808201518184015260208101905061018e565b50505050905090810190601f1680156101d65780820380516001836020036101000a031916815260200191505b50858103845288818151815260200191508051906020019080838360005b8381101561020f5780820151818401526020810190506101f4565b50505050905090810190601f16801561023c5780820380516001836020036101000a031916815260200191505b50858103835287818151815260200191508051906020019080838360005b8381101561027557808201518184015260208101905061025a565b50505050905090810190601f1680156102a25780820380516001836020036101000a031916815260200191505b50858103825286818151815260200191508051906020019080838360005b838110156102db5780820151818401526020810190506102c0565b50505050905090810190601f1680156103085780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b34801561032957600080fd5b5061034860048036038101908080359060200190929190505050610926565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561039657600080fd5b506104e3600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610964565b005b600081600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550339050919050565b6000606080606080600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600360008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600101600360008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600201600360008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600301600360008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600401838054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107385780601f1061070d57610100808354040283529160200191610738565b820191906000526020600020905b81548152906001019060200180831161071b57829003601f168201915b50505050509350828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107d45780601f106107a9576101008083540402835291602001916107d4565b820191906000526020600020905b8154815290600101906020018083116107b757829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108705780601f1061084557610100808354040283529160200191610870565b820191906000526020600020905b81548152906001019060200180831161085357829003601f168201915b50505050509150808054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561090c5780601f106108e15761010080835404028352916020019161090c565b820191906000526020600020905b8154815290600101906020018083116108ef57829003601f168201915b505050505090509450945094509450945091939590929450565b60008181548110151561093557fe5b906000526020600020016000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b8473ffffffffffffffffffffffffffffffffffffffff16600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156109c057600080fd5b60018490806001815401808255809150509060018203906000526020600020016000909192909190915090805190602001906109fd929190610e14565b505060008590806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505060a0604051908101604052808673ffffffffffffffffffffffffffffffffffffffff16815260200185815260200184815260200183815260200182815250600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001019080519060200190610b45929190610e94565b506040820151816002019080519060200190610b62929190610e94565b506060820151816003019080519060200190610b7f929190610e94565b506080820151816004019080519060200190610b9c929190610e94565b509050507f85c0a69c1cd256db1758144447e9630132ae55fd8a0e2a3f0d4e0883c4950d21600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168686868686604051808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001806020018060200180602001858103855289818151815260200191508051906020019080838360005b83811015610c99578082015181840152602081019050610c7e565b50505050905090810190601f168015610cc65780820380516001836020036101000a031916815260200191505b50858103845288818151815260200191508051906020019080838360005b83811015610cff578082015181840152602081019050610ce4565b50505050905090810190601f168015610d2c5780820380516001836020036101000a031916815260200191505b50858103835287818151815260200191508051906020019080838360005b83811015610d65578082015181840152602081019050610d4a565b50505050905090810190601f168015610d925780820380516001836020036101000a031916815260200191505b50858103825286818151815260200191508051906020019080838360005b83811015610dcb578082015181840152602081019050610db0565b50505050905090810190601f168015610df85780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390a15050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610e5557805160ff1916838001178555610e83565b82800160010185558215610e83579182015b82811115610e82578251825591602001919060010190610e67565b5b509050610e909190610f14565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610ed557805160ff1916838001178555610f03565b82800160010185558215610f03579182015b82811115610f02578251825591602001919060010190610ee7565b5b509050610f109190610f14565b5090565b610f3691905b80821115610f32576000816000905550600101610f1a565b5090565b905600a165627a7a723058205f03c648ad272a6571c6efe448f6515ce3338b49078ffec9c46d565be4096b6a0029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"userAddresses\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"userAddress\",\"type\":\"address\"}],\"name\":\"getUser\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"userAddress\",\"type\":\"address\"},{\"name\":\"userName\",\"type\":\"string\"},{\"name\":\"uName\",\"type\":\"string\"},{\"name\":\"uPhone\",\"type\":\"string\"},{\"name\":\"uHome\",\"type\":\"string\"}],\"name\":\"setUser\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_sender\",\"type\":\"address\"}],\"name\":\"setSender\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"userAddress\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"userName\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"uName\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"uPhone\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"uHome\",\"type\":\"string\"}],\"name\":\"changeUser\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_USERADDRESSES = "userAddresses";

    public static final String FUNC_GETUSER = "getUser";

    public static final String FUNC_SETUSER = "setUser";

    public static final String FUNC_SETSENDER = "setSender";

    public static final Event CHANGEUSER_EVENT = new Event("changeUser", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public User(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public String userAddresses(BigInteger param0) throws ContractException {
        final Function function = new Function(FUNC_USERADDRESSES, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public Tuple5<String, String, String, String, String> getUser(String userAddress) throws ContractException {
        final Function function = new Function(FUNC_GETUSER, 
                Arrays.<Type>asList(new Address(userAddress)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple5<String, String, String, String, String>(
                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue(), 
                (String) results.get(3).getValue(), 
                (String) results.get(4).getValue());
    }

    public TransactionReceipt setUser(String userAddress, String userName, String uName, String uPhone, String uHome) {
        final Function function = new Function(
                FUNC_SETUSER, 
                Arrays.<Type>asList(new Address(userAddress),
                new Utf8String(userName),
                new Utf8String(uName),
                new Utf8String(uPhone),
                new Utf8String(uHome)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] setUser(String userAddress, String userName, String uName, String uPhone, String uHome, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETUSER, 
                Arrays.<Type>asList(new Address(userAddress),
                new Utf8String(userName),
                new Utf8String(uName),
                new Utf8String(uPhone),
                new Utf8String(uHome)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetUser(String userAddress, String userName, String uName, String uPhone, String uHome) {
        final Function function = new Function(
                FUNC_SETUSER, 
                Arrays.<Type>asList(new Address(userAddress),
                new Utf8String(userName),
                new Utf8String(uName),
                new Utf8String(uPhone),
                new Utf8String(uHome)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple5<String, String, String, String, String> getSetUserInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETUSER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple5<String, String, String, String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue(), 
                (String) results.get(3).getValue(), 
                (String) results.get(4).getValue()
                );
    }

    public TransactionReceipt setSender(String _sender) {
        final Function function = new Function(
                FUNC_SETSENDER, 
                Arrays.<Type>asList(new Address(_sender)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] setSender(String _sender, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETSENDER, 
                Arrays.<Type>asList(new Address(_sender)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetSender(String _sender) {
        final Function function = new Function(
                FUNC_SETSENDER, 
                Arrays.<Type>asList(new Address(_sender)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSetSenderInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETSENDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<String> getSetSenderOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SETSENDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public List<ChangeUserEventResponse> getChangeUserEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CHANGEUSER_EVENT, transactionReceipt);
        ArrayList<ChangeUserEventResponse> responses = new ArrayList<ChangeUserEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ChangeUserEventResponse typedResponse = new ChangeUserEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.userAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.userName = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.uName = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.uPhone = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.uHome = (String) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeChangeUserEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(CHANGEUSER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeChangeUserEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(CHANGEUSER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static User load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new User(contractAddress, client, credential);
    }

    public static User deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(User.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class ChangeUserEventResponse {
        public TransactionReceipt.Logs log;

        public String sender;

        public String userAddress;

        public String userName;

        public String uName;

        public String uPhone;

        public String uHome;
    }
}
