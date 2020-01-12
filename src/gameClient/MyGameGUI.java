package gameClient;


import java.io.FileNotFoundException;
import java.io.PrintWriter;


import Server.Game_Server;
import Server.game_service;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_graph;


public class MyGameGUI {

	private game_service game;

	public MyGameGUI() {
		game= Game_Server.getServer(1);
	}

	public MyGameGUI(int level) {
		game= Game_Server.getServer(level);
	}

	public void guiGame() {
		oop_graph gr =init();
		System.out.println(gr);
	}










	private oop_graph init()  {
		try {
			PrintWriter pw= new PrintWriter("graph to the game");
			pw.append(game.getGraph());
			pw.close();
			oop_graph g= new OOP_DGraph("graph to the game");
			return g;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}
}
