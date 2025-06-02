<<<<<<< HEAD
module com.example.blackjack {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.blackjackJavaFX to javafx.fxml;
    exports com.example.blackjackJavaFX;
=======
module com.koltont.blackjack {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens com.koltont.blackjack to javafx.fxml;
    exports com.koltont.blackjack;
>>>>>>> update-ui
}