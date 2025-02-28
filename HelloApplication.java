package com.example.blackjackJavaFX;

import com.example.blackjackJavaFX.Deck;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  TO DO
 *
 *  -rename tilepane hands an

 */
public class HelloApplication extends Application {
    public Deck d1 = new Deck();
    public int i = 2; //counter for adding card images to players hand
    public int j = 2; //counter for adding card images to dealers hand
    public int playerHandValue = 0;
    public int dealerHandValue = 0;

    @Override
    public void start(Stage stage) throws IOException {
        String url = "/dev/projects/java/blackjack/src/main/resources/PNG-cards-1.3/PNG-cards-1.3/";
        System.out.println(url);

        TilePane playerHand = new TilePane();
        TilePane dealerHand = new TilePane();

        List<ImageView> playerCardImages = new ArrayList<>();
        List<ImageView> dealerCardImages = new ArrayList<>();

        Label dealerHandValueLabel = new Label("Dealer's Hand: ");
        Label playerHandValueLabel = new Label("Your Hand: ");
        Label outcomeLabel = new Label();

        Button dealButton = new Button("Deal");
        Button hitButton = new Button("Hit");
        Button standButton = new Button("Stand");
        Button resetButton = new Button("Reset");

        dealerHandValueLabel.setStyle("-fx-font-weight: bold");
        dealerHandValueLabel.setFont(new Font(30));
        playerHandValueLabel.setStyle("-fx-font-weight: bold");
        playerHandValueLabel.setFont(new Font(30));
        outcomeLabel.setStyle("-fx-font-weight: bold");
        outcomeLabel.setFont(new Font(50));
        hitButton.setDisable(true);
        standButton.setDisable(true);

        dealButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                d1 = new Deck();

                Collections.shuffle(d1.currentDeck);
                d1.playerHand.clear();
                d1.dealerHand.clear();

                //deals 1 card to player, then 1 to dealer until each hand has 2 cards
                for(int i = 0; i < 2; i++){
                    d1.playerHand.add(i, d1.currentDeck.get(0));
                    playerCardImages.add(i, new ImageView(new Image(url + d1.currentDeck.get(0))));
                    playerCardImages.get(i).setFitWidth(150);
                    playerCardImages.get(i).setFitHeight(200);
                    playerHand.getChildren().add(playerCardImages.get(i));
                    d1.currentDeck.remove(0);

                    d1.dealerHand.add(i, d1.currentDeck.get(0));
                    dealerCardImages.add(i, new ImageView(new Image(url + d1.currentDeck.get(0))));
                    dealerCardImages.get(i).setFitWidth(150);
                    dealerCardImages.get(i).setFitHeight(200);
                    dealerHand.getChildren().add(dealerCardImages.get(i));
                    d1.currentDeck.remove(0);
                }

                //makes first dealer card face down
                dealerCardImages.set(0, new ImageView(new Image("PNG-cards-1.3/PNG-cards-1.3/cardback_red.png")));
                dealerCardImages.get(0).setFitWidth(150);
                dealerCardImages.get(0).setFitHeight(200);
                dealerHand.getChildren().set(0, dealerCardImages.get(0));

                playerHandValue = d1.getPlayerHandValue();
                dealerHandValue = d1.getDealerHandValue();
                dealerHandValueLabel.setText("Dealer's Hand: " + d1.getOneDealerCardValue(1));
                playerHandValueLabel.setText("Your Hand: " + playerHandValue);
                dealButton.setDisable(true);
                hitButton.setDisable(false);
                standButton.setDisable(false);

            }
        });

        hitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(playerHandValue < 21) {
                    d1.playerHand.add(0, d1.currentDeck.get(0));
                    d1.currentDeck.remove(0);

                    playerCardImages.add(i, new ImageView(new Image(url + d1.getPlayerCard(0))));
                    playerCardImages.get(i).setFitWidth(150);
                    playerCardImages.get(i).setFitHeight(200);
                    playerHand.getChildren().add(i, playerCardImages.get(i));
                    i++;

                    playerHandValue = d1.getPlayerHandValue();
                }

                if(playerHandValue > 21){
                    if(d1.getAllPlayerCards().contains("ace")){
                        playerHandValue -= 10;
                        playerHandValueLabel.setText("Your Cards: " + playerHandValue);
                        if(playerHandValue > 21){
                            hitButton.setDisable(true);
                            standButton.setDisable(true);
                            outcomeLabel.setText("Player Bust! You lose");
                        }
                    }
                    else{
                        hitButton.setDisable(true);
                        standButton.setDisable(true);
                        outcomeLabel.setText("Player Bust! You lose");
                    }
                }

                if(playerHandValue == 21){
                    hitButton.setDisable(true);
                }

                playerHandValueLabel.setText("Your Hand: " + playerHandValue);
                }
        });


        standButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dealerCardImages.set(0, new ImageView(new Image(url + d1.getDealerCard(0))));
                dealerCardImages.get(0).setFitWidth(150);
                dealerCardImages.get(0).setFitHeight(200);
                dealerHand.getChildren().set(0, dealerCardImages.get(0));

                hitButton.setDisable(true);
                standButton.setDisable(true);
                dealerHandValue = d1.getDealerHandValue();

                while(dealerHandValue < 17){
                    d1.dealerHand.add(0, d1.currentDeck.get(0));
                    d1.currentDeck.remove(0);

                    dealerCardImages.add(j, new ImageView(new Image(url + d1.getDealerCard(0))));
                    dealerCardImages.get(j).setFitWidth(150);
                    dealerCardImages.get(j).setFitHeight(200);
                    dealerHand.getChildren().add(j, dealerCardImages.get(j));
                    j++;

                    dealerHandValue = d1.getDealerHandValue();
                    dealerHandValueLabel.setText("Dealer's Hand: " + dealerHandValue);

                    if(dealerHandValue > 21){
                        if(d1.getAllDealerCards().contains("ace")){
                            dealerHandValue -= 10;
                            dealerHandValueLabel.setText("Dealer's Hand: " + dealerHandValue);
                            if(dealerHandValue > 21){
                                outcomeLabel.setText("Dealer Bust! You Win!");
                            }
                        }
                    }

                }

                dealerHandValueLabel.setText("Dealer's Hand: " + dealerHandValue);

                if(dealerHandValue <= 21 && playerHandValue <= 21){
                    if(dealerHandValue > playerHandValue){
                        outcomeLabel.setText("Dealer Wins");
                    }

                    if(playerHandValue > dealerHandValue){
                        outcomeLabel.setText("Player Wins");
                    }

                    if(playerHandValue == dealerHandValue){
                        outcomeLabel.setText("Tie");
                    }
                }

                if(dealerHandValue > 21){
                    outcomeLabel.setText("Dealer Bust! You Win!");

                }

            }
        });

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                playerHand.getChildren().clear();
                dealerHand.getChildren().clear();
                playerCardImages.clear();
                dealerCardImages.clear();
                playerHandValue = 0;
                dealerHandValue = 0;
                dealButton.setDisable(false);
                hitButton.setDisable(true);
                standButton.setDisable(true);
                outcomeLabel.setText("");
                dealerHandValueLabel.setText("");
                playerHandValueLabel.setText("");
                i = 2;
                j = 2;

            }
        });

        VBox dealerCardsVBox = new VBox(dealerHandValueLabel, dealerHand);
        VBox playerCardsVBox = new VBox(playerHandValueLabel, playerHand);
        HBox buttons = new HBox(dealButton, hitButton, standButton, resetButton);
        HBox outcome = new HBox(outcomeLabel);

        dealerCardsVBox.setPrefSize(150, 250);
        playerCardsVBox.setPrefSize(150, 250);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        outcome.setAlignment(Pos.BOTTOM_CENTER);

        VBox vBox = new VBox(dealerCardsVBox, playerCardsVBox, buttons, outcome);
        vBox.setSpacing(15);
        Background background = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
        vBox.setBackground(background);
        Scene scene = new Scene(vBox, 750, 1000);
        scene.setFill(Color.GREEN);
        stage.setTitle("Blackjack");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();

    }
}