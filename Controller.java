package com.example.blackjackJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

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
    TilePane playerHandImages;
    @FXML
    TilePane dealerHandImages;
    @FXML
    Label dealerHandValueLabel = new Label("Dealer's Hand: ");
    @FXML
    Label playerHandValueLabel = new Label("Your Hand: ");
    @FXML
    Label outcomeLabel = new Label();


    @FXML
    public void dealButton(ActionEvent e) {
        game.deal();

        dealButton.setDisable(true);
        hitButton.setDisable(false);
        standButton.setDisable(false);
        resetButton.setDisable(false);

        for(int i = 0; i < game.player.hand.getCardImages().size(); i++){
            playerHandImages.getChildren().add(game.player.hand.getCardImages().get(i));
            dealerHandImages.getChildren().add(game.dealer.hand.getCardImages().get(i));
        }

        //Makes second dealer card face down and removes second card image.
        ImageView cardBack = new ImageView(new Image(getClass().getResource("/card_back/cardback_red.png").toExternalForm()));
        cardBack.setFitWidth(150);
        cardBack.setFitHeight(200);
        dealerHandImages.getChildren().add(1, cardBack);
        dealerHandImages.getChildren().remove(2);

        playerHandValueLabel.setText("Your Hand: " + game.player.hand.getHandValue());
        dealerHandValueLabel.setText("Dealer's Hand: " + game.dealer.getTempDealerValue());
    }

    @FXML
    public void hitButton(ActionEvent e) {
        game.hit();
        playerHandImages.getChildren().add(game.player.hand.getCardImages().get(game.player.hand.getHandSize() - 1));
        playerHandValueLabel.setText("Your Hand: " + game.player.hand.getHandValue());

        if(game.player.hand.getHandValue() > 21){
            outcomeLabel.setText("Player Bust. Dealer Wins!");
            hitButton.setDisable(true);
            standButton.setDisable(true);
        }
    }

    @FXML
    public void standButton(ActionEvent e) {
        //Removes face down card and makes it right card image.
        dealerHandImages.getChildren().set(1, game.dealer.hand.getCardImage(1));

        hitButton.setDisable(true);
        standButton.setDisable(true);
        game.stand();
        //Adds card images that dealer hit.
        for (int i = 2; i < game.dealer.hand.getCardImages().size(); i++) {
            dealerHandImages.getChildren().add(game.dealer.hand.getCardImages().get(i));
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

    @FXML
    public void resetButton(ActionEvent e) {
        game.reset();

        dealButton.setDisable(false);
        hitButton.setDisable(true);
        standButton.setDisable(true);
        resetButton.setDisable(false);

        dealerHandValueLabel.setText("Dealer's Hand: ");
        playerHandValueLabel.setText("Your Hand: ");

        dealerHandImages.getChildren().clear();
        playerHandImages.getChildren().clear();
        outcomeLabel.setText("");
    }
}