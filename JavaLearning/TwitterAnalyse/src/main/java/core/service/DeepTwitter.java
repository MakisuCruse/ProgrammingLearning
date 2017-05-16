package core.service;

import twitter4j.*;
import util.OAuth;

/**
 * Created by makisucruse on 2017/5/2.
 */
public class DeepTwitter {
    public static void main(String[] args) throws TwitterException {
        Twitter twitter = OAuth.oAuthTwitter(1);
        Query q = new Query("#FireColbert");
        q.setLang("en");
        q.setCount(100);
        QueryResult result = twitter.search(q);
        for (Status status : result.getTweets()) {
//            System.out.println(status.getUser().getScreenName() + ";" + status.getUser().getStatusesCount());
//            System.out.println(status.getUser().getFollowersCount() + ";" + status.getUser().getFriendsCount() + ";" + status.getFavoriteCount());
            User user = status.getUser();
            System.out.println(user.getLocation());
            System.out.println(status.getGeoLocation().getLatitude() + status.getGeoLocation().getLongitude());
//            System.out.println(user.getOriginalProfileImageURL());
        }
    }
}
