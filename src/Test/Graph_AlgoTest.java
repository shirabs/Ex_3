package Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Server.Game_Server;

import oop_dataStructure.*;

import utils.Graph_Algo;

import utils.graph_algorithms;

class Graph_AlgoTest {

	private static graph_algorithms ga;

	@BeforeAll
	static void testInitGraph() {
		String g=Game_Server.getServer(0).getGraph();
		try {
			PrintWriter pw= new PrintWriter("graph to the junit");
			pw.append(g);
			pw.close();
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		oop_graph y=new OOP_DGraph("graph to the junit");
		ga=new Graph_Algo(y);
		System.out.println(ga);
	}
	@Test	
	void testSave() {
		ga.save("graph");
	}
	
	@Test
	void testInitString(){
		graph_algorithms gt=new Graph_Algo();
		gt.init("graph y");
		gt.equals(ga);
	}

	@Test
	void testIsConnect() {
		assertEquals(true, ga.isConnected());
	}
	
	@Test
	void testShortestPathDist() {
	
		assertEquals(ga.shortestPathDist(1, 3), 2.908235082905467);
	}
	
	@Test
	void testShortestPath() {
		assertEquals(ga.shortestPath(5, 2).size(), 4);
	}
	
	@Test
	void testTSP() {
		List< Integer> t=new LinkedList<Integer>(); 
		t.add(1);
		t.add(5);
		t.add(7);
		List<oop_node_data> a=ga.TSP(t);
		assertEquals(true,a.size()>=3);
	}

	@Test
	void testCopy() {
		oop_graph y=ga.copy();
		oop_graph t= new OOP_DGraph("graph to game");
		assertEquals(false,t.equals(y));
	}




}
