package com.example.blackjackJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    Pane playerHand;
    @FXML
    Pane dealerHand;
    @FXML
    Label dealerHandValueLabel = new Label("Dealer's Hand: ");
    @FXML
    Label playerHandValueLabel = new Label("Your Hand: ");
    @FXML
    Label outcomeLabel = new Label();

    ImageView playerHandImages;

    ImageView dealerHandImages;

    @FXML
    public void dealButton(ActionEvent e) {
        game.deal();
        dealButton.setDisable(true);
        hitButton.setDisable(false);
        standButton.setDisable(false);

        for(int i = 0; i < 2; i++){
            playerHandImages = new ImageView(new Image(game.player.hand.getCardPaths().get(i)));
            dealerHandImages = new ImageView(new Image(game.dealer.hand.getCardPaths().get(i)));

            playerHandImages.setFitWidth(150);
            playerHandImages.setFitHeight(200);
            playerHandImages.setLayoutX(i * 30);
            playerHand.getChildren().add(playerHandImages);

            dealerHandImages.setFitWidth(150);
            dealerHandImages.setFitHeight(200);
            dealerHandImages.setLayoutX(i * 30);
            dealerHand.getChildren().add(dealerHandImages);
        }
    }

    @FXML
    public void hitButton(ActionEvent e) {
        game.hit();
        //playerHandImages.getChildren().add(game.player.hand.getCardImages().get(game.player.hand.getHandSize() - 1));
        for(int i = 2; i < game.player.hand.getHandSize(); i++){
            playerHandImages = new ImageView(new Image(game.player.hand.getCardPaths().get(i)));
            playerHandImages.setFitWidth(150);
            playerHandImages.setFitHeight(200);
            playerHandImages.setLayoutX(i * 30);
            playerHand.getChildren().add(playerHandImages);

        }


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
        //dealerHandImages.getChildren().set(1, game.dealer.hand.getCardImage(1));

        hitButton.setDisable(true);
        standButton.setDisable(true);
        game.stand();
        //Adds card images that dealer hit.
        //for (int i = 2; i < game.dealer.hand.getCardImages().size(); i++) {
           // dealerHandImages.getChildren().add(game.dealer.hand.getCardImages().get(i));
        //}
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

        dealerHand.getChildren().clear();
        playerHand.getChildren().clear();
        outcomeLabel.setText("");
    }

    private void addCard(Pane pane, String imgPath, int index){
        ImageView imgV = new ImageView(new Image(imgPath));

    }
}