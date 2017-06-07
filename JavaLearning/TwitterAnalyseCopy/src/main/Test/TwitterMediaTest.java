import twitter4j.*;
import util.OAuth;

/**
 * Created by makisucruse on 2017/5/24.
 */
public class TwitterMediaTest {
    public static void main(String[] args) throws TwitterException {
        Twitter twitter = OAuth.oAuthTwitter(1);
        Query query = new Query("Kyrie");
        query.setLang("en");
        query.setCount(100);
        QueryResult result = twitter.search(query);
        for (Status status : result.getTweets()) {
            MediaEntity[] entities = status.getMediaEntities();
            System.out.println(entities.length);
//            for (MediaEntity e : entities) {
//
//                System.out.println(e.getMediaURL());
//                System.out.println(e.getText());
//            }
        }
    }
}
