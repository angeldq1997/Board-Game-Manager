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

/**
 * Class that manages the insertions of the codes in the middle tables in N:M relations, this has the objective to do the operations to set the data from Java to the database.
 */
public class SecondariesDAO {
    private static final String SQL_INSERT_MAKE = "INSERT INTO make values (?,?)";
    private static final String SQL_INSERT_DEPICT = "INSERT INTO depict values (?,?)";
    private static final String SQL_INSERT_PRODUCE = "INSERT INTO produce values (?,?)";

    /**
     * Static method that inserts codes on the table make (which connects designers and board games)
     * @param designers designers of the board game
     * @param boardGame the board game which code will be extracted to do one part of the double key
     * @return True if the insertion was successful and False if it encountered an error or exception
     */
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

    /**
     * Static method that inserts codes on the table depict (which connects illustrators and board games)
     * @param illustrators illustrators that worked on the board game illustrations, art and other similar duties
     * @param boardGame board game which code will be extracted to do one part of the double key
     * @return True if the insertion was successful and False if it encountered an error or exception
     */
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

    /**
     * Static method that inserts codes on the table produce (which connects publishers and board games)
     * @param publishers publisher that produce and distribute the board game to stores and other countries
     * @param boardGame board game which code will be extracted to do one part of the double key
     * @return True if the insertion was successful and False if it encountered an error or exception
     */
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