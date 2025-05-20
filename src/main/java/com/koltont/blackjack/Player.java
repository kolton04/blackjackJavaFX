package com.koltont.blackjack;

public class Player {

    public Hand hand;

    public Player(){
        hand = new Hand();
    }

    public void hit(Deck deck){
        hand.addCard(deck.draw());
    }

}
