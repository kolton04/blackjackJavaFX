package com.example.blackjackJavaFX;
import com.example.blackjackJavaFX.Card;

import java.util.*;

public class Deck {
    private String suits[] = {"diamonds", "clubs", "spades", "hearts"};
    private String ranks[] = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    public Card[] deckCards = new Card[52];
    public List<Card> currentDeck = new ArrayList<>(Arrays.asList(deckCards));
    public List<Card> dealerHand = new ArrayList<>();
    public List<Card> playerHand = new ArrayList<>();

    //creates deck of unshuffled cards
    public Deck(){
        int ctr = 0;
        for(int i = 0; i <= ranks.length - 1; i++){
            for(int j = 0; j <= suits.length - 1; j++){
                deckCards[ctr] = new Card(ranks[i], suits[j]);
                currentDeck.set(ctr, deckCards[ctr]);
                ctr++;
            }
        }
    }

    public String toString(){
        return Arrays.toString(deckCards);
    }

    //returns value of players hand based on suit and rank
    public int getPlayerHandValue(){
        int playerValue = 0;

        for(int i = 0; i < playerHand.size(); i++){
            if(playerHand.get(i).getRank() == "jack" || playerHand.get(i).getRank() == "queen" || playerHand.get(i).getRank() == "king" ){
                playerValue += 10;
            }
            else if(playerHand.get(i).getRank() == "ace"){
                playerValue += 11;
            }
            else {
                playerValue += Integer.parseInt(playerHand.get(i).getRank());
            }
        }

        return playerValue;
    }

    //returns value of dealers hand based on suit and rank
    public int getDealerHandValue(){
        int dealerValue = 0;

        for(int i = 0; i < dealerHand.size(); i++){
            if(dealerHand.get(i).getRank() == "jack" || dealerHand.get(i).getRank() == "queen" || dealerHand.get(i).getRank() == "king" ){
                dealerValue += 10;
            }
            else if(dealerHand.get(i).getRank() == "ace"){
                dealerValue += 11;
            }
            else {
                dealerValue += Integer.parseInt(dealerHand.get(i).getRank());
            }
        }

        return dealerValue;
    }

    //returns value of one dealer card based on given index
    public int getOneDealerCardValue(int i){
        int dealerValue = 0;

        if(dealerHand.get(i).getRank() == "jack" || dealerHand.get(i).getRank() == "queen" || dealerHand.get(i).getRank() == "king" ){
            dealerValue += 10;
        }
        else if(dealerHand.get(i).getRank() == "ace"){

            dealerValue += 11;
        }
        else {
            dealerValue += Integer.parseInt(dealerHand.get(i).getRank());
        }

        return dealerValue;
    }

    //returns string of all cards in dealers hand
    public String getAllDealerCards(){
        return dealerHand.toString();
    }

    //returns string of all cards in players hand
    public String getAllPlayerCards(){
        return playerHand.toString();
    }

    //returns string of one dealer card based on given index
    public String getDealerCard(int i){
        return dealerHand.get(i).toString();
    }

    //returns string of one player card based on given index
    public String getPlayerCard(int i){
        return playerHand.get(i).toString();
    }


}
