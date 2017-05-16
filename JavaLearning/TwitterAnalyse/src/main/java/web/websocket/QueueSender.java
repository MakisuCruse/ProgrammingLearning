package web.websocket;


import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by makisucruse on 2017/5/15.
 */
public class QueueSender {
    /**
     * 说明:发送的时候如果这里没有显示的指定destination.将用spring xml中配置的destination
     *
     * @param destination
     * @param message
     */
    public static void sendMqMessage(JmsTemplate jmsTemplate, Destination destination, final String message) {
        if (null == destination) {
            destination = jmsTemplate.getDefaultDestination();
        }
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
        System.out.println("spring send message...");
    }
}
