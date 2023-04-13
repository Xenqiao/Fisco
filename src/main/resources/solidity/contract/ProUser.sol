pragma solidity>=0.4.24 <0.6.11;

contract proUser{

    //所有地址集合
    address[] public proUserAddresses;
    //所有用户名集合
    string[] private proUserNames;

    //定义用户数据结构
    struct proUser{
        address proUserAddress;
        string proUserName;//账号
        string proName;
        string proPhone;
        string proHome;
    }

    mapping(address => proUser) private proUserStruct;


    function getProUserAddresses(uint proUserIndex) public view returns(address proUserAddress){
        return proUserAddresses[proUserIndex];
    }


    function setProUser(
        address proUserAddress,
        string memory proUserName,
        string memory proName,
        string memory proPhone,
        string memory proHome) public {
        proUserNames.push(proUserName);
        proUserAddresses.push(proUserAddress);
        proUserStruct[proUserAddress] = proUser(proUserAddress, proUserName, proName, proPhone, proHome);
    }

    //获取用户个人信息
    function getProUser(address proUserAddress) public view returns(address, string memory, string memory,string memory,string memory){
        return(
        proUserStruct[proUserAddress].proUserAddress,
        proUserStruct[proUserAddress].proUserName,
        proUserStruct[proUserAddress].proName,
        proUserStruct[proUserAddress].proPhone,
        proUserStruct[proUserAddress].proHome
        );
    }
}
