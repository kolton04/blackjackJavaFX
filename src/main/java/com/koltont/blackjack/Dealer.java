package com.koltont.blackjack;

public class Dealer {

    public Hand hand;

    public Dealer(){
        hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public int tempDealerValue(){
        int value = 0;

        if(hand.getHand().get(1).getRank().equals("ace")){
            value += 11;
        }
        else if(hand.getHand().get(1).getRank().equals("king") || hand.getHand().get(1).getRank().equals("queen") || hand.getHand().get(1).getRank().equals("jack")) {
            value += 10;
        }
        else {
            value += Integer.parseInt(hand.getHand().get(1).getRank());
        }
        return value;
    }

    public void hit(Deck deck){
        hand.addCard(deck.draw());
    }
}
