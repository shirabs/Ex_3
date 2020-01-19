package gameClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class represent a KML_Logger object that creates a KML file for each game.
 */
public class KML_Logger {

	private int level;
	private StringBuilder info;


	// Constructor init the object.
	public KML_Logger(int level) {
		this.level = level;
		info = new StringBuilder();
		kmlStart();
	}


	//  opening string for the KML file. Set the elements of the game such as: node, fruit and robot that will be added as a placemark to the KML file.

	public void kmlStart()
	{
		info.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
						"<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" +
						"  <Document>\r\n" +
						"    <name>" + "Game stage :"+this.level + "</name>" +"\r\n"+
						" <Style id=\"node\">\r\n" +
						"      <IconStyle>\r\n" +
						"        <Icon>\r\n" +
						"          <href>http://maps.google.com/mapfiles/kml/pal3/icon35.png</href>\r\n" +
						"        </Icon>\r\n" +
						"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
						"      </IconStyle>\r\n" +
						"    </Style>" +
						" <Style id=\"fruit-tree\">\r\n" +
						"      <IconStyle>\r\n" +
						"        <Icon>\r\n" +
						"          <href>http://maps.google.com/mapfiles/kml/pal5/icon49.png</href>\r\n" +
						"        </Icon>\r\n" +
						"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
						"      </IconStyle>\r\n" +
						"    </Style>" +
						" <Style id=\"fruit-banna\">\r\n" +
						"      <IconStyle>\r\n" +
						"        <Icon>\r\n" +
						"          <href>http://maps.google.com/mapfiles/kml/pal5/icon56.png</href>\r\n" +
						"        </Icon>\r\n" +
						"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
						"      </IconStyle>\r\n" +
						"    </Style>" +
						" <Style id=\"robot\">\r\n" +
						"      <IconStyle>\r\n" +
						"        <Icon>\r\n" +
						"          <href>http://maps.google.com/mapfiles/kml/pal4/icon26.png></href>\r\n" +
						"        </Icon>\r\n" +
						"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
						"      </IconStyle>\r\n" +
						"    </Style>"
				);
	}


	//	  Add place to the KML.
	public void addPlace(String id, String location)
	{
		//Create format
		DateTimeFormatter fo = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		//Local date time instance
		LocalDateTime ldt = LocalDateTime.now();
		//Get format
		String lds = fo.format(ldt);

		info.append(
				"    <Placemark>\r\n" +
						"      <TimeStamp>\r\n" +
						"        <when>" + lds+ "</when>\r\n" +
						"      </TimeStamp>\r\n" +
						"      <styleUrl>#" + id + "</styleUrl>\r\n" +
						"      <Point>\r\n" +
						"        <coordinates>" + location + "</coordinates>\r\n" +
						"      </Point>\r\n" +
						"    </Placemark>\r\n"
				);

	}


	//	  Connect the closing string for the KML file.
	public void kmlEnd()
	{
		info.append("  \r\n</Document>\r\n" +  "</kml>" );
		try
		{
			File f=new File("data/"+this.level+".kml");
			PrintWriter pw=new PrintWriter(f);
			pw.write(info.toString());
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}