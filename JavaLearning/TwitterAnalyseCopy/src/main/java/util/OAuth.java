package util;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by makisucruse on 2017/3/29.
 */
public class OAuth {
    public static Twitter oAuthTwitter(int account) {
        Map<Integer, ArrayList<String>> accounts = getAccounts();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(accounts.get(account).get(0))
                .setOAuthConsumerSecret(accounts.get(account).get(1))
                .setOAuthAccessToken(accounts.get(account).get(2))
                .setOAuthAccessTokenSecret(accounts.get(account).get(3));
        TwitterFactory factory = new TwitterFactory(cb.build());
        return factory.getInstance();
    }

    private static Map<Integer, ArrayList<String>> getAccounts() {
        OrderedProperties properties = getProperities();
        Enumeration<Object> keys = properties.keys();
        Map<Integer, ArrayList<String>> ret = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            list.add(properties.getProperty(key));
        }
        for (int i = 0; i < list.size(); i += 4) {
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add(list.get(i));
            tmp.add(list.get(i + 1));
            tmp.add(list.get(i + 2));
            tmp.add(list.get(i + 3));
            ret.put(i / 4, tmp);
        }
        return ret;
    }

    private static OrderedProperties getProperities() {
        OrderedProperties properties = new OrderedProperties();
        //下面这个类写法***
        InputStream is = OAuth.class.getClassLoader().getResourceAsStream("properies");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}

class OrderedProperties extends Properties {
    private static final long serialVersionUID = -4627607243846121965L;

    private final LinkedHashSet<Object> keys = new LinkedHashSet<>();

    public Enumeration<Object> keys() {
        return Collections.enumeration(keys);
    }

    public Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }

    public Set<Object> keySet() {
        return keys;
    }

    public Set<String> stringPropertyNames() {
        return this.keys.stream().map(key -> (String) key).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
