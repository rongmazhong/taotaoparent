#!/bin/bash
## 在/home/docker/redis-cluster下生成conf和data目标，并生成配置信息
$ for port in `seq 7000 7005`; do \
  mkdir -p ./${port}/conf \
  && PORT=${port} envsubst < ./redis-cluster.tmpl > ./${port}/conf/redis.conf \
  && mkdir -p ./${port}/data; \
done

## 创建6个redis容器
for port in `seq 7000 7005`; do \
  docker run -d -ti -p ${port}:${port} -p 1${port}:1${port} \
  -v /home/docker/redis-cluster/${port}/conf/redis.conf:/usr/local/etc/redis/redis.conf \
  -v /home/docker/redis-cluster/${port}/data:/data \
  --restart always --name redis-${port} --net redis-net \
  --sysctl net.core.somaxconn=1024 redis redis-server /usr/local/etc/redis/redis.conf; \
done

## 查看创建的redis容器
docker ps
