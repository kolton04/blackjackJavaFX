package com.example.blackjackJavaFX;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> hand;
    public Hand(){
        hand = new ArrayList<>();
    }

    public void addCard(Card card){
        hand.add(card);
    }
    public int getHandValue(){
        int value = 0;
        int aces = 0;

        for (Card card : hand) {
            if(card.getRank().equals("ace")){
                value += 11;
                aces++;
            }
            else if(card.getRank().equals("king") || card.getRank().equals("queen") || card.getRank().equals("jack")) {
                value += 10;
            }
            else {
                value += Integer.parseInt(card.getRank());
            }
        }

        //handles aces
        while(value > 21 && aces > 0){
            value -= 10;
            aces--;
        }

        return value;
    }

    public ArrayList<String> getCardPaths(){
        ArrayList<String> cardImages = new ArrayList<>();



        for(int i = 0; i < hand.size(); i++){
            cardImages.add(i, hand.get(i).getCardURL());
        }
        return cardImages;
    }

    /*
    public ImageView getCardImage(int k){
        ArrayList<ImageView> cardImages = new ArrayList<>();

        for(int i = 0; i < hand.size(); i++){
            cardImages.add(i, hand.get(i).getCardURL());
        }
        return cardImages.get(k);
    }

     */

    public void clearHand(){
        hand.clear();
    }

    public int getHandSize(){
        return hand.size();
    }

    public List<Card> getHand() {
        return hand;
    }

}
