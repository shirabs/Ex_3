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

    static KML_Logger km_test;
    static final int stage = 10;
    static final String NODE = "node";
    static final String TREE = "fruit-tree";
    static final String BANANA = "fruit-banana";
    static final String ROBOT = "robot";

    @BeforeAll
    static void init() {
        km_test = new KML_Logger(stage);
        double pos_x = 35.207151268054346;
        double pos_y = 32.10259023385377;
        Random rand = new Random();
        for (int i = 0; i < 15; i++) {
           OOP_Point3D p = new OOP_Point3D(pos_x + i, pos_y + i);
            int styleId = rand.nextInt(5);
            switch (styleId) {
                case 0:
                    km_test.addPlace(NODE, p.toString());
                    break;
                case 1:
                    km_test.addPlace(TREE, p.toString());
                    break;
                case 2:
                    km_test.addPlace(BANANA, p.toString());
                    break;
                case 3:
                    km_test.addPlace(ROBOT, p.toString());
                    break;
                default:

            }
        }
        km_test.kmlEnd();
    }

    @Test
    void checkKmlFile()  {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/"+stage+".kml"));
            assertTrue(null!=br.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}