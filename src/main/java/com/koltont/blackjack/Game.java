package com.koltont.blackjack;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;

public class Game {
    private Deck deck;
    public Player player;
    public Dealer dealer;
    private int wins;
    private int losses;
    private int ties;

    public Game(){
        this.player = new Player();
        this.dealer = new Dealer();
        this.wins = 0;
        this.losses = 0;
        this.ties = 0;
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

    public String handleRound(){
        int playerValue = player.hand.getHandValue();
        int dealerValue = dealer.hand.getHandValue();

        if(playerValue > 21){
            player.loseBet();
            losses++;
            return "Player Bust. Dealer Wins";
        }
        else if(dealerValue > 21 || playerValue > dealerValue){
            player.winBet();
            wins++;
            return "Player Wins";
        }
        else if(playerValue == dealerValue){
            player.pushBet();
            ties++;
            return "Tie";
        }
        else {
            player.loseBet();
            losses++;
            return "Dealer Wins";
        }
    }
}