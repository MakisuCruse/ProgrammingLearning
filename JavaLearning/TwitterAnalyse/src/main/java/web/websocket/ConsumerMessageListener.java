package web.websocket;

import web.controller.WebSocketController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

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
            System.out.println("消息内容是：" + textMsg.getText());
            WebSocketController.broadcast("client", textMsg.getText());
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        }
    }

}
