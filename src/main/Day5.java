package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
    private static List<String> seeds = new ArrayList<>();

    private static List<Long> part2seeds = new ArrayList<>();
    static List<SeedMap> seedToSoil = new ArrayList<>();
    static List<SeedMap> soilToFert = new ArrayList<>();
    static List<SeedMap> fertToWater = new ArrayList<>();
    static List<SeedMap> waterToLight = new ArrayList<>();
    static List<SeedMap> lightToTemp = new ArrayList<>();
    static List<SeedMap> tempToHum = new ArrayList<>();
    static List<SeedMap> humToLoc = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/day5.txt")));
        String s = reader.readLine();

        List<SeedMap> currentMap = null;
        while (s != null) {
            if (!s.trim().isEmpty()) {

                if (s.startsWith("seeds")) {
                    String seedsStr = s.substring(s.indexOf("seeds") + 7);
                    seeds = Arrays.stream(seedsStr.split(" ")).filter(seed -> seed.trim() != null).map(seed -> seed.trim()).toList();

                    s = reader.readLine();
                    continue;
                } else if (s.startsWith("seed-to-soil")) {
                    currentMap = seedToSoil;
                    s = reader.readLine();
                    continue;
                } else if (s.startsWith("soil-to-fertilizer")) {
                    currentMap = soilToFert;
                    s = reader.readLine();
                    continue;
                } else if (s.startsWith("fertilizer-to-water")) {
                    currentMap = fertToWater;
                    s = reader.readLine();
                    continue;
                } else if (s.startsWith("water-to-light")) {
                    currentMap = waterToLight;
                    s = reader.readLine();
                    continue;
                } else if (s.startsWith("light-to-temperature")) {
                    currentMap = lightToTemp;
                    s = reader.readLine();
                    continue;
                } else if (s.startsWith("temperature-to-humidity")) {
                    currentMap = tempToHum;
                    s = reader.readLine();
                    continue;
                } else if (s.startsWith("humidity-to-location")) {
                    currentMap = humToLoc;
                    s = reader.readLine();
                    continue;
                }
                List<Long> vals = Arrays.stream(s.split(" ")).map(Long::valueOf).collect(Collectors.toList());
                currentMap.add(new SeedMap(vals));
            }

            s = reader.readLine();
        }


        long lowestLoc = 0;

        Long first = null;
        Integer range = null;
        for (String seed:seeds) {
            if (first == null){
                first = Long.valueOf(seed);
                continue;
            } else if (range == null) {
                range = Integer.valueOf(seed);
                for (int i=0;i<range;i++){
                    long currentSeed =Long.valueOf(first) + i;
                    long loc = getLocation(currentSeed);
                    if (lowestLoc ==0) {
                        lowestLoc = loc;
                    } else if (loc< lowestLoc) {
                        lowestLoc = loc;
                    }
                }
                first = null;
                range = null;
            }

        }

        System.out.println( lowestLoc);
    }

    private static long getLocation(long numToCheck) {
        long soil = translate(numToCheck, seedToSoil);
        long fert = translate(soil, soilToFert);
        long water = translate(fert, fertToWater);
        long light = translate(water, waterToLight);
        long temp = translate(light, lightToTemp);
        long hum = translate(temp, tempToHum);
        long loc = translate(hum, humToLoc);
        return loc;
    }

    private static long translate(long numToCheck, List<SeedMap> seedMaps) {
        for (SeedMap seedMap : seedMaps) {
            if (seedMap.isInRange(numToCheck)) {
                return seedMap.translate(numToCheck);
            }
        }
        return numToCheck;
    }

    private static class SeedMap {
        public long source, dest, range;

        public SeedMap(List<Long> vals) {
            this.dest = vals.get(0);
            this.source = vals.get(1);
            this.range = vals.get(2);
        }

        public boolean isInRange(long numtocheck) {
            return numtocheck >= source && numtocheck < source + range;
        }

        public long translate(long numtocheck) {
            return numtocheck - source + dest;
        }
    }
}
