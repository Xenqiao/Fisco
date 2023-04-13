pragma solidity>=0.4.24 <0.6.11;

// 商品合约
contract Produc{
    bool public enable;         // 当前商品是否有效
    address public sellerAddr;  // 卖家的地址
    uint public price;          // 商品的单价
    uint public commitTime;     // 卖家承诺发货时间
    address[] public orders;    // 所有订单的地址
    address public lastOrder;   // 最新订单的地址

    modifier onlySeller() {
        require(
            sellerAddr == msg.sender,
            "卖家账号地址错误!"
        );
        _;
    }

    constructor(bool _enable, uint _price, uint _commitTime)
    public
    {
        require(
            _price != 0 && _commitTime != 0,
            "参数信息不完整"
        );
        sellerAddr = msg.sender;
        enable = _enable;
        price = _price;
        commitTime = _commitTime;
    }

    function setEnable(bool _enable)
    public
    onlySeller
    {
        enable = _enable;
    }

    function setPrice(uint _price)
    public
    onlySeller
    {
        price = _price;
    }

    function setCommitTime(uint _commitTime)
    public
    onlySeller
    {
        commitTime = _commitTime;
    }

    function getOrderCount()
    public
    view
    returns(uint _count)
    {
        return orders.length;
    }

    // 买家创建订单，输入购买数量和两倍原价的msg.value
    event NewOrder(address _orderAddr);
    function newOrder(uint amount)
    public
    payable
    {
        require(
            enable == true,
            "产品当前无法下单"
        );
        require(
            amount != 0,
            "购买数量不能为0"
        );
        Order order = (new Order).value(msg.value)(msg.sender, sellerAddr, price * amount, commitTime);
        orders.push(order);
        lastOrder = order;
        emit NewOrder(order);
    }
}

// 订单合约
contract Order {
    // 已支付，已接单，已发货，已签收，已收货，已失效
    enum Status {
        Paid, Taken, Shipped, Signed, Received, Inactive
    }

    address public buyerAddr;      // 买家的地址
    address public sellerAddr;     // 卖家的地址
    uint public price;             // 商品的总价
    uint public commitTime;        // 卖家承诺发货时间
    Status public status;          // 订单的状态
    uint public createTime;        // 订单的创建(支付)时间
    uint public signTime;          // 订单的签收时间
    string public trackingNumber;  // 订单的物流号
    uint8 public score;            // 订单的评分
    string public assession;       // 订单的评价

    modifier inStatus(Status _status) {
        require(
            status == _status,
            "订单的状态不可操作"
        );
        _;
    }

    modifier onlyBuyer() {
        require(
            buyerAddr == msg.sender,
            "买家账号地址错误!"
        );
        _;
    }

    modifier onlySeller() {
        require(
            sellerAddr == msg.sender,
            "卖家账号地址错误!"
        );
        _;
    }

    function getBalance()
    public
    view
    returns(uint _balance)
    {
        return address(this).balance;
    }

    constructor(address _buyerAddr, address _sellerAddr, uint _price, uint _commitTime)
    public
    payable
    {
        require(
            msg.value == _price * 2,
            "买家需要额外支付与商品价格等价的金额作为押金"
        );
        buyerAddr = _buyerAddr;
        sellerAddr = _sellerAddr;
        price = _price;
        commitTime = _commitTime;
        status = Status.Paid;
        createTime = now;
    }

    // 卖家接单之前用户可以取消购买
    function abort() public onlyBuyer payable inStatus(Status.Paid) {
        status = Status.Inactive;
        buyerAddr.transfer(price * 2);
    }

    // 卖家选择接单或拒绝接单
    function take(bool _takeOrder) public onlySeller payable inStatus(Status.Paid) {
        if (_takeOrder) {
            require(msg.value == price, "卖家需要支付与商品价格等价的金额作为押金");
            status = Status.Taken;
        } else {
            status = Status.Inactive;
            buyerAddr.transfer(price * 2);
        }
    }

    // 买家检查卖家是否没按时发货，如果是则退还买家钱，同时作为惩罚，卖家的押金被锁定在合约里了
    function checkLate() public onlyBuyer inStatus(Status.Taken) {
        require(now - createTime > commitTime, "尚未到卖家发货截止期限");
        status = Status.Inactive;
        buyerAddr.transfer(price * 2);
    }

    // 卖家输入运单号，确认发货
    function ship(string _trackingNumber) public onlySeller payable inStatus(Status.Taken) {
        // todo: 检查运单号是否真实存在
        status = Status.Shipped;
        trackingNumber = _trackingNumber;
        sellerAddr.transfer(price); // 卖家发货后退回押金
    }

    // 确认签收
    function sign() public inStatus(Status.Shipped) {
        // todo: 通过运单号查询物流信息，判断是否签收并获取签收时间
        status = Status.Signed;
        // signTime = getSignTime();
        signTime = now; // 测试用

    }

    //买家确认收货
    function receive() public onlyBuyer payable inStatus(Status.Signed){
        status = Status.Received;
        buyerAddr.transfer(price);
        sellerAddr.transfer(price);
    }

    // 过了10天买家没确认收货，则卖家可以自己确认
    function confirmBySeller() public onlySeller payable inStatus(Status.Signed) {
        require(now - signTime > 10 days, "卖家超过10天才可以确认");

        status = Status.Received;
        buyerAddr.transfer(price);
        sellerAddr.transfer(price);
    }

    // 买家对订单进行评价
    function assess(uint8 _score, string _assession) public onlyBuyer inStatus(Status.Received){
        require(_score >= 1 && _score <= 5,"评分只能是1~5之间的整数");
        score = _score;
        assession = _assession;
        status = Status.Inactive;
    }
}

