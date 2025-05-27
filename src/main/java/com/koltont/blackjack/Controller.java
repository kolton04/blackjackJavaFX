package com.koltont.blackjack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    Game game = new Game();
    @FXML
    Button dealButton;
    @FXML
    Button hitButton;
    @FXML
    Button standButton;
    @FXML
    Button resetButton;
    @FXML
    Pane playerHand;
    @FXML
    Pane dealerHand;
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
    Label outcomeLabel = new Label();
    Image cardback = new Image(getClass().getResourceAsStream("/cardback/cardback-black.png"));
    ImageView deckView = new ImageView(cardback);
    ImageView hiddenDealerView = new ImageView(cardback);

    @FXML
    public void initialize() {
        deckView.setFitHeight(200);
        deckView.setFitWidth(150);
        deck.getChildren().add(deckView);
    }

    @FXML
    public void dealButton(ActionEvent e) throws IOException {
        game.deal();

        dealButton.setDisable(true);
        hitButton.setDisable(false);
        standButton.setDisable(false);
        playerLabel.setVisible(true);
        dealerLabel.setVisible(true);
        playerValue.setVisible(true);
        dealerValue.setVisible(true);

        playerHand.getChildren().addAll(getHand(game.player.hand.getCardPaths()));
        dealerHand.getChildren().addAll(getHand(game.dealer.hand.getCardPaths()));
        hiddenDealerView.setFitHeight(200);
        hiddenDealerView.setFitWidth(150);
        dealerHand.getChildren().add(1, hiddenDealerView);

        playerValue.setText(String.valueOf(game.player.hand.getHandValue()));
        dealerValue.setText(String.valueOf(game.dealer.tempDealer()));

    }

    @FXML
    public void hitButton(ActionEvent e) throws IOException {
        game.hit();

        playerHand.getChildren().addAll(getHand(game.player.hand.getCardPaths()));
        playerValue.setText(String.valueOf(game.player.hand.getHandValue()));

        if(game.player.hand.getHandValue() > 21){
            outcomeLabel.setText("Player Bust. Dealer Wins!");
            dealButton.setDisable(true);
            hitButton.setDisable(true);
            standButton.setDisable(true);
        }
    }

    @FXML
    public void standButton(ActionEvent e) throws IOException {
        //Removes face down card and makes it right card image.
        //dealerHandImages.getChildren().set(1, game.dealer.hand.getCardImage(1));

        dealButton.setDisable(true);
        hitButton.setDisable(true);
        standButton.setDisable(true);
        game.stand();
        dealerHand.getChildren().addAll(getHand(game.dealer.hand.getCardPaths()));
        dealerValue.setText(String.valueOf(game.dealer.hand.getHandValue()));



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

    @FXML
    public void resetButton(ActionEvent e) {
        game.reset();

        dealButton.setDisable(false);
        dealButton.setVisible(true);
        hitButton.setDisable(true);
        standButton.setDisable(true);
        resetButton.setDisable(false);

        playerLabel.setVisible(false);
        dealerLabel.setVisible(false);
        playerValue.setVisible(false);
        dealerValue.setVisible(false);


        dealerHand.getChildren().clear();
        playerHand.getChildren().clear();
        dealerValue.setText("");
        playerValue.setText("");
        outcomeLabel.setText("");
    }

    private ArrayList<ImageView> getHand(ArrayList<String> imgPath) throws IOException {
        ArrayList<ImageView> imgViews = new ArrayList<>();

        for(int i = 0; i < imgPath.size(); i++){
            ImageView imgV = new ImageView(new Image(imgPath.get(i)));
            imgV.setFitWidth(150);
            imgV.setFitHeight(200);
            imgV.setLayoutX(i * 30);

            imgViews.add(imgV);
        }

        return imgViews;
    }
}