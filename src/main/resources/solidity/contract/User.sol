pragma solidity>=0.4.24 <0.6.11;

contract User{

    //所有地址集合
    address[] public userAddresses;
    //所有用户名集合
    string[] private userNames;
    address private sender;

    //定义用户数据结构
    struct User{
        address userAddress;
        string userName;//账号
        string uName;
        string uPhone;
        string uHome;
    }
    mapping(address => User) private studentStruct;


    //记录用户信息的修改记录与修改者
    event changeUser(address sender, address userAddress, string userName, string uName, string uPhone, string uHome);
    function setSender(address _sender) public{
        sender = _sender;
    }


    function setUser(
        address userAddress,
        string memory userName,
        string memory uName,
        string memory uPhone,
        string memory uHome) public {

        require(sender == userAddress);
        userNames.push(userName);
        userAddresses.push(userAddress);
        studentStruct[userAddress] = User(userAddress, userName, uName, uPhone, uHome);
        emit changeUser(sender, userAddress, userName, uName, uPhone, uHome);

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