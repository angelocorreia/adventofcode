package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day4 {
    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/day4.txt")));
        String s = reader.readLine();
        List<Card> cards = new ArrayList<>();
        while (s != null) {

            String game = s.substring(5, s.indexOf(":"));
            Card card = new Card();
            card.number = Integer.valueOf(game.trim());
            String numSplit[] = s.substring(s.indexOf(":") + 1).split("\\|");
            for (int i = 0; i < numSplit.length; i++) {
                String numberStr = numSplit[i];
                String split[] = numberStr.split(" ");
                for (String num : split) {
                    if (!num.trim().isEmpty())
                        if (i == 0) {
                            card.winningNumbers.add(Integer.valueOf(num));
                        } else {
                            card.myNumbers.add(Integer.valueOf(num));
                        }
                }
            }
            cards.add(card);
            s = reader.readLine();
        }
        int sum = 0;
        int nonWinningCards = 0;
        Map<Integer, Integer> cardWins = new HashMap<>();
        for (Card card : cards) {
            card.myNumbers.retainAll(card.winningNumbers);
        }
        for (Card card : cards) {
            card.myNumbers.retainAll(card.winningNumbers);
            if (card.myNumbers.size() > 0) {

                if (cardWins.containsKey(card.number)) {
                    cardWins.put(card.number, cardWins.get(card.number) + 1);
                } else {
                    cardWins.put(card.number, 1);
                }
                int numCurrentCards =  cardWins.get(card.number);
                for (int i = 0; i < card.myNumbers.size(); i++) {
                    cardWins.putIfAbsent(card.number + 1 + i, 0);
                    int wins = cardWins.get(card.number + 1 + i);
                    cardWins.put(card.number + 1 + i, wins + numCurrentCards);
                }
            } else {
                nonWinningCards = nonWinningCards +1;
            }

        }
        for (int val : cardWins.values()) {
            sum = sum + val;
        }
        System.out.println(sum + nonWinningCards);

    }

    private static class Card {
        int number;
        Set<Integer> winningNumbers = new HashSet<>();
        Set<Integer> myNumbers = new HashSet<>();

    }
}
