package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Day9 {
    public static void main(String[] args) throws Exception {
        List<int[]> sequences = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/day9.txt")));
        String s = reader.readLine();
        List<List<Integer>> sequenceList = new ArrayList<>();
        while (s != null) {
            sequences.add(Arrays.stream(s.split(" ")).mapToInt(Integer::valueOf).toArray());
            sequenceList.add(Arrays.asList(s.split(" ")).stream().map(Integer::valueOf).collect(Collectors.toList()));
            s = reader.readLine();
        }
        int totalSum = 0;
        for (int[] seq : sequences) {
            totalSum = totalSum + getNextInSequence(seq, seq.length - 1, 0);

        }
        System.out.println("Total "+ totalSum);

        for (int i=0; i<sequences.size();i++){
            System.out.println(sequences.get(i));
        }
    }

    public static List<Integer> getNextList(List<Integer> list) {
        List<Integer> newList= new ArrayList<>();
        for (int i =0;i<list.size()-2;i++){
            newList.add(list.get(i+1)-list.get(i));
        }
        return newList;
    }

    public static int getNextInSequence(int[] sequence, int index, int level) {
        int lastInSequence = getFromSequence(sequence, index, level);
        return lastInSequence + getSumOfDiff(sequence, index, level);
    }

    public static int getFromSequence(int[] sequence, int index, int level) {
        if (level == 0) {
            return sequence[index];
        } else {
            int priorUpper = getFromSequence(sequence, index + 1, level - 1);
            int priorLower = getFromSequence(sequence, index, level - 1);
            return priorUpper - priorLower;
        }
    }

    public static int getSumOfDiff(int[] sequence, int index, int level) {
        int lastInSequence = getFromSequence(sequence, index, level);
        int nextLower = getFromSequence(sequence, index - 1, level);
        int diff = lastInSequence - nextLower;
        int sum = diff;
        if (diff > 0) {
            sum = sum + getSumOfDiff(sequence, index - 1, level + 1);
        }
        return sum;
    }


}
