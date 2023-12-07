package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args) throws Exception {
        Map<String, Integer> patternMap = new HashMap<>();
        for (int i = 0;i< 10;i++) {
            patternMap.put(String.valueOf(i), i);
            switch (i) {
                case 0: patternMap.put("zero",i);
                case 1: patternMap.put("one",i);
                case 2: patternMap.put("two",i);
                case 3: patternMap.put("three",i);
                case 4: patternMap.put("four",i);
                case 5: patternMap.put("five",i);
                case 6: patternMap.put("six",i);
                case 7: patternMap.put("seven",i);
                case 8: patternMap.put("eight",i);
                case 9: patternMap.put("nine",i);
            }
        }

        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/input.txt")));
        String s= reader.readLine();
        Pattern pattern = Pattern.compile("(?=(" + patternMap.keySet().stream().collect(Collectors.joining("|")) + "))");
        int sum = 0;
        int counter = 0;
        while (s!= null ) {

            Matcher matcher = pattern.matcher(s);
            Integer first = null;
            Integer last = null;
            while (matcher.find()) {
                if (first == null) {
                    counter ++;
                    first = patternMap.get(matcher.group(1));
                }
                last = patternMap.get(matcher.group(1));
            }

            sum = sum + (first *10 + last);
            System.out.println("found numbers. Input: " + s + " first " + first + " last " + last + " sum " + sum);
            s = reader.readLine();
        }
        System.out.println("Total rows " + counter + " Sum = " + sum);

    }
}
