package com.example.blackjackJavaFX;

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


public class Main extends Application {
    Game game = new Game();

    @Override
    public void start(Stage stage) throws IOException {

        Button dealButton = new Button("Deal");
        Button hitButton = new Button("Hit");
        Button standButton = new Button("Stand");
        Button resetButton = new Button("Reset");

        hitButton.setDisable(true);
        standButton.setDisable(true);
        resetButton.setDisable(true);

        Label dealerHandValueLabel = new Label("Dealer's Hand: ");
        Label playerHandValueLabel = new Label("Your Hand: ");
        Label outcomeLabel = new Label();

        TilePane playerHandTP = new TilePane();
        TilePane dealerHandTP = new TilePane();

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
                game.deal();

                dealButton.setDisable(true);
                hitButton.setDisable(false);
                standButton.setDisable(false);
                resetButton.setDisable(false);

                for(int i = 0; i < game.player.hand.getCardImages().size(); i++){
                    playerHandTP.getChildren().add(game.player.hand.getCardImages().get(i));
                    dealerHandTP.getChildren().add(game.dealer.hand.getCardImages().get(i));
                }

                //Makes second dealer card face down and removes second card image.
                ImageView cardBack = new ImageView(new Image(getClass().getResource("/card_back/cardback_red.png").toExternalForm()));
                cardBack.setFitWidth(150);
                cardBack.setFitHeight(200);
                dealerHandTP.getChildren().add(1, cardBack);
                dealerHandTP.getChildren().remove(2);

                playerHandValueLabel.setText("Your Hand: " + game.player.hand.getHandValue());
            }
        });

        hitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.hit();
                playerHandTP.getChildren().add(game.player.hand.getCardImages().get(game.player.hand.getHandSize() - 1));
                playerHandValueLabel.setText("Your Hand: " + game.player.hand.getHandValue());

                if(game.player.hand.getHandValue() > 21){
                    outcomeLabel.setText("Player Bust. Dealer Wins!");
                }
            }
        });


        standButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Removes face down card and makes it right card image.
                dealerHandTP.getChildren().set(1, game.dealer.hand.getCardImage(1));

                hitButton.setDisable(true);
                standButton.setDisable(true);
                game.stand();
                //Adds card images that dealer hit.
                for (int i = 2; i < game.dealer.hand.getCardImages().size(); i++) {
                    dealerHandTP.getChildren().add(game.dealer.hand.getCardImages().get(i));
                }
                dealerHandValueLabel.setText("Dealer's Hand: " + game.dealer.hand.getHandValue());

                if(game.dealer.hand.getHandValue() > 21){
                    outcomeLabel.setText("Dealer Bust. You Win!");
                }
                else if(game.player.hand.getHandValue() > game.dealer.hand.getHandValue()){
                    outcomeLabel.setText("You Win!");
                }
                else if(game.player.hand.getHandValue() == game.dealer.hand.getHandValue()){
                    outcomeLabel.setText("Tie!");

                }
                else{
                    outcomeLabel.setText("Dealer Wins!");

                }

            }
        });

       resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.reset();

                dealButton.setDisable(false);
                hitButton.setDisable(true);
                standButton.setDisable(true);
                resetButton.setDisable(false);

                dealerHandValueLabel.setText("Dealer's Hand: ");
                playerHandValueLabel.setText("Your Hand: ");

                dealerHandTP.getChildren().clear();
                playerHandTP.getChildren().clear();
                outcomeLabel.setText("");
            }
        });


        VBox dealerCardsVBox = new VBox(dealerHandValueLabel, dealerHandTP);
        VBox playerCardsVBox = new VBox(playerHandValueLabel, playerHandTP);
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