package com.example.blackjackJavaFX;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class Game {

    private Deck deck;
    public Player player;
    public Dealer dealer;


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


















