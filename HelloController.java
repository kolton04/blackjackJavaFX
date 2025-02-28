package com.example.blackjackJavaFX;

import com.example.blackjackJavaFX.Deck;
import javafx.fxml.FXML;

import java.util.Collections;

public class HelloController {
    Deck d1 = new Deck();
    @FXML
    protected void onDeal() {
        //creates new deck to start fresh everytime
        d1 = new Deck();

        //shuffles the deck and resets dealer & player hands
        Collections.shuffle(d1.currentDeck);
        d1.playerHand.clear();
        d1.dealerHand.clear();

        //deals 1 card to player, then 1 to dealer until each hand has 2 cards
        for(int i = 0; i < 2; i++){
            d1.playerHand.add(i, d1.currentDeck.get(0));
            d1.currentDeck.remove(0);
            d1.dealerHand.add(i, d1.currentDeck.get(0));
            d1.currentDeck.remove(0);
        }

    }
}