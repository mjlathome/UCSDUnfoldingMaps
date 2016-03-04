package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;
import processing.core.PFont;
//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	// constants
	private final int colourS = color(0, 0, 255);		// blue
	private final int colourM = color(255, 255, 0);	// yellow
	private final int colourL = color(255, 0, 0);		// red
	private final int colourKey = color(175, 175, 175);	// grey
	private final int colourText = color(0, 0, 0);	// black
	
	private final float radiusS = 4f;	// small
	private final float radiusM = 8f;	// medium
	private final float radiusL = 12f;	// large
	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
	    }
	    
	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    // int yellow = color(255, 255, 0);
	    
	    //TODO: Add code here as appropriate
	    
	    // create list of markers
	    for (int i = 0; i < earthquakes.size(); i++) {
	    	markers.add(createMarker(earthquakes.get(i)));
	    }
	    
	    // add markers to map
	    map.addMarkers(markers);
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
				
		// create marker
    	SimplePointMarker quakeMarker = new SimplePointMarker(feature.getLocation(), feature.properties);
    	
    	// extract magnitude
    	Object magObj = feature.getProperty("magnitude");
    	float mag = Float.parseFloat(magObj.toString());
    	
    	if (mag < 4.0) {
    		quakeMarker.setColor(colourS);
    		quakeMarker.setRadius(radiusS);
    	} else if (mag >= 5) {
    		quakeMarker.setColor(colourL);
    		quakeMarker.setRadius(radiusL);
    	} else {
    		quakeMarker.setColor(colourM);
    		quakeMarker.setRadius(radiusM);
    	}
		
		return quakeMarker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		
		// draw box
		fill(colourKey);
		rect(25, 50, 150, 250);
		
		// add labels
		fill(colourText);
		text("Earthquake Key", 50, 75);
		text("5.0+ Magnitude", 75, 130);
		text("4.0+ Magnitude", 75, 180);
		text("Below 4.0", 75, 230);		
		
		// draw marker symbols
		fill(colourL);
		ellipse(50, 125, radiusL, radiusL);	// head

		fill(colourM);
		ellipse(50, 175, radiusM, radiusM);	// head
		
		fill(colourS);
		ellipse(50, 225, radiusS, radiusS);	// head
		
	}
}
