package gameClient;


import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Server.Game_Server;
import Server.*;
import oop_dataStructure.*;
import oop_utils.OOP_Point3D;
import utils.StdDraw;


public class MyGameGUI {

	private game_service game;
	oop_graph g;

	public MyGameGUI() {
		game= Game_Server.getServer(0);
		g =init();
	}

	public MyGameGUI(int level) {
		game= Game_Server.getServer(level);
		g =init();
	}

	public void guiGame() {
		drowGraph();
		drowFruit();
	}

	private void drowFruit() {
		List<String> f= game.getFruits();
		System.out.println(f);

		LinkedList<fruits> fruits= new LinkedList<fruits>();
		Iterator<String> it=f.iterator();
		while (it.hasNext()) {
			String temp=it.next();
			String s= temp.substring(temp.indexOf("\"pos\":"));
			double x= Double.parseDouble(s.substring(7, s.indexOf(",")));
			double y= Double.parseDouble(s.substring(s.indexOf(",")+1, s.lastIndexOf(',')));
			if(temp.contains("\"type\":1")) {
				StdDraw.picture(x, y, "banna.png",0.0008,0.0005);
			}
			else
				StdDraw.picture(x, y, "tree.png",0.0008,0.0005);
		}

























	}
	private void drowGraph() {
		//set x-axis and y-axis	
		StdDraw.setCanvasSize();
		setCanvas(g);
		//drow point node
		Collection<oop_node_data> v =g.getV();
		Iterator<oop_node_data> it=v.iterator();
		StdDraw.setPenRadius(0.015);
		StdDraw.setPenColor(Color.BLACK);
		while (it.hasNext()) {
			oop_node_data node=it.next();
			double x=node.getLocation().x();
			double y=node.getLocation().y();
			StdDraw.point(x,y);
			StdDraw.text(x+0.0001, y+0.0001, node.getKey()+"");
		}
		//drow line of edge , weight of edge and the direction of the edge 
		it=v.iterator();
		while (it.hasNext()) {
			Collection<oop_edge_data> e =g.getE(it.next().getKey());
			if(e!=null) {
				Iterator<oop_edge_data> it2=e.iterator();
				while (it2.hasNext()) {
					oop_edge_data edge=it2.next();
					StdDraw.setPenRadius(0.0005);
					StdDraw.setPenColor(Color.BLUE);
					double x1=g.getNode(edge.getSrc()).getLocation().x();
					double x2=g.getNode(edge.getDest()).getLocation().x();
					double y1=g.getNode(edge.getSrc()).getLocation().y();
					double y2=g.getNode(edge.getDest()).getLocation().y();
					StdDraw.line(x1, y1, x2, y2);
					String w =new java.text.DecimalFormat("0.0").format( edge.getWeight());
					StdDraw.text((2*x1+8*x2)/10, (2*y1+8*y2)/10, w);
					StdDraw.setPenRadius(0.01);
					StdDraw.setPenColor(Color.ORANGE);
					StdDraw.point((0.6*x1+9.4*x2)/10, (0.6*y1+9.4*y2)/10);
				}
			}
		}
	}

	//update the range
	private  void setCanvas(oop_graph g) {	
		double xmin=Double.MAX_VALUE;
		double xmax=Double.MIN_VALUE;
		double ymin=Double.MAX_VALUE;
		double ymax=Double.MIN_VALUE;

		Collection<oop_node_data> v =g.getV();
		Iterator<oop_node_data> it=v.iterator();
		while(it.hasNext()) {
			oop_node_data node=it.next();
			if(node.getLocation().x()<xmin) xmin=node.getLocation().x();
			if(node.getLocation().x()>xmax) xmax=node.getLocation().x();
			if(node.getLocation().y()<ymin) ymin=node.getLocation().y();
			if(node.getLocation().y()>ymax) ymax=node.getLocation().y();
		}
		System.out.println(xmax+"    "+xmin+"    "+ymax+"    "+ymin);
		StdDraw.setXscale(xmin-0.001,xmax+0.001);
		StdDraw.setYscale(ymin-0.001,ymax+0.001);
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
