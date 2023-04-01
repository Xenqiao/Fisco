pragma solidity>=0.4.24;

contract User{

    //所有地址集合
    address[] public userAddresses;
    //所有用户名集合
    string[] private userNames;

    //定义用户数据结构
    struct User{
        address userAddress;
        string userName;//账号
        string uName;
        string uPhone;
        string uHome;
    }
    mapping(address => User) private studentStruct;


    function setUser(
        address userAddress,
        string memory userName,
        string memory uName,
        string memory uPhone,
        string memory uHome) public {
        userNames.push(userName);
        userAddresses.push(userAddress);
        studentStruct[userAddress] = User(userAddress, userName, uName, uPhone, uHome);
    }

    //获取用户个人信息
    function getUser(address userAddress) public view returns(address, string memory, string memory,string memory,string memory){
        return(
        studentStruct[userAddress].userAddress,
        studentStruct[userAddress].userName,
        studentStruct[userAddress].uName,
        studentStruct[userAddress].uPhone,
        studentStruct[userAddress].uHome
        );
    }
}