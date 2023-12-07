package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Day3 {

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/day3.txt")));
        String s = reader.readLine();
        List<String> data = new ArrayList<>();
        while (s != null) {
            data.add(s);
            s = reader.readLine();
        }
        Map<String, Set<Integer>> engineParts = new HashMap<>();
        for (int rowNum = 0; rowNum < data.size(); rowNum++) {
            String row = data.get(rowNum);
            int index = 0;
            StringBuilder numBuilder = new StringBuilder();
            String  partIndex = null;
            for (int i = 0; i < row.length(); i++) {

                if (isNum(row.charAt(i))) {
                    numBuilder.append(row.charAt(i));
                    if (partIndex==null) {
                        partIndex = isAdjacent(data, rowNum, i);
//                        if (partIndex != null) {
//                            Set<Integer> parts = engineParts.get(partIndex);
//                            if (parts == null) {
//                                parts = new HashSet<>();
//                            }
//                            parts.add(Integer.valueOf(numBuilder.toString()));
//                            engineParts.put(partIndex, parts);
//                        }
                    }
                } else {
                    // reset
                    if (numBuilder.toString().length() > 0) {
                        if (partIndex != null) {
                            Set<Integer> parts = engineParts.get(partIndex);
                            if (parts == null) {
                                parts = new HashSet<>();
                            }
                            parts.add(Integer.valueOf(numBuilder.toString()));
                            engineParts.put(partIndex, parts);
                        }

                        numBuilder = new StringBuilder();
                        partIndex = null;
                    }
                }
                // get the last one
                if (i == row.length() - 1 && numBuilder.toString().length() > 0) {
                    // reset
                    if (numBuilder.toString().length() > 0) {
                        if (partIndex != null) {
                            Set<Integer> parts = engineParts.get(partIndex);
                            if (parts == null) {
                                parts = new HashSet<>();
                            }
                            parts.add(Integer.valueOf(numBuilder.toString()));
                            engineParts.put(partIndex, parts);
                        }

                        numBuilder = new StringBuilder();
                        partIndex = null;
                    }
                }
            }

        }

        int sum = 0;
        for (Set<Integer> parts : engineParts.values()) {
            if (parts.size()==2){
                List<Integer> setList = parts.stream().toList();
                sum = sum + (setList.get(0) * setList.get(1));
            }

        }
        System.out.println("part size" + engineParts.size() + " sum " + sum);
    }

    private static boolean isNum(char c) {
        return Character.isDigit(c);
    }

    private static boolean isGear(char c) {
        return c == '*';
    }

    private static String isAdjacent(List<String> data, int rowNum, int colNum) {
        String prevRow = rowNum > 0 ? data.get(rowNum - 1) : null;
        String nextRow = rowNum < data.size() - 1 ? data.get(rowNum + 1) : null;
        String currentRow = data.get(rowNum);
        if (prevRow != null) {
            String index = checkRow(prevRow, colNum);
            if (index != null) {
                return String.valueOf(rowNum-1) + "-" + index;
            }
        }
        if (currentRow != null) {
            String index = checkRow(currentRow, colNum);
            if (index != null) {
                return String.valueOf(rowNum) + "-" + index;
            }
        }
        if (nextRow != null) {
            String index = checkRow(nextRow, colNum);
            if (index != null) {
                return String.valueOf(rowNum+1) + "-" + index;
            }
        }
        return null;
    }

    private static String checkRow(String row, int colNum) {
        if (colNum > 0) {
            if (isGear(row.charAt(colNum - 1))) {
                return String.valueOf(colNum - 1);
            }
            if (isGear(row.charAt(colNum))) {
                return String.valueOf(colNum);
            }

        }

        if (colNum < (row.length() - 1) && isGear(row.charAt(colNum + 1))) {
            return String.valueOf(colNum + 1);
        }
        return null;
    }
}
