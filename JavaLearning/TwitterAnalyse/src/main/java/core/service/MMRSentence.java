package core.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makisucruse on 2017/5/3.
 */
public class MMRSentence {
    static List<String> existSentences = new ArrayList<>();

    public static void main(String[] args) {
        existSentences.add("this is my book ");
        existSentences.add("that is her book");
        System.out.println(addToList("this is his dog"));
        System.out.println(addToList("this is my book"));
        System.out.println(addToList("this is his dog"));
    }

    private static boolean addToList(String text) {
        for (String sentence : existSentences) {
            String[] words = sentence.split(" ");
            String[] txt = text.split(" ");
            int repeat = 0;
            for (String s : words) {
                for (String t : txt) {
                    if (s.equals(t)) {
                        repeat++;
                    }
                }
            }
            if (words.length / repeat > 2) {
                existSentences.add(text);
                return true;
            }
        }
        return false;
    }
}
