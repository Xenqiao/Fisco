package mvcdemo.util.toolcontract;

import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple6;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class Product extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506108fc806100206000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680635833b95814610051578063d9aba9ce1461017a575b600080fd5b34801561005d57600080fd5b50610178600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061034f565b005b34801561018657600080fd5b506101bb600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061048e565b604051808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001806020018681526020018060200180602001858152602001848103845289818151815260200191508051906020019080838360005b83811015610241578082015181840152602081019050610226565b50505050905090810190601f16801561026e5780820380516001836020036101000a031916815260200191505b50848103835287818151815260200191508051906020019080838360005b838110156102a757808201518184015260208101905061028c565b50505050905090810190601f1680156102d45780820380516001836020036101000a031916815260200191505b50848103825286818151815260200191508051906020019080838360005b8381101561030d5780820151818401526020810190506102f2565b50505050905090810190601f16801561033a5780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b60c0604051908101604052808773ffffffffffffffffffffffffffffffffffffffff168152602001868152602001858152602001848152602001838152602001828152506000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101908051906020019061043492919061082b565b5060408201518160020155606082015181600301908051906020019061045b92919061082b565b50608082015181600401908051906020019061047892919061082b565b5060a08201518160050155905050505050505050565b60006060600060608060008060008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166000808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001016000808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600201546000808b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206003016000808c73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206004016000808d73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060050154848054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106d75780601f106106ac576101008083540402835291602001916106d7565b820191906000526020600020905b8154815290600101906020018083116106ba57829003601f168201915b50505050509450828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107735780601f1061074857610100808354040283529160200191610773565b820191906000526020600020905b81548152906001019060200180831161075657829003601f168201915b50505050509250818054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561080f5780601f106107e45761010080835404028352916020019161080f565b820191906000526020600020905b8154815290600101906020018083116107f257829003601f168201915b5050505050915095509550955095509550955091939550919395565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061086c57805160ff191683800117855561089a565b8280016001018555821561089a579182015b8281111561089957825182559160200191906001019061087e565b5b5090506108a791906108ab565b5090565b6108cd91905b808211156108c95760008160009055506001016108b1565b5090565b905600a165627a7a72305820bc5a2156b32e39699497bc5ddb662db60fb8ac54aa3276400f7ac12008ebb0b30029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506108fc806100206000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680632577cfee146100515780634b6cfdd914610226575b600080fd5b34801561005d57600080fd5b50610092600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061034f565b604051808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001806020018681526020018060200180602001858152602001848103845289818151815260200191508051906020019080838360005b838110156101185780820151818401526020810190506100fd565b50505050905090810190601f1680156101455780820380516001836020036101000a031916815260200191505b50848103835287818151815260200191508051906020019080838360005b8381101561017e578082015181840152602081019050610163565b50505050905090810190601f1680156101ab5780820380516001836020036101000a031916815260200191505b50848103825286818151815260200191508051906020019080838360005b838110156101e45780820151818401526020810190506101c9565b50505050905090810190601f1680156102115780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b34801561023257600080fd5b5061034d600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506106ec565b005b60006060600060608060008060008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166000808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001016000808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600201546000808b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206003016000808c73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206004016000808d73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060050154848054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105985780601f1061056d57610100808354040283529160200191610598565b820191906000526020600020905b81548152906001019060200180831161057b57829003601f168201915b50505050509450828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106345780601f1061060957610100808354040283529160200191610634565b820191906000526020600020905b81548152906001019060200180831161061757829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106d05780601f106106a5576101008083540402835291602001916106d0565b820191906000526020600020905b8154815290600101906020018083116106b357829003601f168201915b5050505050915095509550955095509550955091939550919395565b60c0604051908101604052808773ffffffffffffffffffffffffffffffffffffffff168152602001868152602001858152602001848152602001838152602001828152506000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010190805190602001906107d192919061082b565b506040820151816002015560608201518160030190805190602001906107f892919061082b565b50608082015181600401908051906020019061081592919061082b565b5060a08201518160050155905050505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061086c57805160ff191683800117855561089a565b8280016001018555821561089a579182015b8281111561089957825182559160200191906001019061087e565b5b5090506108a791906108ab565b5090565b6108cd91905b808211156108c95760008160009055506001016108b1565b5090565b905600a165627a7a723058203be0f2aadf8f6704277f22119e2717b15ae40701760f50718e572881e1a1f9ff0029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"productAddress\",\"type\":\"address\"},{\"name\":\"productName\",\"type\":\"string\"},{\"name\":\"productPrice\",\"type\":\"uint256\"},{\"name\":\"productHome\",\"type\":\"string\"},{\"name\":\"productMake\",\"type\":\"string\"},{\"name\":\"productID\",\"type\":\"uint256\"}],\"name\":\"setProduct\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"productAddress\",\"type\":\"address\"}],\"name\":\"getProduct\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_SETPRODUCT = "setProduct";

    public static final String FUNC_GETPRODUCT = "getProduct";

    public Product(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt setProduct(String productAddress, String productName, Integer productPrice, String productHome, String productMake, Integer productID) {
        final Function function = new Function(
                FUNC_SETPRODUCT, 
                Arrays.<Type>asList(new Address(productAddress),
                new Utf8String(productName),
                new Uint256(productPrice),
                new Utf8String(productHome),
                new Utf8String(productMake),
                new Uint256(productID)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] setProduct(String productAddress, String productName, BigInteger productPrice, String productHome, String productMake, BigInteger productID, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETPRODUCT, 
                Arrays.<Type>asList(new Address(productAddress),
                new Utf8String(productName),
                new Uint256(productPrice),
                new Utf8String(productHome),
                new Utf8String(productMake),
                new Uint256(productID)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetProduct(String productAddress, String productName, BigInteger productPrice, String productHome, String productMake, BigInteger productID) {
        final Function function = new Function(
                FUNC_SETPRODUCT, 
                Arrays.<Type>asList(new Address(productAddress),
                new Utf8String(productName),
                new Uint256(productPrice),
                new Utf8String(productHome),
                new Utf8String(productMake),
                new Uint256(productID)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple6<String, String, BigInteger, String, String, BigInteger> getSetProductInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETPRODUCT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple6<String, String, BigInteger, String, String, BigInteger>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (String) results.get(3).getValue(), 
                (String) results.get(4).getValue(), 
                (BigInteger) results.get(5).getValue()
                );
    }

    public Tuple6<String, String, BigInteger, String, String, BigInteger> getProduct(String productAddress) throws ContractException {
        final Function function = new Function(FUNC_GETPRODUCT, 
                Arrays.<Type>asList(new Address(productAddress)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple6<String, String, BigInteger, String, String, BigInteger>(
                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(),
                (String) results.get(3).getValue(), 
                (String) results.get(4).getValue(), 
                (BigInteger) results.get(5).getValue());
    }

    public static Product load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Product(contractAddress, client, credential);
    }

    public static Product deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Product.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
