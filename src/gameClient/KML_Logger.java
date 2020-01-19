package gameClient;

import java.io.IOException;
import java.util.logging.*;

public class KML_Logger {
	private static Logger theLogger=Logger.getLogger("myLogger");
	private static FileHandler theFileHandler;
	
	public static void main(String[] args) throws SecurityException, IOException {
		theFileHandler =new FileHandler("myLogger.xml");
		theLogger.addHandler(theFileHandler);
		
		theFileHandler.setFormatter(new XMLFormatter());
		MyGameGUI mg=new MyGameGUI();
				}
}
