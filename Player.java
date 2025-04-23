package com.example.blackjackJavaFX;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public Hand hand;

    public Player(){
        hand = new Hand();
    }

    public void hit(Deck deck){
        hand.addCard(deck.draw());
    }




}
