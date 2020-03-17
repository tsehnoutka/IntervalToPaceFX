package tnt.productions.intervaltopace.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tnt.productions.intervaltopace.MainApp;
import tnt.productions.intervaltopace.model.Row;

public class RowOverviewController {
    @FXML
    private TableView<Row> rowTable;
    @FXML
    private TableColumn<Row, String> intervalColumn;
    @FXML
    private TableColumn<Row, String> timeColumn;
    @FXML
    private TableColumn<Row, String> distanceColumn;
    @FXML
    private TableColumn<Row, String> paceColumn;

   

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public RowOverviewController() {
    	// EMPTY
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the columns.
        intervalColumn.setCellValueFactory(cellData -> cellData.getValue().intervalProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        distanceColumn.setCellValueFactory(cellData -> cellData.getValue().distanceProperty());
        paceColumn.setCellValueFactory(cellData -> cellData.getValue().paceProperty());
        //rowTable.setStyle(getClass().getResource("\\src\\theme1.css").toExternalForm());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        // Add observable list data to the table
        rowTable.setItems(mainApp.getRowData());
    }

}
