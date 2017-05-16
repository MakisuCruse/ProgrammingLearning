package util;

import core.DAO.ITweetDAO;
import core.DAO.IUserDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsOperations;

/**
 * Created by makisucruse on 2017/3/28.
 */

public class AccessDAO {
    static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static ITweetDAO getTweetDao() {
        return (ITweetDAO) context.getBean("tweetDao");
    }

    public static IUserDAO getUserDao() {
        return (IUserDAO) context.getBean("userDao");
    }

    public static JmsOperations getTwitterMsg() {
        return (JmsOperations) context.getBean("jmsTemplate");
    }
}
