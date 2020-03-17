package tnt.productions.intervaltopace.view;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import tnt.productions.intervaltopace.MainApp;

public class RootLayoutController {
	 private MainApp mainApp;
	 String outputFileName;
	 String fileDirectory;
	 Boolean fileOpened = false;
	 
	 @FXML
	 private void handleAbout() {
		 Alert alert = new Alert(AlertType.INFORMATION);
	     alert.setTitle("Interval To Pace");
	     alert.setHeaderText("About");
	     alert.setContentText("TNT Productions");

	     alert.showAndWait();
	 }

	 @FXML
	 private void handleOpen() {
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Open Interval File");
		 fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Garmin", "*.csv"));
		 File inputFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		 
	     if (inputFile != null) {
 			 fileDirectory =inputFile.getPath(); 
    	     int i = fileDirectory.lastIndexOf('.');
	    	 outputFileName = String.format("%sPace.csv", fileDirectory.substring(0,i));  //  Add "Pace" to input file name
	    	 fileOpened= true;  //  used to determine if I can save a file
	    	 mainApp.loadDataFromFile(inputFile);
	     }
	 }

	@FXML
	private void handleSave() {
		if (fileOpened) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Pace File");
			fileChooser.setInitialFileName(outputFileName);
			File outputFile = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
			
			if (outputFile == null) {
				String msg = String.format("Did not save file");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText(msg);
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						System.out.println("Pressed Information OK.");

					}
				});
			} else {
				System.out.format("The output file: %s\n", outputFileName);
				mainApp.saveDataToFile(outputFile);
			} // end else
		} // end if
	} // end handle Save

	 @FXML
	 private void handleTheme1() {
		 mainApp.HandleTheme1Change();
	 }
	 
	 @FXML
	 private void handleTheme2() {
		 mainApp.HandleTheme2Change();
	 }
	 
	 @FXML
	 private void handleDark() {
		 mainApp.HandleDarkChange();
	 }
	 
	 @FXML
	 private void handleNone() {
		 mainApp.HandleNoneChange();
	 }
	 
	 @FXML
	 private void handleExit() {
		 System.exit(0); 
	 }

	public void setMainApp(MainApp mainApp) {
		// TODO Auto-generated method stub
		this.mainApp = mainApp;
		
	}
}
