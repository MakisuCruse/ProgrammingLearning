package core.service.KnowledgeBase;

import cmu.arktweetnlp.Tagger;
import core.DAO.Impl.PersonTrendImpl;
import core.DAO.Impl.PlaceTrendImpl;
import core.DAO.Impl.TweetDaoImpl;
import core.DAO.Impl.UserDaoImpl;
import core.model.Person;
import core.model.Tweet;
import core.model.User;
import util.AccessDAO;

import java.io.IOException;
import java.util.List;

/**
 * Created by makisucruse on 2017/5/22.
 */
public class PersonPlaceAnalyse {
    private static TweetDaoImpl tweetDao = (TweetDaoImpl) AccessDAO.getTweetDao();

    private static UserDaoImpl userDao = (UserDaoImpl) AccessDAO.getUserDao();

    private static PersonTrendImpl personDao = (PersonTrendImpl) AccessDAO.getPersonDao();

    private static PlaceTrendImpl placeTrend = (PlaceTrendImpl) AccessDAO.getPlaceDao();

    public static void main(String[] args) throws IOException {
        PersonPlaceAnalyse.personAnalyse();
    }

    private static void personAnalyse() throws IOException {
        List<Tweet> tweets = tweetDao.findAllTweet();
        Tagger tagger = getTagger();
        for (Tweet t : tweets) {
            //发推者发了小于十条的消息就忽略该条推文,发推者粉丝数小于50就忽略该推文
            List<User> u = userDao.findUser(t.getUserId());
            if (u.size() != 0) {
                User user = userDao.findUser(t.getUserId()).get(0);
                if (user.getUserStatusCount() < 100 || user.getFollowerCount() < 1000) continue;
                List<Tagger.TaggedToken> tokens = tagger.tokenizeAndTag(t.getText());
                tokens.stream().filter(taggedToken -> taggedToken.token.startsWith("@")).forEach(taggedToken -> {
                    List<User> users = userDao.findUserThroughScreenName(taggedToken.token.substring(1));
                    if (users.size() != 0)
                        personDao.addPerson(new Person(taggedToken.token, t.getTrendName(), users.get(0).getFollowerCount()));
                    else
                        personDao.addPerson(new Person(taggedToken.token, t.getTrendName(), -1));
                });
            }

        }
    }

    private static Tagger getTagger() throws IOException {
        Tagger tagger = new Tagger();
        tagger.loadModel("/Users/makisucruse/Downloads/TwitterAnalyseCopy/model.txt");
        return tagger;
    }
}
