package com.taotao.activemq;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * 〈springActivemq测试〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/6
 */

public class SpringActivemqTest {

    @Test
    public void testJmsTemplate() throws Exception {
        //初始化spring容器
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //从容器中获取JmsTemplate
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        //获取destination
        Destination destination = (Destination) applicationContext.getBean("test-queue");
        // 发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage te = session.createTextMessage("spring activemq send queue");
                return te;
            }
        });
    }
}
