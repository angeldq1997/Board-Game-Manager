module es.angeldam.boardgamemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires mysql.connector.j;

    opens es.angeldam.boardgamemanager.dataAccess to java.xml.bind;
    exports es.angeldam.boardgamemanager.dataAccess;
    opens es.angeldam.boardgamemanager to javafx.fxml;
    exports es.angeldam.boardgamemanager;
    exports es.angeldam.boardgamemanager.controllers;
    opens es.angeldam.boardgamemanager.controllers to javafx.fxml;

    exports es.angeldam.boardgamemanager.model;
    exports es.angeldam.boardgamemanager.utils;
    exports es.angeldam.boardgamemanager.controllers.subcontrollers;
    opens es.angeldam.boardgamemanager.controllers.subcontrollers to javafx.fxml;
}