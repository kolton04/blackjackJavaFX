package com.koltont.blackjack;

import static java.lang.Math.round;

public class Player {

    public Hand hand;
    public int chips;
    public int bet;


    public Player(){
        hand = new Hand();
        chips = 100;
        bet = 0;
    }

    public void hit(Deck deck){
        hand.addCard(deck.draw());
    }

    public int getChipAmt(){
        return chips;
    }

    public int getBetAmt(){
        return bet;
    }

    public void setBetAmt(int bet){
        this.bet = bet;
        this.chips -= bet;
    }

    public void winBet(){
        chips += bet * 2;
    }

    public void loseBet(){

    }

    public void pushBet(){
        chips += bet;
    }
}
