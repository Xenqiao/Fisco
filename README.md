FiscoBcosDemo

 #环境准备：
 ###MySql
       请找到这个类 src/main/java/mvcdemo/dao/mysql/DBUtil.java  
       修改return DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
       在最后一个双引号(第三个)中输入你的 MySQL 连接密码
请将项目中附带的 

【 producer.sql 】【 product.sql 】 【 user.sql 】三个文件还原为表

创建一个名叫 **test** 的数据库中创建对应的 **user** producer **product** 三个表


