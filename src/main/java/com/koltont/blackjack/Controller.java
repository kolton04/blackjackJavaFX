package com.koltont.blackjack;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
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
    @FXML
    JFXSlider betSlider;
    @FXML
    Label betValidation;
    @FXML
    Label betAmt;
    @FXML
    Label payoutLabel;
    @FXML
    Label chipsLabel;

    Image cardback = new Image(getClass().getResourceAsStream("/cardback/cardback_black.png"));
    ImageView deckView = new ImageView(cardback);
    ImageView hiddenDealerView = new ImageView(cardback);



    @FXML
    public void initialize() {
        deckView.setFitHeight(300);
        deckView.setFitWidth(225);
        deck.getChildren().add(deckView);

        hitButton.setEffect(new GaussianBlur(10));
        standButton.setEffect(new GaussianBlur(10));

        chipsLabel.setText("CHIPS: " + game.player.getChipAmt());

        //Updates bet amount label in real time with slider
        betSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int bet = (int) Math.round(newValue.doubleValue());
            betAmt.setText("BET AMOUNT: " + bet);
            payoutLabel.setText("PAYOUT: " + (int)(bet * 1.5));
        });

    }

    @FXML
    public void dealButton(ActionEvent e) throws IOException {
        if(betSlider.getValue() == 0){
            betValidation.setText("Bet amount cannot be 0");
        }
        else if(betSlider.getValue() > game.player.getChipAmt()){
            betValidation.setText("You can't bet more than you have");
        }
        else{
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

            betValidation.setText("");

            PauseTransition dealHandPause = new PauseTransition(Duration.millis(350));
            dealHandPause.setOnFinished(actionEvent -> {
                try {
                    game.player.setBetAmt((int)betSlider.getValue());

                    game.deal();

                    betAmt.setText("BET AMOUNT: " + game.player.getBetAmt());
                    chipsLabel.setText("CHIPS: " + game.player.getChipAmt());

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

                    betSlider.setDisable(true);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            dealHandPause.play();
        }

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
        PauseTransition outcomePause = new PauseTransition(Duration.millis(400));
        outcomePause.setOnFinished(actionEvent -> {
            String result = game.handleRound();
            outcomeLabel.setText(result);

            chipsLabel.setText("CHIPS: " + game.player.getChipAmt());
            outcomeHBox.setVisible(true);
            playArea.setEffect(new GaussianBlur(5));

            dealButton.setDisable(false);
            dealButton.setEffect(null);
            betSlider.setDisable(false);
            betSlider.setMax(game.player.chips);

        });
        outcomePause.play();
    }
}