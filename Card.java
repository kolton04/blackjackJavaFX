package com.example.blackjackJavaFX;

import javafx.scene.image.Image;

public class Card {
    public String rank;
    public String suit;
    public Image cardImage;


    public Card(String rank, String suit){
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank(){
        return rank;
    }

    public String getSuit(){
        return suit;
    }

    public String toString(){
        return (this.rank + "_of_" + this.suit + ".png");
    }

    public Image getCardImage(){
        return cardImage;
    }

}
