package web.websocket;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by makisucruse on 2017/5/15.
 */
public class ConsumerMessageListener implements MessageListener {
    //消息监听器
    //监听消费
    @Override
    public void onMessage(Message message) {
        TextMessage textMsg = (TextMessage) message;
        System.out.println("接收到一个纯文本消息。");
        try {
            System.out.println("消息内容是:" + textMsg.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
