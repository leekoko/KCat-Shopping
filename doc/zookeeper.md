# zookeeper

M：怎么安装zookeeper呢?

Z：首先需要安装JDK环境，然后安装zookeeper   

1. 下载zookeeper地址：``http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.4.10/``   
2. 将zookeeper上传到linux服务器，解压``tar -xvf zookeeper-3.4.10.tar.gz ``   
3. 进入解压后的/bin目录，运行``./zkServer.sh start``即可   

M：那我要怎么修改zookeeper的配置呢？

Z：进入/conf目录，复制一份配置文件``cp zoo_sample.cfg zoo.cfg``,编辑配置文件即可。   

M：配置文件中，``syncLimit=5``是干嘛用的？

Z：设置leader和follwer的响应时间单位，当超时时，就会从服务器列表中移除超时服务器。   

M：那initLimit=10呢？

Z：投票选举新leader的初始化时间。

M：什么是leader（领导者）呢?

Z：这来源于zookeeper的选主机制。在分布式系统中，选出主节点来控制其他节点或者是分配任务。   

M：配置文件中dataDir=/tmp/zookeeper是干嘛用的？

Z：数据持久化路径。

M：那有日志文件吗，怎么设置保存位置？

Z：直接添加``dataLogDir=/home/vlan/zookeeper/log``   

M：怎么设置zookeeper的端口呢？

Z：这里默认的是2181``clientPort=2181``   

