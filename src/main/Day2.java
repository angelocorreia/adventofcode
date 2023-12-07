package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day2 {
    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/day2.txt")));
        String s = reader.readLine();
        List<Game> games = new ArrayList<>();
        while (s != null) {

            String game = s.substring(5, s.indexOf(":"));
            Game gameObj = new Game();
            gameObj.number = Integer.valueOf(game);
            String cubes[] = s.substring(s.indexOf(":") + 1).split(";");
            for (String cube : cubes) {
                String colors[] = cube.split(",");
                for (String color : colors) {
                    String colorSplit[] = color.split(" ");
                    int count = Integer.valueOf(colorSplit[1]);
                    switch (colorSplit[2]) {
                        case "red":
                            if (gameObj.red < count)
                                gameObj.red = count;
                            break;
                        case "blue":
                            if (gameObj.blue < count)
                                gameObj.blue = count;
                            break;
                        case "green":
                            if (gameObj.green < count)
                                gameObj.green = count;
                            break;
                    }
                }
            }
            games.add(gameObj);
            s = reader.readLine();
        }

        AtomicInteger sum = new AtomicInteger();

        games.stream().filter(g -> g.red <= 12 && g.green <= 13 && g.blue <= 14).forEach(g -> sum.addAndGet(g.number));
        AtomicInteger power = new AtomicInteger();
        games.stream().forEach(g -> power.addAndGet(g.red  * g.blue * g.green));
        System.out.println("sum " + sum.get() + " power " + power);
    }

    public static class Game {
        int number;
        int red;
        int green;
        int blue;
    }
}
