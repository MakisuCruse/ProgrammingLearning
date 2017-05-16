package util;

import core.Algo.FrequentCount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by makisucruse on 2017/4/14.
 */
public class DataPreProcessors {

    public static boolean nGramHasAddWord(String text, Map<String, Integer> words) {
        for (Map.Entry<String, Integer> m : words.entrySet()) {
            String[] strs = m.getKey().split(" ");
            for (String s : strs) {
                if (text.equals(s)) return true;
            }
        }
        return false;
    }

    private static List<Map.Entry<String, Integer>> sortMap(Map<String, Integer> wordCount) {
        List<Map.Entry<String, Integer>> tmp = new ArrayList<>(wordCount.entrySet());
        Collections.sort(tmp, (o1, o2) -> o2.getValue() - o1.getValue());
        return tmp;
    }

    public static void nGramClean(Map<String, Integer> map) {
        //移除子词组
        ArrayList<String> removeList = new ArrayList<>();
        for (Map.Entry<String, Integer> m : map.entrySet()) {
            String[] strs = m.getKey().split(" ");
            if (strs.length != 0) {
                for (String s : strs) {
                    if (map.containsKey(s)) removeList.add(s);
                }
            }
        }
        removeList.forEach(map::remove);
        //移除已经出现的但是不规范的词组
        List<Map.Entry<String, Integer>> lst = sortMap(map);
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();
        for (Map.Entry<String, Integer> m : lst) {
            l1.add(m.getKey());
            l2.add(m.getKey().toLowerCase());
        }
        for (int i = 0; i < l2.size() - 1; i++) {
            for (int j = i + 1; j < l2.size(); j++) {
                if (l2.get(i).equals(l2.get(j))) {
                    if (map.containsKey(l1.get(j))) map.remove(l1.get(j));
                }
            }
        }
    }

    public static String truncationText(String text) {
        if (text.matches(".*[,\\.!&:\\-`\"']$")) {
            if (text.trim().length() == 1) return "";
            else {
                text = text.substring(0, text.length() - 1);
            }
        }
        if (text.matches("^[\"].*")) {
            if (text.length() == 1) return "";
            else {
                text = text.substring(1, text.length());
            }
        }
        return text;
    }

    public static boolean shouldBeIgnore(String text, String trend) {
        String trendReg = ".*" + trend.toLowerCase() + ".*";
        return text.matches("^http.*") || text.toLowerCase().matches(trendReg) || binaryInExcludeSet(text) || "".equals(text);
    }

    public static boolean shouldBeIgnoreNGram(String text) {
        if ("".equals(text)) return true;
        ArrayList<String> lst = getStopWord();
        for (String s : lst) {
            String[] tmp = text.split(" ");
            for (String t : tmp) {
                if (s.equals(t.toLowerCase())) return true;
            }
        }
        return false;
    }

    private static boolean binaryInExcludeSet(String text) {
        ArrayList<String> lst = getStopWord();
        int low = 0;
        int high = lst.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            String midVal = lst.get(mid);
            if (text.toLowerCase().compareTo(midVal) == 0) return true;
            if (text.toLowerCase().compareTo(midVal) < 0) high = mid - 1;
            else low = mid + 1;
        }
        return false;
    }

    private static ArrayList<String> getStopWord() {
        String filePath = FrequentCount.class.getClassLoader().getResource("stopword").getPath();
        ArrayList<String> ret = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = reader.readLine()) != null) {
                ret.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Collections.sort(ret);
        return ret;
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("Happy Passover", 1000);
        map.put("Happy", 500);
        map.put("Passover", 300);
        map.put("happy passover", 100);
        map.put("happy Passover", 50);
        nGramClean(map);
        map.entrySet().forEach(System.out::println);
    }
}

