package core.service;

import core.model.TweetDescription;
import edu.stanford.nlp.simple.Sentence;
import util.TrendConcerning;

import java.util.List;

/**
 * Created by makisucruse on 2017/4/12.
 */
public class nerTag {
    public static void main(String[] args) {
        TweetDescription tweetDescription = getResult("#NationalTeachersDay");
        System.out.println(tweetDescription);
    }

    private static TweetDescription getResult(String trend) {
        String text = TrendConcerning.getFrequentWords(trend);
        StringBuilder position = new StringBuilder();
        StringBuilder time = new StringBuilder();
        StringBuilder person = new StringBuilder();
        StringBuilder event = new StringBuilder();
        Sentence sentence = new Sentence(text);
        List<String> words = sentence.words();
        for (int i = 0; i < words.size(); i++) {
            String tag = sentence.nerTag(i);
            String word = sentence.word(i);
            if ("DATE".equals(tag) || "TIME".equals(tag) || "DURATION".equals(tag) || "SET".equals(tag)) {
                time.append(word).append(" ");
            } else if ("LOCATION".equals(tag) || "MISC".equals(tag)) {
                position.append(word).append(" ");
            } else if ("PERSON".equals(tag) || "MISC".equals(tag) || word.startsWith("@")) {
                person.append(word).append(" ");
            } else {
                event.append(word).append(" ");
            }
        }
        TweetDescription td = new TweetDescription();
        td.setEvent(event.toString());
        td.setPerson(person.toString());
        td.setPosition(position.toString());
        td.setTime(time.toString());
        return td;
    }
}
