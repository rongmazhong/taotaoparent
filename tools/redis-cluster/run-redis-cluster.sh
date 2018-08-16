#!/bin/bash
## 通过启动ruby来实现集群

echo yes | docker run -i --rm --net redis-net ruby sh -c '\
  gem install redis \
  && wget http://download.redis.io/redis-stable/src/redis-trib.rb \
  && ruby redis-trib.rb create --replicas 1 \
  '"$(for port in `seq 7000 7005`; do \
    echo -n "$(docker inspect --format '{{ (index .NetworkSettings.Networks "redis-net").IPAddress }}' "redis-${port}")":${port} ' ' ; \
  done)"
