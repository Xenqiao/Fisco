pragma solidity>=0.4.24 <0.6.11;

contract simpletokenstruct{

    string public name;  //代币名称
    string public simple;  //代币代号
    uint8 public decimals;  //代币小数点位位数
    uint256 public totalSupply;  //代币总发行量
    address public sender;

    function balanceOf(address _owner) public view returns (uint256 balance){   //查看_owner账户的余额，返回余额

    }

    function transfer(address _to, uint256 _value) public returns (bool success){   //给_to地址转账_value，返回操作是否成功

    }

    function transferFrom(address _from, address _to, uint256 _value) public returns (bool success){
        //从_from地址给_to地址转账_value，返回操作是否成功
    }

    function approve(address _spender, uint256 _value) public returns (bool success){
        //允许_spender地址来操作_value大小的余额
    }

    function allowance(address _owner, address _spender) public view returns (uint256 remaining){
        //用来查询_owner允许_spender使用多少个代币
    }

    event Transfer(address indexed _from, address indexed _to, uint256 _value); //定义了一个事件，暂时不管
    event Approval(address indexed _owner, address indexed _spender, uint256 _value); //定义了一个事件，这里暂时不管

}

contract Erc20 is simpletokenstruct{

    constructor(address _sender) public{
        name='chocobo';  //名字为choboco
        simple='GIL';   //代号为GIL
        decimals=1;    //最多一位小数
        totalSupply=10000;  //总发行量10000
        sender = _sender;
        balanceOf[sender]=totalSupply;  //发布时，发布者拥有所有代币
    }

    mapping(address=>uint256) public balanceOf;  //将地址映射为uint，代表账户余额
    mapping(address=>mapping(address=>uint256)) internal approvemapping;

    //代币转账逻辑
    function transfer(address _to, uint256 _value) public returns (bool success){

        require(_to != address(0));  //地址不为空
        require(balanceOf[sender]>=_value);  //转账账户余额充足
        require(balanceOf[_to]+_value>=balanceOf[_to]);  //检测是否溢出

        balanceOf[sender]-=_value;  //发送者减去数额
        balanceOf[_to]+=_value;  //接受者增加数额

        emit Transfer(sender,_to,_value);  //暂时不管
        success = true;

    }


    //代币授权转账
    function transferFrom(address _from, address _to, uint256 _value) public returns (bool success){

        require(_to != address(0));//地址不为空
        require(balanceOf[_from]>=_value);//转账账户余额充足
        //require(approvemapping[_from][sender]>=_value); //这里检测委托人的可操作的金额是否转账金额
        require(balanceOf[_to]+_value>=balanceOf[_to]); //检测是否溢出

        balanceOf[_from]-=_value;//发送者减去数额
        balanceOf[_to]+=_value;//接受者增加数额

        success = true;
    }

    //代币授权逻辑
    function approve(address _spender, uint256 _value) public returns (bool success){
        approvemapping[sender][_spender]=_value;  //映射可操作的代币，由调用者（msg.sender）指定_spender操作_value数额的代币
        emit Approval(sender,_spender,_value);  //暂时不管
        success = true;
    }

    //查看剩余能调用多少代币
    function allowance(address _owner, address _spender) public view returns (uint256 remaining){
        return approvemapping[_owner][_spender];   //查看剩余能调用多少代币
    }

    //用于铸造代币
    function mint(uint amount) external {
        balanceOf[sender] += amount;
        totalSupply += amount;
        emit Transfer(address(0), sender, amount);
    }


    //销毁代币的函数
    function burn(uint amount) external {
        balanceOf[sender] -= amount;
        totalSupply -= amount;
        emit Transfer(sender, address(0), amount);
    }



    event Transfer(address indexed _from, address indexed _to, uint256 _value); //暂时不管
    event Approval(address indexed _owner, address indexed _spender, uint256 _value);  //暂时不管

}
