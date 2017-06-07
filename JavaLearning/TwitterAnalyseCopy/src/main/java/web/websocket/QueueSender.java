package web.websocket;


import com.alibaba.fastjson.JSON;
import core.model.QueueData;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

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
     * @param data
     */
    public static void sendMqMessage(JmsTemplate jmsTemplate, final QueueData data) {
        jmsTemplate.send(jmsTemplate.getDefaultDestination(), new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                String jsonStr = JSON.toJSONString(data);
//                System.out.println(jsonStr);
                return session.createTextMessage(jsonStr);
            }
        });
        System.out.println("spring send message...");
    }
}
