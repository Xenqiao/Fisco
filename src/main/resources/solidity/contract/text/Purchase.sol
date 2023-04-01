pragma solidity ^0.8.4;

//安全的远程购买，Remix编写
contract Purchase{
    uint public value;//商品价值
    address payable public seller;//卖家地址
    address payable public buyer;//买家地址

    //枚举类型，不用加分号，用于标记交易状态
    enum State{Created,Locked,Release,Inactive}

    State public state;//定义枚举类型变量

    //modifier类似于一个可以通用的函数供其他function重复调用，减少代码量
    //_;可以放在modifier结构体{}内的任何位置来运行调用modifier的function代码
    modifier condition(bool condition_){
        require(condition_);
        _;
    }

    //error用于解释操作失败的原因，可以继承，参数列表可以只定义数据类型或为空
    //不能重载，不能作为控制流的一种手段，合约内部和外部均可定义。
    //调用时必须用revert修饰，revert用于回退error中数据给调用者并回退当前调用中所有更改。
    error OnlyBuyer();//仅有买家可以调用
    error OnlySeller();//仅有卖家可以调用
    error InvalidState();//当前交易状态为Invalid，不能调用
    error ValueNotEven();//商品价格为不为偶数，需中止交易

    modifier onlyBuyer(){
        if(msg.sender != buyer)
            revert OnlyBuyer();
        _;
    }

    modifier onlySeller(){
        if(msg.sender != seller)
            revert OnlySeller();
        _;
    }

    modifier inState(State state_){
        if(state != state_)
            revert InvalidState();
        _;
    }

    //调用event可以将合约中某些内容的更改记录到日志(记录在区块链上)，用关键字emit修饰调用
    //事件(event)和日志不能在合约内部访问，可以被子合约调用
    event Aborted();//标识中止交易状态
    event PurchaseConfirmed();//标识交易发起
    event ItemReceived();//标识交易确认
    event SellerRefunded();//标识卖家退款事件

    //构造函数，部署合约时调用，仅调用一次，初次调用该合约的地址默认为卖家并初始化卖价
    //当前版本的address分为：address 和 payable address
    //payable address支持.transfer和.send方法，用于发送ether
    //payable address只能给payable address转账，而不能给address转账
    constructor() payable{
        seller = payable(msg.sender);
        value = msg.value/2;
        if((2*value)!=msg.value)
            revert ValueNotEven();//value必须为偶数
    }

    //中止交易过程，给卖家退钱
    function abort() external onlySeller inState(State.Created){
        emit Aborted();
        state = State.Inactive;
        seller.transfer(address(this).balance);//由交易调用智能呢个合约中的function，伴随转账金额，所以此处需要退款给卖家
    }

    //发起交易(交易发起者置为买家)，更改交易状态为Locked
    function confirmPurchase() external
    inState(State.Created) condition(msg.value == (2*value)) payable{
        emit PurchaseConfirmed();
        buyer = payable(msg.sender);//强制类型转换将buyer转换为payable address类型
        state = State.Locked;
    }

    //确认交易，给买家转账1*value，更改交易状态为Release
    function confirmReceived() external onlyBuyer inState(State.Locked){
        emit ItemReceived();
        state = State.Release;
        buyer.transfer(value);
    }

    //交易退款，给卖家转账3*value,更改交易状态为Inactive
    function refundSeller() external onlySeller inState(State.Release){
        emit SellerRefunded();
        state = State.Inactive;
        seller.transfer(3*value);
    }
}