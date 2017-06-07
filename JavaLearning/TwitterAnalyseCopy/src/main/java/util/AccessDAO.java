package util;

import core.DAO.*;
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

    public static IPersonTrend getPersonDao() {
        return (IPersonTrend) context.getBean("personDao");
    }

    public static IPlaceTrend getPlaceDao() {
        return (IPlaceTrend) context.getBean("placeDao");
    }

    public static ITopicDAO getTopicDao() {
        return (ITopicDAO) context.getBean("topicDao");
    }
}
