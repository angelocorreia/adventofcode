package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Day9 {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/day9.txt")));
        String s = reader.readLine();
        List<List<Integer>> sequenceList = new ArrayList<>();
        List<List<Integer>> subLists = new ArrayList<>();
        while (s != null) {
            sequenceList.add(Arrays.asList(s.split(" ")).stream().map(Integer::valueOf).collect(Collectors.toList()));
            s = reader.readLine();
        }

        int part1total = 0;
        int part2total = 0;
        int counter = 0;
        for (List<Integer> seq : sequenceList) {
            subLists.add(seq);
            List<Integer> newList = getNextList(sequenceList.get(counter));
            while (!newList.stream().allMatch(i -> i == 0)) {
                subLists.add(newList);
                newList = getNextList(newList);
            }

            for (int i = 0; i < subLists.size(); i++) {

                part1total = part1total + subLists.get(i).get(subLists.get(i).size() - 1);

            }
            for (int i = subLists.size() - 2; i >= 0; i--) {
                int toAdd = subLists.get(i).get(0) - subLists.get(i + 1).get(0);
                subLists.get(i).add(0, toAdd);
                if (i == 0) {
                    part2total = part2total + toAdd;
                }

            }
            counter++;
            subLists.clear();
        }
        System.out.println("Other total " + part1total);
        System.out.println("Part 2 " + part2total);

    }

    public static List<Integer> getNextList(List<Integer> list) {
        List<Integer> newList = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            newList.add(list.get(i + 1) - list.get(i));
        }
        return newList;
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


}
