package com.taotao.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * 〈Activemq测试〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/5
 */
public class TestActiveMq {

    @Test
    public void testQueueProducer () throws Exception {
        // // 创建一个连接工厂对象，需要指定mq服务的ip和端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.0.0.70:61616");
        // // 创建连接对象
        Connection connection = connectionFactory.createConnection();
        // // 开启连接，调用start方法
        connection.start();
        // // 创建session对象
        // // (第一个参数是否开启分布式事务，一般不用，保证数据的最终一致，可以使用消息队列实现；)
        // // 第二个参数是应答模式,
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // // 创建Destination对象，两种形式 queue、topic
        Queue queue = session.createQueue("test-queue");
        // // 创建produce对象
        MessageProducer producer = session.createProducer(queue);
        // // 创建TextMessage对象
        // TextMessage textMessage = new ActiveMQTextMessage();
        // textMessage.setText("hello activemq");
        TextMessage textMessage = session.createTextMessage("hello activemq");
        // 发送消息
        producer.send(textMessage);
        // // 关闭资源
        connection.close();
    }
}
