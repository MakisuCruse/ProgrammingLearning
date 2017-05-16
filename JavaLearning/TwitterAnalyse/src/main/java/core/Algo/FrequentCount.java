package core.Algo;

import util.DataPreProcessors;

import java.util.*;

/**
 * Created by makisucruse on 2017/3/28.
 */
public class FrequentCount {
    public static List<Map.Entry<String, Integer>> analyseTrend(String[] texts, String trend) {
        Map<String, Integer> wordCount = new HashMap<>();
        //添加nGram分词的结果
        for (String text : nGram(texts, 2)) {
            text = text.trim();
            if (DataPreProcessors.shouldBeIgnoreNGram(text)) continue;
            if (!wordCount.containsKey(text)) {
                wordCount.put(text, 1);
            } else {
                wordCount.put(text, wordCount.get(text) + 1);
            }
        }
        DataPreProcessors.nGramClean(wordCount);
        for (String text : texts) {
            text = DataPreProcessors.truncationText(text);
            if (DataPreProcessors.shouldBeIgnore(text, trend)) continue;
            if (DataPreProcessors.nGramHasAddWord(text, wordCount)) continue;
            if (!wordCount.containsKey(text)) {
                wordCount.put(text, 1);
            } else {
                wordCount.put(text, wordCount.get(text) + 1);
            }
        }
        return sortMap(wordCount);
    }

    private static List<Map.Entry<String, Integer>> sortMap(Map<String, Integer> wordCount) {
        List<Map.Entry<String, Integer>> tmp = new ArrayList<>(wordCount.entrySet());
        Collections.sort(tmp, (o1, o2) -> o2.getValue() - o1.getValue());
        return tmp;
    }

    private static List<String> nGram(String[] strs, int n) {
        List<String> ret = new ArrayList<>();
        for (int i = 0; i <= strs.length - n; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = i; j < i + n; j++) {
                builder.append(strs[j]).append(" ");
            }
            ret.add(builder.toString());
        }
        return ret;
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("abc", 3);
        map.put("xt", 20);
        map.put("xz", 55);
        List<Map.Entry<String, Integer>> lst = sortMap(map);
        for (Map.Entry<String, Integer> m : lst) {
            System.out.println(m.getKey() + ";" + m.getValue());
        }
    }
}
