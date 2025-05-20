module com.koltont.blackjack {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.koltont.blackjack to javafx.fxml;
    exports com.koltont.blackjack;
}