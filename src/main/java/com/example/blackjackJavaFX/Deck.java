package com.example.blackjackJavaFX;

import java.util.*;

public class Deck {
    private String suit[] = {"diamonds", "clubs", "spades", "hearts"};
    private String rank[] = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    public List<Card> cards = new ArrayList<>(52);

    //creates shuffled deck of cards
    public Deck(){
        int ctr = 0;
        for(int i = 0; i <= rank.length - 1; i++){
            for(int j = 0; j <= suit.length - 1; j++){
                cards.add(ctr, new Card(rank[i], suit[j]));
                ctr++;
            }
        }
        Collections.shuffle(cards);
    }

    public Card draw(){
        return cards.remove(0);
    }
}
