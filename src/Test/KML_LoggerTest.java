package Test;

import gameClient.KML_Logger;
import oop_utils.OOP_Point3D;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class KML_LoggerTest {

	static KML_Logger KMLtest;
	static final int level = 23;
	static final String NODE = "node";
	static final String TREE = "fruit-tree";
	static final String BANANA = "fruit-banana";
	static final String ROBOT = "robot";

	@BeforeAll
	static void init() {
		KMLtest = new KML_Logger(level);
		double pos_x = 35.207151268054346;
		double pos_y = 32.10259023385377;
		Random rand = new Random();
		for (int i = 0; i < 15; i++) {
			OOP_Point3D p = new OOP_Point3D(pos_x + i, pos_y + i);
			int sId = rand.nextInt(5);
			if(sId==0)
				KMLtest.addPlace(NODE, p.toString());

			if(sId==1)
				KMLtest.addPlace(TREE, p.toString());
			if(sId==2)
				KMLtest.addPlace(BANANA, p.toString());
			if(sId==3)
				KMLtest.addPlace(ROBOT, p.toString());

		}
		
		KMLtest.KMLEnd();
	}

	@Test
	void KmlFile()  {
		try {
			BufferedReader br = new BufferedReader(new FileReader("data/"+level+".kml"));
			assertTrue(null!=br.readLine());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}