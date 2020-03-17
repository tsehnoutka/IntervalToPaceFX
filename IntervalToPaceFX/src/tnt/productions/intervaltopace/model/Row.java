package tnt.productions.intervaltopace.model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;


public class Row{
    private final StringProperty interval ;
    private final StringProperty time ;
    private final StringProperty distance ;
    private final StringProperty  pace;
    
    public Row(String intValue, String timeValue, String distValue, String paceValue) {
        this.interval = new SimpleStringProperty (intValue) ;
        this.time = new SimpleStringProperty (timeValue) ;
        this.distance = new SimpleStringProperty (distValue) ;
        this.pace = new SimpleStringProperty (paceValue) ;
    }
    public String getInterval() {
        return interval.get() ;
    }
    public StringProperty intervalProperty(){
    	return interval;
    }
    
    public String getTime() {
        return time.get() ;
    }
    public StringProperty timeProperty(){
    	return time;
    }

    public String getDistance() {
        return distance.get() ;
    }
    public StringProperty distanceProperty(){
    	return distance;
    }

    public String getPace() {
        return pace.get() ;
    }
    public StringProperty paceProperty(){
    	return pace;
    }
}
