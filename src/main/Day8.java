package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day8 {
    public static int directionIndex = 0;
    public static String directions = null;
    public static Map<String, Node> nodes = new HashMap<>();

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/day8.txt")));
        String s = reader.readLine();
        directions = s;
        s = reader.readLine(); // ingore blank space
        Pattern nodePattern = Pattern.compile("(\\w+)=\\((\\w+),(\\w+)\\)");
        Matcher m = null;

        while (s != null) {
            s = s.replaceAll("\\s+", "");
            m = nodePattern.matcher(s);
            if (m.matches()) {
                Node n = new Node(m.group(1), m.group(2), m.group(3));
                nodes.put(n.id, n);
            }
            s = reader.readLine();
        }
        List<Node> endsWithA =nodes.values().stream().filter(n -> n.id.endsWith("A")).toList();
        //System.out.println("Part 1: " + findZZZ(nodes.get("AAA"), 0));
        List<Integer> locations = new ArrayList<>();
        endsWithA.forEach(n->locations.add(getLoopCount(n)));
        int[] locationsArray = locations.stream().mapToInt(i->i).toArray();
        System.out.println("LCM of the given array of numbers : " + lcm_of_array_elements(locationsArray));

    }

    private static class Node {
        String id, left, right;

        public Node(String id, String left, String right) {
            this.id = id;
            this.left = left;
            this.right = right;
        }
    }


    public static int findZZZ(Node node, int distance) {

        while (!node.id.equals("ZZZ")) {
            char nextDirection = getDirection();
            distance += 1;
            if (nextDirection == 'L') {
                node = nodes.get(node.left);
            } else {
                node = nodes.get(node.right);
            }
        }
        return distance;
    }

    public static char getDirection() {
        if (directionIndex == directions.length()) {
            directionIndex = 0;
        }
        return directions.charAt(directionIndex++);
    }

    public static int getLoopCount(Node node) {
        String initialNode = null;
        int loopCount = 0;

        while (!node.id.endsWith("Z")) {
            char nextDirection = getDirection();
            if (initialNode==null) {
                initialNode = node.id;
            }
            loopCount += 1;
            if (nextDirection == 'L') {
                node = nodes.get(node.left);
            } else {
                node = nodes.get(node.right);
            }
        }
        return loopCount;
    }
    public static long lcm_of_array_elements(int[] element_array)
    {
        long lcm_of_array_elements = 1;
        int divisor = 2;

        while (true) {
            int counter = 0;
            boolean divisible = false;

            for (int i = 0; i < element_array.length; i++) {

                // lcm_of_array_elements (n1, n2, ... 0) = 0.
                // For negative number we convert into
                // positive and calculate lcm_of_array_elements.

                if (element_array[i] == 0) {
                    return 0;
                }
                else if (element_array[i] < 0) {
                    element_array[i] = element_array[i] * (-1);
                }
                if (element_array[i] == 1) {
                    counter++;
                }

                // Divide element_array by devisor if complete
                // division i.e. without remainder then replace
                // number with quotient; used for find next factor
                if (element_array[i] % divisor == 0) {
                    divisible = true;
                    element_array[i] = element_array[i] / divisor;
                }
            }

            // If divisor able to completely divide any number
            // from array multiply with lcm_of_array_elements
            // and store into lcm_of_array_elements and continue
            // to same divisor for next factor finding.
            // else increment divisor
            if (divisible) {
                lcm_of_array_elements = lcm_of_array_elements * divisor;
            }
            else {
                divisor++;
            }

            // Check if all element_array is 1 indicate
            // we found all factors and terminate while loop.
            if (counter == element_array.length) {
                return lcm_of_array_elements;
            }
        }
    }

}
