pragma solidity>=0.4.24 <0.6.11;

contract Product{
    struct product{
        address productAddress;
        string productName;
        uint productPrice;
        string productHome;
        string productMake;
        uint productID;

    }

    mapping(address => product) private ProductStruct;

    function setProduct(
        address productAddress,
        string memory productName,
        uint productPrice,
        string memory productHome,
        string memory productMake,
        uint productID
    ) public {
        ProductStruct[productAddress] = product(productAddress, productName, productPrice, productHome, productMake, productID);
    }

    function getProduct(address productAddress) public view returns(address, string memory, uint, string memory, string memory, uint ){

        return(ProductStruct[productAddress].productAddress,    //1
        ProductStruct[productAddress].productName,              //2
        ProductStruct[productAddress].productPrice,
        ProductStruct[productAddress].productHome,              //value4
        ProductStruct[productAddress].productMake,              //value5
        ProductStruct[productAddress].productID                 //value6
        );
    }
}