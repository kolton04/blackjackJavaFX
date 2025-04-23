package com.example.blackjackJavaFX;

public class Dealer {

    public Hand hand;

    public Dealer(){
        hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public void hit(Deck deck){
        hand.addCard(deck.draw());
    }

}
