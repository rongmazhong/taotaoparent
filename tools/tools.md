## 启动zookeeper      
```bash
3 常用命令
3.1. 启动ZK服务:        bin/zkServer.sh start
3.2. 查看ZK服务状态:  bin/zkServer.sh status
3.3  停止ZK服务:        bin/zkServer.sh stop
3.4. 重启ZK服务:        bin/zkServer.sh restart
3.5  连接服务器          zkCli.sh -server 127.0.0.1:2181
3.6  查看根目录 ls /
3.7  创建 testnode节点,关联字符串"zz"         create /zk/testnode "zz"
3.8  查看节点内容  get /zk/testnode
3.9  设置节点内容  set /zk/testnode abc
4.0  删除节点      delete /zk/testnode
```     
## 启动fastdfs     
```bash
docker start storage
docker start tracker 
```    
## redis

