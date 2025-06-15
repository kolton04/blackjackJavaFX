package com.koltont.blackjack;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;

public class Game {
    private Deck deck;
    public Player player;
    public Dealer dealer;
    private double bet;


    public Game(){
        this.player = new Player();
        this.dealer = new Dealer();
    }

    public void deal() {
        deck = new Deck();

        player.hand.clearHand();
        dealer.hand.clearHand();

        player.hit(deck);
        dealer.hit(deck);
        player.hit(deck);
        dealer.hit(deck);

    }

    public void hit(){
        player.hit(deck);
    }

    public void stand(){
        while(dealer.hand.getHandValue() < 17){
            dealer.hit(deck);
        }
    }
    public void reset(){
        player.hand.clearHand();
        dealer.hand.clearHand();
    }
}