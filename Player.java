package com.example.blackjackJavaFX;

public class Player {

    public Hand hand;

    public Player(){
        hand = new Hand();
    }

    public void hit(Deck deck){
        hand.addCard(deck.draw());
    }

}
