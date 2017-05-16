package web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import util.AccessDAO;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by makisucruse on 2017/5/15.
 */
@ServerEndpoint("/websocket/{myWebsocket}")
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    public static Map<String, Session> clients = new ConcurrentHashMap<String, Session>();

    /**
     * 打开连接时触发
     *
     * @param myWebsocket
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("myWebsocket") String myWebsocket, Session session) {
        logger.info("Websocket Start Connecting:" + myWebsocket);
        System.out.println("进入：" + myWebsocket);
        clients.put(myWebsocket, session);
    }

    /**
     * 收到客户端消息时触发
     *
     * @param myWebsocket
     * @param message
     * @return
     */
    @OnMessage
    public void onMessage(@PathParam("myWebsocket") String myWebsocket, String message, Session session) throws IOException, JMSException {
        JmsTemplate template = (JmsTemplate) AccessDAO.getTwitterMsg();
        Destination destination = template.getDefaultDestination();
        while (template.receive(destination) != null) {
            TextMessage msg = (TextMessage) template.receive(destination);
            if (msg != null) {
                session.getBasicRemote().sendText(msg.getText());
            }
        }
//        session.getBasicRemote().sendText(message);
    }

    /**
     * 异常时触发
     *
     * @param myWebsocket
     * @param throwable
     */
    @OnError
    public void onError(@PathParam("myWebsocket") String myWebsocket, Throwable throwable) {
        logger.info("Websocket Connection Exception:" + myWebsocket);
        logger.info(throwable.getMessage(), throwable);
        clients.remove(myWebsocket);
    }

    /**
     * 关闭连接时触发
     *
     * @param myWebsocket
     */
    @OnClose
    public void onClose(@PathParam("myWebsocket") String myWebsocket) {
        logger.info("Websocket Close Connection:" + myWebsocket);
        clients.remove(myWebsocket);
    }


    /**
     * 将数据传回客户端
     * 异步的方式
     *
     * @param myWebsocket
     * @param message
     */
    public static void broadcast(String myWebsocket, String message) throws IOException {
        if (clients.containsKey(myWebsocket)) {
            clients.get(myWebsocket).getBasicRemote().sendText(message);
        }
    }
}
