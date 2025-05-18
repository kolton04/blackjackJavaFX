package com.example.blackjackJavaFX;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
    private String rank;
    private String suit;
    private ImageView cardImage;

    public Card(String rank, String suit){
        String path = "/PNG-cards-1.3/cards/" + rank + "_of_" + suit + ".png";
        this.rank = rank;
        this.suit = suit;
        this.cardImage = new ImageView(new Image(getClass().getResource(path).toExternalForm()));
        cardImage.setFitWidth(150);
        cardImage.setFitHeight(200);
    }

    public String getRank(){
        return rank;
    }

    public String getSuit(){return suit;}

    public ImageView getCardImage() {
        return cardImage;
    }

}
