package com.koltont.blackjack;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    Game game = new Game();

    @FXML
    JFXButton dealButton;
    @FXML
    JFXButton hitButton;
    @FXML
    JFXButton standButton;
    @FXML
    JFXButton resetButton;
    @FXML
    Pane playerHand;
    @FXML
    Pane dealerHand;
    @FXML
    StackPane playerSP;
    @FXML
    StackPane dealerSP;
    @FXML
    Pane deck;
    @FXML
    Label dealerLabel;
    @FXML
    Label playerLabel;
    @FXML
    Label dealerValue;
    @FXML
    Label playerValue;
    @FXML
    HBox outcomeHBox;
    @FXML
    Label outcomeLabel = new Label();
    Image cardback = new Image(getClass().getResourceAsStream("/cardback/cardback-black.png"));
    ImageView deckView = new ImageView(cardback);
    ImageView hiddenDealerView = new ImageView(cardback);
    ImageView dealtCard = new ImageView(cardback);



    @FXML
    public void initialize() {
        dealtCard.setFitHeight(300);
        dealtCard.setFitWidth(225);
        deckView.setFitHeight(300);
        deckView.setFitWidth(225);
        deck.getChildren().add(deckView);
        deck.getChildren().add(dealtCard);

        hitButton.setEffect(new GaussianBlur(10));
        standButton.setEffect(new GaussianBlur(10));

        ArrayList<ImageView> dealtCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ImageView cardBack = new ImageView(cardback);
            cardBack.setFitWidth(225);
            cardBack.setFitHeight(300);
            cardBack.setLayoutX(deck.getLayoutX());
            cardBack.setLayoutY(deck.getLayoutY());
            deck.getChildren().add(cardBack);
            dealtCards.add(cardBack);
        }

    }

    @FXML
    public void dealButton(ActionEvent e) throws IOException {
        game.deal();

        dealtCard.setVisible(false);

        playerHand.setVisible(true);
        dealerHand.setVisible(true);
        playerLabel.setVisible(true);
        dealerLabel.setVisible(true);
        playerValue.setVisible(true);
        dealerValue.setVisible(true);

        dealtCard.setVisible(false);

        playerHand.setVisible(true);
        dealerHand.setVisible(true);
        playerLabel.setVisible(true);
        dealerLabel.setVisible(true);
        playerValue.setVisible(true);
        dealerValue.setVisible(true);

        playerHand.getChildren().addAll(getHand(game.player.hand.getCardPaths()));
        dealerHand.getChildren().addAll(getHand(game.dealer.hand.getCardPaths()));

        hiddenDealerView.setFitHeight(300);
        hiddenDealerView.setFitWidth(225);
        dealerHand.getChildren().add(1, hiddenDealerView);

        playerValue.setText(String.valueOf(game.player.hand.getHandValue()));
        dealerValue.setText(String.valueOf(game.dealer.tempDealerValue()));

        dealButton.setDisable(true);
        dealButton.setEffect(new GaussianBlur(10));
        hitButton.setDisable(false);
        hitButton.setEffect(null);
        standButton.setDisable(false);
        standButton.setEffect(null);


    }

    @FXML
    public void hitButton(ActionEvent e) throws IOException {
        game.hit();

        playerHand.getChildren().addAll(getHand(game.player.hand.getCardPaths()));
        playerValue.setText(String.valueOf(game.player.hand.getHandValue()));


        if(game.player.hand.getHandValue() > 21){
            outcomeLabel.setText("Player Bust. Dealer wins with " + game.dealer.hand.getHandValue());
            dealButton.setDisable(true);
            hitButton.setDisable(true);

            standButton.setDisable(true);
            outcomeHBox.setVisible(true);

        }
    }

    @FXML
    public void standButton(ActionEvent e) throws IOException {

        hitButton.setDisable(true);
        hitButton.setEffect(new GaussianBlur(10));
        standButton.setDisable(true);
        standButton.setEffect(new GaussianBlur(10));
        game.stand();
        dealerHand.getChildren().remove(1);
        dealerHand.getChildren().addAll(getHand(game.dealer.hand.getCardPaths()));
        dealerValue.setText(String.valueOf(game.dealer.hand.getHandValue()));



        if(game.dealer.hand.getHandValue() > 21){
            outcomeLabel.setText("Dealer Bust. You Win!");
            outcomeHBox.setVisible(true);

        }
        else if(game.player.hand.getHandValue() > game.dealer.hand.getHandValue()){
            outcomeLabel.setText("You Win!");
            outcomeHBox.setVisible(true);

        }
        else if(game.player.hand.getHandValue() == game.dealer.hand.getHandValue()){
            outcomeLabel.setText("Tie!");
            outcomeHBox.setVisible(true);


        }
        else{
            outcomeLabel.setText("Dealer Wins!");
            outcomeHBox.setVisible(true);


        }
    }

    @FXML
    public void resetButton(ActionEvent e) {
        game.reset();

        dealButton.setDisable(false);
        hitButton.setDisable(true);
        standButton.setDisable(true);
        resetButton.setDisable(false);

        dealButton.setEffect(null);

        playerLabel.setVisible(false);
        dealerLabel.setVisible(false);
        playerValue.setVisible(false);
        dealerValue.setVisible(false);


        dealerHand.getChildren().clear();
        playerHand.getChildren().clear();
        dealerValue.setText("");
        playerValue.setText("");
        outcomeLabel.setText("");
        outcomeHBox.setVisible(false);

    }

    private ArrayList<ImageView> getHand(ArrayList<String> imgPath) throws IOException {
        ArrayList<ImageView> imgViews = new ArrayList<>();

        for(int i = 0; i < imgPath.size(); i++){
            ImageView imgV = new ImageView(new Image(imgPath.get(i)));
            imgV.setFitWidth(225);
            imgV.setFitHeight(300);
            imgV.setLayoutX(i * 30);
            imgV.setLayoutY(i * 15);

            imgViews.add(imgV);
        }

        return imgViews;
    }


}