module es.angeldam.boardgamemanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.angeldam.boardgamemanager to javafx.fxml;
    exports es.angeldam.boardgamemanager;
    exports es.angeldam.boardgamemanager.controllers;
    opens es.angeldam.boardgamemanager.controllers to javafx.fxml;
}