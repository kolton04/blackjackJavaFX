package com.koltont.blackjack;

import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    AnchorPane playArea;
    @FXML
    AnchorPane dealerAP;
    @FXML
    AnchorPane playerAP;
    @FXML
    JFXButton dealButton;
    @FXML
    JFXButton hitButton;
    @FXML
    JFXButton standButton;
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



    @FXML
    public void initialize() {
        deckView.setFitHeight(300);
        deckView.setFitWidth(225);
        deck.getChildren().add(deckView);

        hitButton.setEffect(new GaussianBlur(10));
        standButton.setEffect(new GaussianBlur(10));

    }

    @FXML
    public void dealButton(ActionEvent e) throws IOException {
        game.reset();

        outcomeHBox.setVisible(false);
        outcomeLabel.setText("");
        playArea.setEffect(null);

        dealerHand.getChildren().clear();
        playerHand.getChildren().clear();
        dealerAP.setVisible(false);
        playerAP.setVisible(false);

        dealButton.setDisable(true);
        dealButton.setEffect(new GaussianBlur(10));

        hitButton.setDisable(true);
        hitButton.setEffect(new GaussianBlur(10));

        standButton.setDisable(true);
        standButton.setEffect(new GaussianBlur(10));


        PauseTransition dealHandPause = new PauseTransition(Duration.millis(500));
        dealHandPause.setOnFinished(actionEvent -> {
            try {
                game.deal();

                // Enables player and dealer AnchorPanes with hand labels.
                dealerAP.setVisible(true);
                playerAP.setVisible(true);

                // Loops through hand card paths and adds image to respective hand. Sets hand value labels
                playerHand.getChildren().addAll(getHand(game.player.hand.getCardPaths()));
                playerValue.setText(String.valueOf(game.player.hand.getHandValue()));

                dealerHand.getChildren().addAll(getHand(game.dealer.hand.getCardPaths()));
                dealerValue.setText(String.valueOf(game.dealer.tempDealerValue()));

                // Hides second dealer card
                hiddenDealerView.setFitHeight(300);
                hiddenDealerView.setFitWidth(225);

                dealerHand.getChildren().add(1, hiddenDealerView);

                hitButton.setDisable(false);
                hitButton.setEffect(null);

                standButton.setDisable(false);
                standButton.setEffect(null);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        dealHandPause.play();

    }

    @FXML
    public void hitButton(ActionEvent e) throws IOException {
        game.hit();

        PauseTransition hitPause = new PauseTransition(Duration.millis(250));
        hitPause.setOnFinished(actionEvent -> {
            try {
                playerHand.getChildren().addAll(getHand(game.player.hand.getCardPaths()));
                playerValue.setText(String.valueOf(game.player.hand.getHandValue()));
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        } );
        hitPause.play();

        if(game.player.hand.getHandValue() > 21) {
            handleOutcome();
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
        dealerValue.setText(String.valueOf(game.dealer.hand.getHandValue()));


        PauseTransition standPause = new PauseTransition(Duration.millis(450));
        standPause.setOnFinished(actionEvent -> {
            try {
                dealerHand.getChildren().addAll(getHand(game.dealer.hand.getCardPaths()));
                dealerValue.setText(String.valueOf(game.dealer.hand.getHandValue()));
                handleOutcome();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        standPause.play();

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

    private void handleOutcome(){
        int playerValue = game.player.hand.getHandValue();
        int dealerValue = game.dealer.hand.getHandValue();

        PauseTransition outcomePause = new PauseTransition(Duration.seconds(1));
        outcomePause.setOnFinished(actionEvent -> {
            if(playerValue > 21){
                outcomeLabel.setText("Player Bust. Dealer wins!");

                dealButton.setDisable(true);
                hitButton.setDisable(true);
                standButton.setDisable(true);
            }
            else if(playerValue > dealerValue){
                outcomeLabel.setText("You Win!");
            }

            if(dealerValue > 21){
                outcomeLabel.setText("Dealer Bust. You Win!");
            }
            else if(playerValue == dealerValue){
                outcomeLabel.setText("Tie!");
            }

            if(dealerValue > playerValue){
                outcomeLabel.setText("Dealer Wins!");
            }

            outcomeHBox.setVisible(true);
            playArea.setEffect(new GaussianBlur(5));
            dealButton.setDisable(false);
            dealButton.setEffect(null);
        });
        outcomePause.play();
    }
}