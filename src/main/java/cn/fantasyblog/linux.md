

### 查看进程和端口
ps -ef |grep
ss -tlnp|grep

### mysql
MySQL在Linux下数据库名、表名、列名、别名大小写规则是这样的：
1、数据库名与表名是严格区分大小写的；
2、表的别名是严格区分大小写的；
3、列名与列的别名在所有的情况下均是忽略大小写的；
4、变量名也是严格区分大小写的；
MySQL在Windows下都不区分大小写
#### 卸载旧版本Mysql
yum remove mysql
#### 更新yum源头
rpm -Uvh https://dev.mysql.com/get/mysql80-community-release-el7-3.noarch.rpm
#### 清除yum缓存
yum clean all
#### 重新建立yum缓存
yum makecache
#### 安装Mysql
yum -y install mysql
yum -y install mysql-community-server
#### 启动Mysql
service mysqld start
#### 查看Mysql是否启动
service mysqld status
#### 停止Mysql
service mysqld stop
#### 重设密码
##### 在 vim /etc/my.cnf添加代码
skip-grant-tables
##### 将密码置空
update user set authentication_string=''where user = 'root'
##### 先设置一个符合规则的密码
alter user 'root'@'localhost'identified by 'Abc123...';
##### 查看当前默认规则
show variables like 'validate_password%';
##### 修改校验密码策略等级
set global validate_password.policy=LOW;
##### 设置密码长度至少为4
set global validate_password.length=4;
##### 重新设置密码
alter user 'admin'@'%' identified with mysql_native_password by '设置的密码';
#### 实现远程连接
##### 查看用户连接mysql列表
select host,user,plugin from user;
##### 设置新增用户密码
alter user '用户名'@'%' identified with mysql_native_password by '密码';
##### 设置该账户可以远程登录
grant all privileges on *.* to '用户名'@'%';
##### 刷新权限
flush privileges;

### elasticsearch
#### 新增用户组
gruopadd 用户组
#### 为添加的用户指定相应的用户组
useradd 用户名 -g 用户组 -p 密码
#### 对当前目录下所有目录以及子目录进行相同的拥有者变更
chown -R 用户名:用户组 目录
#### 后台启动elasticsearch
elasticsearch -d

### kafka
#### 进入配置目录
cd kafka_2.12-2.5.0/config/

#### 备份配置文件
cp server.properties server.properties.bak

#### 修改配置文件
vim server.properties

#### 修改及添加以下配置
broker.id=1
listeners=PLAINTEXT://127.0.0.1:9092
advertised.listeners=PLAINTEXT://127.0.0.1:9092

#### 其他自定义配置（根据实际修改）
zookeeper.connect=127.0.0.1:2181
zookeeper.connection.timeout.ms=18000

#### 保存退出
:wq

#### 配置说明
broker.id：当前机器在集群中的唯一标识。例如有三台Kafka主机，则分别配置为1,2,3。

listeners：服务监听端口。

advertised.listeners：提供给生产者，消费者的端口号，即外部访问地址。默认为listeners的值。

zookeeper.connect：zookeeper连接地址

#### 启动Kafka
./kafka-server-start.sh -daemon ../config/server.properties

#### 关闭kafka服务
./kafka-server-stop.sh

#### 测试创建一个topic：
./kafka-topics.sh --create --zookeeper 127.0.0.1:2181 --replication-factor 1 --partitions 1 --topic topic1

#### 查看topic信息
./kafka-topics.sh --describe --zookeeper 127.0.0.1:2181 --topic topic1

#### 启动生产者控制台
./kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic topic1

##### 启动消费者控制台（新开一个窗口）
./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic topic1 --from-beginning


### 部署springboot
touch nohup.out
nohup java -jar jar包名

### 其他命令
netstat -tunlp 查看所有端口
ps -ajx 查看历史命令以及对应的进程
jps 查看关于java的进程
rpm -ivh 安装
tar -zxvf
touch -nohup.out 新建文件
true  -nohup.out 清空文件
### rpm
-i, --install 安装
-v, --verbose 打印冗长信息（显示指令执行过程）
-h, --hash 安装时列出标记（显示安装进度）
-e, --erase 擦除（也就是卸载）
-q, --query 查询（单独使用时，查看RMP包是否安装，配合其他参数使用查询相关信息）
-a, --all 查询所有已经安装的RPM包
-l, --list 显示包中的文件列表

### tar
-c ：建立一个压缩文件的参数指令(create 的意思)；
-x ：解开一个压缩文件的参数指令！
-t ：查看 tarfile 里面的文件！
特别注意，在参数的下达中， c/x/t 仅能存在一个！不可同时存在！
因为不可能同时压缩与解压缩。
-z ：--gzip或--ungzip 通过gzip指令处理备份文件
-j ：是否同时具有 bzip2 的属性？亦即是否需要用 bzip2 压缩？
-v ：压缩的过程中显示文件！
-f ：使用档名，请留意，在 f 之后要立即接档名喔！不要再加参数！
　　　例如使用『 tar -zcvfP tfile sfile』就是错误的写法，要写成
　　　『 tar -zcvPf tfile sfile』才对喔！
-p ：使用原文件的原来属性（属性不会依据使用者而变）
-P ：可以使用绝对路径来压缩！
-N ：比后面接的日期(yyyy/mm/dd)还要新的才会被打包进新建的文件中！

### VIM
gg,dG清空所有内容
\关键词 查找关键字,n下一个关键字