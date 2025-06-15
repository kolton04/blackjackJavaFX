package com.koltont.blackjack;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;

public class Player {

    public Hand hand;
    public double chips;
    public double bet;


    public Player(){
        hand = new Hand();
        chips = 100;
        bet = 0;
    }

    public void hit(Deck deck){
        hand.addCard(deck.draw());
    }




}
