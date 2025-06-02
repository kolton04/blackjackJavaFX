package com.koltont.blackjack;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
    private String rank;
    private String suit;
    private String cardURL;

    public Card(String rank, String suit){
        this.rank = rank;
        this.suit = suit;
        this.cardURL = "PNG-cards-1.3/" + rank + "_of_" + suit + ".png";
    }

    public String getRank(){
        return rank;
    }

    public String getSuit(){return suit;}

    public String getCardURL() {
        return cardURL;
    }

}
