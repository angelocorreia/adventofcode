package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
    public static void main(String[] args) throws Exception {


        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/day6.txt")));
        String s = reader.readLine();
        String[] times = null, distances = null;
        List<Race> races = new ArrayList<>();
        while (s != null) {

            if (s.startsWith("Time")) {
                times = s.split(":")[1].trim().replaceAll("\\s+", "").split("-");
            } else if (s.startsWith("Distance")) {
                distances = s.split(":")[1].trim().replaceAll("\\s+", "").split("-");
            }
            s = reader.readLine();
        }
        Double odds = null;
        for (int i = 0; i < times.length; i++) {
            Race race = new Race(Long.parseLong(times[i]), Long.parseLong(distances[i]));
            races.add(race);
            if (odds == null) {
                odds = race.upper - race.lower + 1;
            } else {
                odds = odds * (race.upper - race.lower + 1);
            }
        }
        System.out.println("odds: " + odds);
    }

    private static class Race {
        long time;
        long distance;

        double lower, upper;

        public Race(long time, long distance) {
            this.time = time;
            this.distance = distance + 1;
            calculate();
        }

        private void calculate() {
            double quad = Math.sqrt(Math.pow(-time, 2) - 4 * distance);
            upper = Math.floor((time + quad) / 2);
            lower = Math.ceil((time - quad) / 2);
        }
    }

}
