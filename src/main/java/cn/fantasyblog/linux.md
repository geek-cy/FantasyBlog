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



### 查看进程和端口
ps -ef |grep
ss -tlnp|grep
### mysql



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

### VIM
gg,dG清空所有内容
\关键词 查找关键字,n下一个关键字

### 部署springboot
nohup java -jar xxx


#### 其他技巧
netstat -tunlp 查看所有端口
ps -ajx