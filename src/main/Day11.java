package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {
    static char[][] universe;
    static List<Integer> blankRows = new ArrayList<>();
    static List<Integer> blankColumns = new ArrayList<>();
    static int muliplier = 1;
    public static void main(String[] args) throws Exception {

        List<String> rawInput = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/day11demo.txt")));
        String s = reader.readLine();

        while (s != null) {
            rawInput.add(s);
            s = reader.readLine();
        }

        universe = new char[rawInput.size()][rawInput.get(0).length()];
        for (int i = 0; i < rawInput.size(); i++) {
            universe[i] = rawInput.get(i).toCharArray();
        }


        // find blank rows
        for (int i = 0; i < universe.length ; i++) {
            boolean blankRow = true;
            for (int j = 0; j < universe[i].length; j++) {
                if (universe[i][j] == '#') {
                    blankRow = false;
                }
            }
            if (blankRow) {
                blankRows.add(i);
            }
        }
        // find blank columns
        for (int i = 0; i < universe[0].length ; i++) {
            boolean blankCol = true;
            for (int j = 0; j < universe.length; j++) {
                if (universe[j][i] == '#') {
                    blankCol = false;
                }
            }
            if (blankCol) {
                blankColumns.add(i);
            }
        }

        // get galaxy coordinates
        int galaxyNum = 0;
        List<Galaxy> galaxies = new ArrayList<>();
        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe[0].length; j++) {
                if(universe[i][j]=='#'){
                    galaxies.add(new Galaxy(galaxyNum, i,j));
                    galaxyNum = galaxyNum +1;
                }
            }
        }
        int sum = 0;
        System.out.println("Found " + galaxies.size() + " galaxies");
        for (int i = 0;i<galaxies.size()-1;i++){
            for (int j = i+1;j<galaxies.size();j++){
                int thisSum = galaxies.get(i).getDistance(galaxies.get(j));
                System.out.println("Comparing " + i +" and " + j + " distance is " + thisSum);
                sum = sum + galaxies.get(i).getDistance(galaxies.get(j));
            }
        }
        System.out.println("Sum of distances" + sum);

    }

    private static int getBlankColumnsBetween(int lower, int upper) {
        return blankColumns.stream().filter(c->c>lower && c<upper).collect(Collectors.toList()).size()*muliplier;
    }
    private static int getBlankRowsBetween(int lower, int upper) {
        return blankRows.stream().filter(c->c>lower && c<upper).collect(Collectors.toList()).size()*muliplier;
    }
    private static class Galaxy{
        int num;int x;int y;

        public Galaxy(int num, int x, int y) {
            this.num = num;
            this.x = x;
            this.y = y;
        }

        public int getDistance(Galaxy other) {
            int blankRows = getBlankRowsBetween(x, other.x);
            int blankColumns = getBlankColumnsBetween(y, other.y);
            int newX=0, newY=0, otherX=0, otherY=0;
            if (x > other.x) {
                newX = x+blankRows;
                otherX = other.x;
            } else {
                newX = x;
                otherX = other.x+blankRows;
            }
            if (y > other.y) {
                newY = y+blankColumns;
                otherY = other.y;
            } else {
                newY = y;
                otherY = other.y+blankColumns;
            }
            int intialDistance= Math.abs(newX - otherX) + Math.abs(newY-otherY);
            return intialDistance;
        }
    }



}
