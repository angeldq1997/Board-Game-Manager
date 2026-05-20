package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Designer;
import es.angeldam.boardgamemanager.model.Illustrator;
import es.angeldam.boardgamemanager.model.Publisher;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.util.List;

public class SecondariesDAO {
    private static final String SQL_INSERT_MAKE = "INSERT INTO make values (?,?)";
    private static final String SQL_INSERT_DEPICT = "INSERT INTO depict values (?,?)";
    private static final String SQL_INSERT_PRODUCE = "INSERT INTO produce values (?,?)";

    public static boolean insertMake(List<Designer> designers, BoardGame boardGame){
        boolean insertedWithoutErrors = true;
        if ( !designers.isEmpty() ) {
            for (Designer d : designers) {
                if ( d != null ){
                    try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT_MAKE)) {
                        ps.setInt(1, d.getCode());
                        ps.setInt(2, boardGame.getCode());
                        ps.executeUpdate();
                    } catch (Exception e) {
                        insertedWithoutErrors = false;
                        Utils.alert(Alert.AlertType.ERROR, "ERROR WITH DATABASE", "UPLOADING RELATIONS TO DATABASE FAILED", e.getMessage());
                    }
                }
            }
        }
        return insertedWithoutErrors;
    }

    public static boolean insertDepict(List<Illustrator> illustrators, BoardGame boardGame){
        boolean insertedWithoutErrors = true;
        if ( !illustrators.isEmpty() ) {
            for (Illustrator i : illustrators) {
                if ( i != null ){
                    try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT_DEPICT)) {
                        ps.setInt(1, i.getCode());
                        ps.setInt(2, boardGame.getCode());
                        ps.executeUpdate();
                    } catch (Exception e) {
                        insertedWithoutErrors = false;
                        Utils.alert(Alert.AlertType.ERROR, "ERROR WITH DATABASE", "UPLOADING RELATIONS TO DATABASE FAILED", e.getMessage());
                    }
                }
            }
        }
        return insertedWithoutErrors;
    }

    public static boolean insertProduce(List<Publisher> publishers, BoardGame boardGame){
        boolean insertedWithoutErrors = true;
        if ( !publishers.isEmpty() ) {
            for (Publisher p : publishers) {
                if ( p != null ){
                    try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT_PRODUCE)) {
                        ps.setInt(1, p.getCode());
                        ps.setInt(2, boardGame.getCode());
                        ps.executeUpdate();
                    } catch (Exception e) {
                        insertedWithoutErrors = false;
                        Utils.alert(Alert.AlertType.ERROR, "ERROR WITH DATABASE", "UPLOADING RELATIONS TO DATABASE FAILED", e.getMessage());
                    }
                }
            }
        }
        return insertedWithoutErrors;
    }
}