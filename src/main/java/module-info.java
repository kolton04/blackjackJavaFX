module com.example.blackjack {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.blackjackJavaFX to javafx.fxml;
    exports com.example.blackjackJavaFX;
}