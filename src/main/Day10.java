package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day10 {

    static char[][] maze;
    static int[][] distances;

    public static void main(String[] args) throws Exception {

        List<String> rawInput = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/day10demo.txt")));
        String s = reader.readLine();

        while (s != null) {
            rawInput.add(s);
            s = reader.readLine();
        }
        maze = new char[rawInput.size()][rawInput.get(0).length()];
        distances = new int[rawInput.size()][rawInput.get(0).length()];
        int x = 0, y = 0;
        for (int i = 0; i < rawInput.size(); i++) {
            if (rawInput.get(i).contains("S")) {
                x = i;
                y = rawInput.get(i).indexOf('S');
            }
            maze[i] = rawInput.get(i).toCharArray();
        }
        int distance = 0;
        distances[x][y] = distance;
        // find the next 2 directions from x
        String dir1 = null, dir2 = null;
        int x1 = x, y1 = y, x2 = x, y2 = y;
        distance = distance + 1;
        if (x1 > 0) {
            // check north
            String nextDirPresent = getNextDirection("N", x - 1, y);
            if (nextDirPresent != null) {
                if (dir1 == null)
                    dir1 = "N";
                else if (dir2 == null)
                    dir2 = "N";
            }
        }
        if (x1 < rawInput.size() - 2) {
            // check south
            String nextDirPresent = getNextDirection("S", x + 1, y);
            if (nextDirPresent != null) {
                if (dir1 == null)
                    dir1 = "S";
                else if (dir2 == null)
                    dir2 = "S";
            }
        }
        if (y1 > 0) {
            // check west
            String nextDirPresent = getNextDirection("W", x, y - 1);
            if (nextDirPresent != null) {
                if (dir1 == null)
                    dir1 = "W";
                else if (dir2 == null)
                    dir2 = "W";
            }
        }
        if (y1 < maze[0].length - 2) {
            // check east
            String nextDirPresent = getNextDirection("E", x, y + 1);
            if (nextDirPresent != null) {
                if (dir1 == null)
                    dir1 = "E";
                else if (dir2 == null)
                    dir2 = "E";
            }
        }
        switch (dir1) {
            case "N":
                x1 = x1 - 1;
                break;
            case "S":
                x1 = x1 + 1;
                break;
            case "E":
                y1 = y1 + 1;
                break;
            case "W":
                y1 = y1 - 1;
                break;
        }
        switch (dir2) {
            case "N":
                x2 = x2 - 1;
                break;
            case "S":
                x2 = x2 + 1;
                break;
            case "E":
                y2 = y2 + 1;
                break;
            case "W":
                y2 = y2 - 1;
                break;
        }
        distances[x1][y1] = distance;
        distances[x2][y2] = distance;
        System.out.println("Starting off at " + dir1 + " and " + dir2 + " at " + x1 + "," + y1 + " " + x2 + "," + y2);
        ;

        boolean found = false;

        while (!found) {

            System.out.println("Headed " + dir1 + " and " + dir2 + " to " + x1 + "," + y1 + " " + x2 + "," + y2);

            dir1 = getNextDirection(dir1, x1, y1);
            dir2 = getNextDirection(dir2, x2, y2);
            distance = distance + 1;

            switch (dir1) {
                case "N":
                    x1 = x1 - 1;
                    break;
                case "S":
                    x1 = x1 + 1;
                    break;
                case "E":
                    y1 = y1 + 1;
                    break;
                case "W":
                    y1 = y1 - 1;
                    break;
            }
            switch (dir2) {
                case "N":
                    x2 = x2 - 1;
                    break;
                case "S":
                    x2 = x2 + 1;
                    break;
                case "E":
                    y2 = y2 + 1;
                    break;
                case "W":
                    y2 = y2 - 1;
                    break;
            }
            if (distances[x1][y1] > 0) {
                found = true;
            } else {
                distances[x1][y1] = distance;
            }
            if (distances[x2][y2] > 0) {
                found = true;
            } else {
                distances[x2][y2] = distance;
            }
        }
        System.out.println("midpoint " + distance);
        fillOutside(0, 0);

        fillInside(x ,y);
        System.out.println("area " + countArea());
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                System.out.print(distances[i][j]);
            }
            System.out.print("\n");
        }
    }


    public static boolean isCompatible(String source, String connector) {
        switch (connector) {
            case "NS":
                switch (source) {
                    case "N", "S":
                        return true;
                }
                break;
            case "EW":
                switch (source) {
                    case "E", "W":
                        return true;
                }
                break;
            case "NE":
                switch (source) {
                    case "S", "W":
                        return true;
                }
                break;
            case "NW":
                switch (source) {
                    case "S", "E":
                        return true;
                }
                break;

            case "SE":
                switch (source) {
                    case "N", "W":
                        return true;
                }
                break;
            case "SW":
                switch (source) {
                    case "N", "E":
                        return true;
                }
                break;

        }
        return false;
    }

    public static String getNextDirection(String source, int x, int y) {
        String connector = getConnector(x, y);
        if (connector != null && isCompatible(source, connector)) {
            switch (source) {
                case "N":
                    if (connector.equals("NS"))
                        return "N";
                    if (connector.equals("SE"))
                        return "E";
                    if (connector.equals("SW"))
                        return "W";
                case "S":
                    if (connector.equals("NS"))
                        return "S";
                    if (connector.equals("NE"))
                        return "E";
                    if (connector.equals("NW"))
                        return "W";
                case "E":
                    if (connector.equals("EW"))
                        return "E";
                    if (connector.equals("SW"))
                        return "S";
                    if (connector.equals("NW"))
                        return "N";
                case "W":
                    if (connector.equals("EW"))
                        return "W";
                    if (connector.equals("SE"))
                        return "S";
                    if (connector.equals("NE"))
                        return "N";
            }
            return connector.replaceAll(source, "");
        }

        return null;
    }

    public static String getConnector(int x, int y) {
        char c = maze[x][y];
        switch (c) {
            case '|': {
                return "NS";
            }
            case '-': {
                return "EW";
            }
            case 'J': {
                return "NW";
            }
            case '7': {
                return "SW";
            }
            case 'F': {
                return "SE";
            }
            case 'L': {
                return "NE";
            }
            default:
                return null;
        }
    }

    public static void fillOutside(int x, int y) {
        if (distances[x][y] == 0) {
            distances[x][y] = 1;
        }
        for (int a = x-1;a<=x+1;a++) {
            for (int b = y-1;b<=y+1;b++) {
                if (a>=0 && a<distances.length && b>=0 && b<distances[0].length) {
                    if (distances[a][b] == 0 && a !=x && b!= y) {
                        distances[a][b] = 1;
                        fillOutside(a, b);

                    }
                }
            }

        }

    }

    public static void fillInside(int x, int y) {
        if (distances[x][y] == 0) {
            distances[x][y] = 2;
        }
        for (int a = x-1;a<=x+1;a++) {
            for (int b = y-1;b<=y+1;b++) {
                if (a>=0 && a<distances.length && b>=0 && b<distances[0].length) {
                    if (distances[a][b] == 0 && a !=x && b!= y) {
                        distances[a][b] = 2;
                        fillInside(a, b);

                    }
                }
            }

        }

    }
    public static int countArea() {
        int count = 0;

        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                if (distances[i][j] == 2) {
                    count = count + 1;
                } else {
                    distances[i][j] = 1;
                }
            }
        }
        return count;
    }
}
