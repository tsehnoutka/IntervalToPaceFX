package tnt.productions.intervaltopace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tnt.productions.intervaltopace.model.Row;
import tnt.productions.intervaltopace.view.RootLayoutController;
import tnt.productions.intervaltopace.view.RowOverviewController;

//  Wanted to make a change to test Git Hub

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<Row> rowData = FXCollections.observableArrayList();
	private Scene scene;
	//private String theme1Url = getClass().getResource("..\\resource\\theme1.css").toExternalForm();
	//private String theme1Url = getClass().getResource("..\\..\\..\\..\\resource\\theme1.css").toExternalForm();
	//private String theme1Url = getClass().getResource("C:\\Users\\tim\\workspace\\IntervalToPaceFX\\resources\\theme1.css").toExternalForm();
	private String theme1Url = getClass().getResource("theme1.css").toExternalForm();
	private String theme2Url = getClass().getResource("theme2.css").toExternalForm();
	private String themeDarkUrl = getClass().getResource("DarkTheme.css").toExternalForm();
	String currentTheme = "None";


	public MainApp() {

	}

	public ObservableList<Row> getRowData() {
		return rowData;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Interval To Pace");

		initRootLayout();
		showRowOverview();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the row overview inside the root layout.
	 */
	public void showRowOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RowOverview.fxml"));
			AnchorPane rowOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(rowOverview);

			// Give the controller access to the main app.
			RowOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("resource")
	public void loadDataFromFile(File inputFile) {
		final LineData myLineData= new LineData();
		
		rowData.clear();  //  clear the table in case this is not the first file being open/

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(inputFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.

			//String line = null;
			//while ((line = bufferedReader.readLine()) != null) {
			bufferedReader.lines().forEach(lines -> {
				// I don't want to do anything with the first(header) and last(summary) lines in the file
				//first = line.contains("Split");
				//last = line.contains("Summary");
				if (lines.contains("Split") || lines.contains("Summary")) {
					//System.out.format("First or last line:\n%s\n", line);
					return;
				}
				//System.out.println(line);
				List<String> items = Arrays.asList(lines.split(",")); // split the comma delimited file's line
				
				// get time
				String time = items.get(1); // get the interval time
				// Separate the minutes and the seconds
				List<String> iTime = Arrays.asList(time.split("\\."));  
				iTime = Arrays.asList(iTime.get(0).split(":")); 
				int iMin=0;
				int iSec=Integer.valueOf(iTime.get(1));
				String tmp = iTime.get(0);
				if ( !tmp.equals("") )
					iMin = Integer.valueOf(iTime.get(0));
				
				myLineData.setTotalTime(myLineData.addTotalTime(60*iMin + iSec));  // Convert time to seconds and add it to the total time
				
				// get distance
				Float dist = Float.valueOf(items.get(3)); // Get the interval distance
				myLineData.addTotalDist(dist); // add the distance to the total Distance

				// If the distance is greater than or equal to a mile.
				if (myLineData.getTotalDist() >= 1) {
					//System.out.format("Distance: %.2f", totalDist);
					//  need to remove the excess distance
					myLineData.setExcessDist(myLineData.getTotalDist() - 1);
					int pace = (int) (myLineData.getTotalTime() / myLineData.getTotalDist()); // get pace in Seconds
					myLineData.setTotalDist(1);  //  set distance to one
					// extrapolate Time
					myLineData.setExcessTime(myLineData.getTotalTime() - pace);
					//System.out.format("  Excess Dist: %.2f  Total Time:%d  Excess Time: %d\n", excessDist,totalTime,excessTime);
					
					
					int min = (int) pace / 60; // get the minute of the pace
					int sec = (int) (pace % 60); //  get the seconds of the pace

					// write string to output file
					
					String tmpDistance = String.format("%.2f\t", myLineData.getTotalDist());
					String tmpPace = String.format("%2d:%02d", min, (int) (sec));
					
					rowData.add(new Row(Integer.toString(myLineData.getX()), tmpPace, tmpDistance,tmpPace));

					// Write file to Console ( i put these on separate line for
					// easy debugging )
					System.out.format("Interval: %2d\t", myLineData.getX()); // Print out interval
					System.out.format("Time: %s\t", tmpPace); // Print out Time
					System.out.format("Distance: %.2f\t", myLineData.getTotalDist()); // Print out Distance
					System.out.format("Pace: %2s\n",tmpPace); // Print out pace; multiplying by .6 to convert percent of minute to seconds
					myLineData.incrementX();
					
					// reset totals
					myLineData.setTotalTime(myLineData.getExcessTime());
					myLineData.setTotalDist(myLineData.getExcessDist());
				} // end if total distance is greater than 1 mile

			}); // end for the lines in the file loop
			
			//  print  out the last partial interval.
			int pace = (int) (myLineData.getTotalTime() / myLineData.getTotalDist()); // get pace in Seconds
			int min = (int) pace / 60; // get the minute of the pace
			int sec = (int) (pace % 60); //  get the seconds of the pace

			// write string to output file
			String tmpDistance = String.format("%.2f\t", myLineData.getTotalDist());
			String tmpPace = String.format("%2d:%02d", min, (int) (sec));
			String tmpTime = String.format("%2d:%02d", (myLineData.getTotalTime()-myLineData.getExcessTime())/60, (myLineData.getTotalTime() - myLineData.getExcessTime()) % 60 );
			
			rowData.add(new Row(Integer.toString(myLineData.getX()), tmpTime, tmpDistance, tmpPace));

			// Write file to Console ( i put these on separate line for
			// easy debugging )
			System.out.format("Interval: %2d\t", myLineData.getX()); // Print out interval
			System.out.format("Time: %s\t", tmpTime); // Print out Time
			System.out.format("Distance: %.2f\t", myLineData.getTotalDist()); // Print out Distance
			System.out.format("Pace: %2s\n",tmpPace); // Print out pace; multiplying by .6 to convert percent of minute to seconds
			
			// Always close files.
			bufferedReader.close();
		} // end try

		catch (IOException ex) {
			System.out.println("Error reading file '" + inputFile.getName() + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}

	public void saveDataToFile(File inputFile) {
		try {
		// FileReader fileReader = new FileReader(outputFile.getName());
			inputFile.createNewFile(); // creates the file
			FileWriter fileWriter = new FileWriter(inputFile); // creates a FileWriter Object

			fileWriter.write("Interval,Time,Distance,Pace\n"); // Writes the header to the file
			String tmpString;
			// loop through the data
			for (Row myRow : rowData) {
				tmpString = String.format("%2s,%s,%s,%s\n", myRow.getInterval(), myRow.getTime(), myRow.getDistance(),
						myRow.getPace());
				fileWriter.write(tmpString);
			}

			// fileWriter.write(outputString);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void HandleTheme1Change(){
        System.out.println("\nBefore - HandleTheme1Change: " + scene.getStylesheets());
        System.out.println("Current Theme: " + currentTheme);
        
        if ( !currentTheme.equals("None"))
        		scene.getStylesheets().remove(currentTheme);
        if(!scene.getStylesheets().contains(theme1Url)){
        	scene.getStylesheets().add(theme1Url);
        }
        currentTheme=theme1Url;
        
        System.out.println("Current Theme: " + currentTheme);
        System.out.println("After - HandleTheme1Change: " + scene.getStylesheets());
	}
	public void HandleTheme2Change(){
        if ( !currentTheme.equals("None"))
        		scene.getStylesheets().remove(currentTheme);
        if(!scene.getStylesheets().contains(theme2Url)){
        	scene.getStylesheets().add(theme2Url);
        }
        currentTheme=theme2Url;
	}
	public void HandleDarkChange(){
        if ( !currentTheme.equals("None"))
        		scene.getStylesheets().remove(currentTheme);
        if(!scene.getStylesheets().contains(themeDarkUrl)){
        	scene.getStylesheets().add(themeDarkUrl);
        }
        currentTheme=themeDarkUrl;
	}
	public void HandleNoneChange(){
        if ( !currentTheme.equals("None"))
			scene.getStylesheets().remove(currentTheme);
        currentTheme="None";
	}
}
