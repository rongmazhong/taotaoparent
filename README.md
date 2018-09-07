# taotaoparent    
### 淘淘商城学习项目

```
说明：本项目根据csdn自己开发的分布式java项目。   
其中，所有底层硬件均用docker实现，减少开发配置服务器的时间。
启动前先看 tools中的tools.md。
项目搭建主要有： fastdfd、solr、redis、activemq等。
dubbo的zookeeper注册中心用单独一台虚拟机集群部署，其它实例可用基于docker部署。
```

启动顺序：
- 先启动taotao-manager
- 再启动taotao-content
- 再启动taotao-search
- 再启动taotao-manager-web
- 再启动taotao-search-web
- 再启动taotao-portal-web

  