package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Day7 {
    public static void main(String[] args) throws Exception {


        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/day7.txt")));
        String s = reader.readLine();
        List<Hand> hands = new ArrayList<>();
        while (s != null) {
            String[] line = s.split(" ");
            hands.add(new Hand(line[0], Integer.valueOf(line[1])));
            s = reader.readLine();
        }
        Collections.sort(hands);
        int sum = 0;
        for (int i = 0; i < hands.size(); i++) {
            sum = sum + hands.get(i).bid * (i + 1);
        }
        System.out.println(sum);
    }

    private static class Hand implements Comparable {
        public String cards;
        public int bid;
        public HandType handType;

        private static Map<Character, Integer> charRank = new HashMap<>();

        static {
            charRank.put('A', 13);
            charRank.put('K', 12);
            charRank.put('Q', 11);
            charRank.put('J', 0);
            charRank.put('T', 9);
            charRank.put('9', 8);
            charRank.put('8', 7);
            charRank.put('7', 6);
            charRank.put('6', 5);
            charRank.put('5', 4);
            charRank.put('4', 3);
            charRank.put('3', 2);
            charRank.put('2', 1);
        }

        public Hand(String cards, int bid) {
            this.cards = cards;
            this.bid = bid;
            calculateType();
        }

        private void calculateType() {
            Map<Character, Integer> charCount = new HashMap<>();
            char highestCountCard = 0;
            int mostNumberCards = 0;
            for (int i = 0; i < cards.length(); i++) {
                charCount.putIfAbsent(cards.charAt(i), 0);
                int numOfCards = charCount.get(cards.charAt(i)) + 1;
                charCount.put(cards.charAt(i), numOfCards);
                if (numOfCards > mostNumberCards && cards.charAt(i) != 'J') {
                    mostNumberCards = numOfCards;
                    highestCountCard = cards.charAt(i);
                }
            }
            if (charCount.containsKey('J') && highestCountCard > 0) {
                // swap joker with other highest card
                int jokerCount = charCount.get('J');
                charCount.remove('J');
                charCount.put(highestCountCard, charCount.get(highestCountCard) + jokerCount);
            }
            switch (charCount.size()) {
                case 1:
                    handType = HandType.FIVE_OF_A_KIND; // all cards of the same kind
                    break;
                case 2: // either 4 of a kind or full house
                    if (charCount.values().stream().anyMatch(v -> v.intValue() == 4)) {
                        handType = HandType.FOUR_OF_A_KIND;
                    } else {
                        handType = HandType.FULL_HOUSE;
                    }
                    break;
                case 3: // 2 pair or 3 of a kind
                    if (charCount.values().stream().anyMatch(v -> v.intValue() == 3)) {
                        handType = HandType.THREE_OF_A_KIND;
                    } else {
                        handType = HandType.TWO_PAIR;
                    }
                    break;
                case 4:
                    handType = HandType.SINGLE_PAIR;
                    break;
                default:
                    handType = HandType.HIGH_CARD;
            }
        }


        @Override
        public int compareTo(Object o) {
            Hand other = (Hand) o;
            if (!handType.equals(other.handType)) {
                return handType.rank.compareTo(other.handType.rank);
            }
            for (int i = 0; i < cards.length(); i++) {
                if (cards.charAt(i) != other.cards.charAt(i))
                    return charRank.get(cards.charAt(i)).compareTo(charRank.get(other.cards.charAt(i)));
            }
            return 0; // equal
        }
    }

    public enum HandType {
        FIVE_OF_A_KIND(6), FOUR_OF_A_KIND(5), FULL_HOUSE(4), THREE_OF_A_KIND(3), TWO_PAIR(2), SINGLE_PAIR(1), HIGH_CARD(0);

        private Integer rank;

        HandType(Integer rank) {
            this.rank = rank;
        }
    }
}
