package gameClient;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * this class create 24 kml files that can be loaded to google earth and view the game
 * in a specific level
 * this is only for auto game.
 */

public class KML_Logger {

	private int stage;
	private StringBuffer str;

	/**
	 *  constructor
	 * @param level
	 */
	public KML_Logger(int stage) {
		this.stage = stage;
		str = new StringBuffer();
		KMLStart();
	}


	//	  this function restart the KML file
	private void KMLStart(){
		str.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
						"<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" +
						"  <Document>\r\n" +
						"    <name>" + "Game slevel :"+stage + "</name>" +"\r\n"
				);
		KMLnode();
	}


	//	  this function restart the node in the KML
	private void KMLnode(){
		str.append(" <Style id=\"node\">\r\n" +
				"      <IconStyle>\r\n" +
				"        <Icon>\r\n" +
				"          <href>http://maps.google.com/mapfiles/kml/pal3/icon35.png</href>\r\n" +
				"        </Icon>\r\n" +
				"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
				"      </IconStyle>\r\n" +
				"    </Style>"
				);
		KMLFruit();
	}


	//	  this function restart the Fruits in the KML ,-1 or 1 according to thr type

	private void KMLFruit(){
		str.append(
				" <Style id=\"fruit_-1\">\r\n" +
						"      <IconStyle>\r\n" +
						"        <Icon>\r\n" +
						"          <href>http://maps.google.com/mapfiles/kml/paddle/purple-stars.png</href>\r\n" +
						"        </Icon>\r\n" +
						"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
						"      </IconStyle>\r\n" +
						"    </Style>" +
						" <Style id=\"fruit_1\">\r\n" +
						"      <IconStyle>\r\n" +
						"        <Icon>\r\n" +
						"          <href>http://maps.google.com/mapfiles/kml/paddle/red-stars.png</href>\r\n" +
						"        </Icon>\r\n" +
						"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
						"      </IconStyle>\r\n" +
						"    </Style>"
				);
		KMLRobot();
	}


	//	  this function restart the Robots in the KML

	private void KMLRobot(){
		str.append(
				" <Style id=\"robot\">\r\n" +
						"      <IconStyle>\r\n" +
						"        <Icon>\r\n" +
						"          <href>http://maps.google.com/mapfiles/kml/shapes/motorcycling.png></href>\r\n" +
						"        </Icon>\r\n" +
						"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
						"      </IconStyle>\r\n" +
						"    </Style>"
				);
	}


	//	  this function used in gui
	//	  after all move of robot and fruit
	//	  the function enters to the kml the location of robot and fruit 

	public void Place_Mark(String id, String location){
		LocalDateTime Present_time = LocalDateTime.now();
		str.append(
				"    <Placemark>\r\n" +
						"      <TimeStamp>\r\n" +
						"        <when>" + Present_time+ "</when>\r\n" +
						"      </TimeStamp>\r\n" +
						"      <styleUrl>#" + id + "</styleUrl>\r\n" +
						"      <Point>\r\n" +
						"        <coordinates>" + location + "</coordinates>\r\n" +
						"      </Point>\r\n" +
						"    </Placemark>\r\n"
				);
	}


	void KML_Stop(){
		str.append("  \r\n</Document>\r\n" +
				"</kml>");
		SaveToFile();
	}

// this function save all the KML to file in the data location
	private void SaveToFile(){
		try {
			File file=new File("data/"+stage+".kml");
			PrintWriter pw=new PrintWriter(file);
			pw.write(str.toString());
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}