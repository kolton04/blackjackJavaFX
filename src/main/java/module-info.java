module com.koltont.blackjack {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens com.koltont.blackjack to javafx.fxml;
    exports com.koltont.blackjack;
}