package gameClient;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.graph;
import gui.gui_graph;

public class TestForMe {
	public static void main(String[] args) {


		MyGameGUI g=new MyGameGUI();
		g.draw();
		game_service game = Game_Server.getServer(1); // you have [0,23] games
		//
				Graph_Algo text=new Graph_Algo();
				text.init(game.getGraph());
				//	graph_algorithms t=new Graph_Algo();
				//		t.init((graph) text);
				text.save("my graph");
				gui_graph.drowgraph((graph) text);


//		graph_algorithms ga=new Graph_Algo();
//		ga.init(game.getGraph());
//		ga.save("graph");
//		gui_graph.drowgraph("graph");

	}
}
