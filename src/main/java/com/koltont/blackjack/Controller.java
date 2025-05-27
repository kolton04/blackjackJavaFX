package com.koltont.blackjack;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

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
    Label dealerHandValueLabel = new Label("Dealer's Hand: ");
    @FXML
    Label playerHandValueLabel = new Label("Your Hand: ");
    @FXML
    Label outcomeLabel = new Label();
    Image cardback = new Image(getClass().getResourceAsStream("/cardback/cardback-black.png"));
    ImageView cardbackView = new ImageView(cardback);

    @FXML
    public void initialize() {
        cardbackView.setFitHeight(200);
        cardbackView.setFitWidth(150);
        deck.getChildren().add(cardbackView);
    }

    @FXML
    public void dealButton(ActionEvent e) throws IOException {
        game.deal();

        hitButton.setVisible(true);
        hitButton.setDisable(false);
        standButton.setVisible(true);
        standButton.setDisable(false);

        playerHand.getChildren().addAll(getHand(game.player.hand.getCardPaths()));
        dealerHand.getChildren().addAll(getHand(game.dealer.hand.getCardPaths()));
    }

    @FXML
    public void hitButton(ActionEvent e) throws IOException {
        game.hit();

        playerHand.getChildren().addAll(getHand(game.player.hand.getCardPaths()));



        playerHandValueLabel.setText("Your Hand: " + game.player.hand.getHandValue());

        if(game.player.hand.getHandValue() > 21){
            outcomeLabel.setText("Player Bust. Dealer Wins!");
            hitButton.setDisable(true);
            standButton.setDisable(true);
        }
    }

    @FXML
    public void standButton(ActionEvent e) throws IOException {
        //Removes face down card and makes it right card image.
        //dealerHandImages.getChildren().set(1, game.dealer.hand.getCardImage(1));

        hitButton.setDisable(true);
        standButton.setDisable(true);
        game.stand();
        dealerHand.getChildren().addAll(getHand(game.dealer.hand.getCardPaths()));

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

    @FXML
    public void resetButton(ActionEvent e) {
        game.reset();

        dealButton.setDisable(false);
        dealButton.setVisible(true);
        hitButton.setDisable(true);
        standButton.setDisable(true);
        resetButton.setDisable(false);

        dealerHandValueLabel.setText("Dealer's Hand: ");
        playerHandValueLabel.setText("Your Hand: ");

        dealerHand.getChildren().clear();
        playerHand.getChildren().clear();
        outcomeLabel.setText("");
    }

    private ArrayList<ImageView> getHand(ArrayList<String> imgPath) throws IOException {
        ArrayList<ImageView> imgViews = new ArrayList<>();

        for(int i = 0; i < imgPath.size(); i++){
            System.out.println(imgPath.get(i));
            ImageView imgV = new ImageView(new Image(imgPath.get(i)));
            System.out.println(getClass().getResourceAsStream(imgPath.get(i)));
            imgV.setFitWidth(150);
            imgV.setFitHeight(200);
            imgV.setLayoutX(i * 30);

            imgViews.add(imgV);
        }

        return imgViews;
    }
}