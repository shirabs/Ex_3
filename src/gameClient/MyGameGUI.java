package gameClient;


import java.awt.Color;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


import javax.swing.JOptionPane;


import org.json.JSONException;
import org.json.JSONObject;


import Server.*;
import oop_dataStructure.*;
import oop_elements.OOP_Edge;
import oop_utils.OOP_Point3D;
import utils.Graph_Algo;
import utils.StdDraw;
import utils.graph_algorithms;




public class MyGameGUI {

	private game_service game;
	private oop_graph g;
	private List<Fruit> f=new ArrayList<Fruit>();
	double xmin=Double.MAX_VALUE;
	double xmax=Double.MIN_VALUE;
	double ymin=Double.MAX_VALUE;
	double ymax=Double.MIN_VALUE;
	private static KML_Logger kml;
	public static int i=0;

	public MyGameGUI() {
		StdDraw.setCanvasSize(1300, 600);
		String game = (JOptionPane.showInputDialog(null, "choose type game ","game",
				JOptionPane.PLAIN_MESSAGE,null,new Object[] {"play Manual Game","Play Aotu Game"},"select")).toString();
		if(game=="play Manual Game") {
			playManualGame();
		}
		else {
			PlayAotuGame();
		}

	}

	private void initGame() {
		//set x-axis and y-axis	
		StdDraw.setCanvasSize(1300,600);
		setCanvas(g);

	}

	private void guiGame() {
		drawFruit();
		drawRobot();
	}

	//draw the fruit on the graph
	private void drawFruit() {
		initfruit();
		Iterator<Fruit> it2=f.iterator();
		while (it2.hasNext()) {
			Fruit temp=it2.next();
			double x= temp.getLocation().x();
			double y= temp.getLocation().y();
			if(temp.getType()==1)
				StdDraw.picture(x, y, "dollar.png",0.0005,0.0004);
			else
				StdDraw.picture(x, y, "monye.png",0.0005,0.0004);
		}
	}

	//draw the robot on the graph
	private void drawRobot() {
		List<String> robot=game.getRobots();
		System.out.println(i+")   "+robot);
		i++;

		try {
			for(String r:robot) {
				JSONObject obj = new JSONObject(r);
				JSONObject fr = (JSONObject) obj.get("Robot");
				String pos = fr.getString("pos");
				String[] point = pos.split(",");
				double x = Double.parseDouble(point[0]);
				double y = Double.parseDouble(point[1]);
				StdDraw.picture(x, y, "robot1.png",0.0005,0.0005);
				kml.Place_Mark("robot.png",pos);

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	//draw the graph
	private void drawGraph() {
		StdDraw.picture((xmax+xmin)/2, (ymax+ymin)/2,"mmm.png",0.022,0.0079);
		//draw point node
		Collection<oop_node_data> v =g.getV();
		Iterator<oop_node_data> it=v.iterator();
		StdDraw.setPenRadius(0.02);
		StdDraw.setPenColor(Color.BLACK);
		while (it.hasNext()) {
			oop_node_data node=it.next();
			double x=node.getLocation().x();
			double y=node.getLocation().y();
			StdDraw.point(x,y);
			StdDraw.text(x+0.0001, y+0.0001, node.getKey()+"");
		}
		//draw line of edge , weight of edge and the direction of the edge 
		it=v.iterator();
		while (it.hasNext()) {
			Collection<oop_edge_data> e =g.getE(it.next().getKey());
			if(e!=null) {
				Iterator<oop_edge_data> it2=e.iterator();
				while (it2.hasNext()) {
					oop_edge_data edge=it2.next();
					StdDraw.setPenRadius(0.0015);
					StdDraw.setPenColor(Color.BLACK);
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

	//init the graph of the choose level
	private oop_graph init()  {
		try {
			PrintWriter pw= new PrintWriter("graph to the game");
			pw.append(game.getGraph());
			pw.close();
			oop_graph g= new OOP_DGraph("graph to the game");
			return g;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null ;
	}

	//update the fruit from data game
	private void initfruit() {
		f=new ArrayList<Fruit>();
		List<String> fruit=game.getFruits();
		Iterator<String> it=fruit.iterator();
		try {
			while(it.hasNext()) {
				JSONObject obj = new JSONObject(it.next());
				JSONObject fr = (JSONObject) obj.get("Fruit");
				double value = fr.getDouble("value");
				int type = fr.getInt("type");
				String pos = fr.getString("pos");
				String[] point = pos.split(",");
				double x = Double.parseDouble(point[0]);
				double y = Double.parseDouble(point[1]);
				double z = Double.parseDouble(point[2]);
				OOP_Point3D pos1 = new OOP_Point3D(x, y, z);
				oop_edge_data edge=foundFruitEdge(pos1);
				if(type==-1) {
					Fruit t= new Fruit(value,pos1,new OOP_Edge(edge.getDest(), edge.getSrc()));
					f.add(t);
					kml.Place_Mark("monye", pos);
				}
				else {
					Fruit t= new Fruit(value,pos1,edge);
					f.add(t);
					kml.Place_Mark("dollar", pos);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// found the edge that the fruit found their	
	private oop_edge_data foundFruitEdge(OOP_Point3D pos) {
		Collection<oop_node_data> v = g.getV();
		for(oop_node_data n : v) {
			Collection<oop_edge_data> e = g.getE(n.getKey());
			for(oop_edge_data ed: e) {
				OOP_Point3D p =g.getNode(ed.getSrc()).getLocation();
				OOP_Point3D p2 =g.getNode(ed.getDest()).getLocation();
				//check if the fruit is on the edge
				if(Math.abs((p.distance2D(p2)-(Math.abs((pos.distance2D(p)))+(Math.abs((pos.distance2D(p2))))))) <= 0.0000001)
				{
					int low=n.getKey();
					int high=ed.getDest();
					if(n.getKey()>ed.getDest()) {
						low= ed.getDest();
						high= n.getKey();
					}
					oop_edge_data edF = g.getEdge(low, high);
					if(edF!= null) return edF;
				}
			}
		}
		return null;
	}

	//play manual game
	public void playManualGame() {

		String num = JOptionPane.showInputDialog(null, "Enter a scenario you want to play : ");
		int scenario_num = Integer.parseInt(num);
		if(scenario_num>=0 && scenario_num<=23) {
			game = Game_Server.getServer(scenario_num);
			kml=new KML_Logger(scenario_num);
			g=init();
			initGame();
			drawGraph();
			guiGame();
			try {
				JSONObject line = new JSONObject(game.toString());
				JSONObject gs = line.getJSONObject("GameServer");
				int rs = gs.getInt("robots");
				for(int a = 0;a<rs;a++) {
					String rob = JOptionPane.showInputDialog(null, "choose were you want to put the robot  : ");
					game.addRobot(Integer.parseInt(rob));
				}
				guiGame();
				game.startGame();
				while(game.isRunning()) {
					StdDraw.clear();
					StdDraw.enableDoubleBuffering();
					drawGraph();
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(xmin+0.0003 , ymin+0.0005 , "time to end 00:"+game.timeToEnd()/1000  );
					StdDraw.text(xmin+0.00001, ymin , "result:"+sumResult());
					List<String> robots = game.move();
					graph_algorithms ag= new Graph_Algo();
					ag.init(g);
					if(robots!=null) {
						for(String robot : robots) {
							String robot_json = robot;
							line = new JSONObject(robot_json);
							JSONObject r = line.getJSONObject("Robot");
							int id = r.getInt("id");
							int src = r.getInt("src");
							int dest = r.getInt("dest");
							if(dest==-1) {	
								guiGame();
								StdDraw.show();
								String nextnode = JOptionPane.showInputDialog(null, "choose were you want to go with the robot "+id+" : ");
								List<oop_node_data> ro= ag.shortestPath(src, Integer.parseInt(nextnode));
								System.out.println(ro);
								for(oop_node_data n:ro) {
									game.chooseNextEdge(id, n.getKey());
									game.move();
								}
							}
						}
						guiGame();
						StdDraw.show();
						Thread.sleep(100);
					}
				}
				System.out.println(game.toString());
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.picture(35.197730011299,32.104700000825,"game over.png");
				StdDraw.text(35.197730011299, 32.10469393931, "results: "+ 	sumResult());
				StdDraw.show();

			}
			catch (JSONException | InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	//	play automatic game
	public void PlayAotuGame() {
		String num = JOptionPane.showInputDialog(null, "Enter a scenario you want to play : ");
		int scenario_num = Integer.parseInt(num);
		if(scenario_num>=0 && scenario_num<=23) {
			kml=new KML_Logger(scenario_num);
			game = Game_Server.getServer(scenario_num);
			g=init();
			initGame();
			drawGraph();
			guiGame();
		}
		try {
			JSONObject line = new JSONObject(game.toString());
			JSONObject gs = line.getJSONObject("GameServer");
			int rs = gs.getInt("robots");
			int visit=0;
			for(int a = 0;a<rs;a++) {
				putRobot(visit);
				visit++;
			}
			guiGame();
			game.startGame();
			while(game.isRunning()) {

				StdDraw.clear();
				StdDraw.enableDoubleBuffering();
				drawGraph();
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.text(xmin+0.0003 , ymin+0.0005 , "time to end 00:"+game.timeToEnd()/1000  );
				StdDraw.text(xmin+0.00001, ymin , "result:"+sumResult());

				List<String> robots = game.move();
				if(robots!=null) {
					for(String robot : robots) {
						String robot_json = robot;
						line = new JSONObject(robot_json);
						JSONObject r = line.getJSONObject("Robot");
						int id = r.getInt("id");
						int src = r.getInt("src");
						int dest = r.getInt("dest");

						if(dest==-1) {	
							dest = nextNode(src);
							game.chooseNextEdge(id, dest);
						}
					}
					guiGame();
					StdDraw.show();
					//				Thread.sleep(100);
				}
			}
			System.out.println(game.toString());
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.picture(35.197730011299,32.104700000825,"game over.png");
			StdDraw.text(35.197730011299, 32.10469393931, "results: "+ 	sumResult());
			StdDraw.show();
			kml.KML_Stop();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	// print to the window the result of the game	
	private double sumResult() {
		try {
			double result=0;
			for(String r:game.getRobots()) {
				JSONObject line = new JSONObject(r);
				JSONObject s = line.getJSONObject("Robot");
				result+= s.getDouble("value");
			}
			return result;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return (Double) null;
	}

	//return the hext node to move from the short path to the fruit	
	private  int nextNode( int src) {
		graph_algorithms ag=new Graph_Algo();
		ag.init(g);
		int a= foundFruitEdge(f.get(0).getLocation()).getDest();
		double spd=ag.shortestPathDist(src, a);
		List<oop_node_data> sp=ag.shortestPath(src,a);
		for( fruits fruit:f ) {
			a= foundFruitEdge(fruit.getLocation()).getDest();
			if(a==src) {
				a= foundFruitEdge(fruit.getLocation()).getSrc();
			}
			spd=ag.shortestPathDist(src, a);
			double spdt=ag.shortestPathDist(src, a);
			if(spd>spdt||sp.size()==1){
				sp=ag.shortestPath(src, a);;

			}
		}
		return sp.get(1).getKey();
	}

	//	add robot to the game
	private void putRobot(int v) {
		if(v<f.size()) {
			game.addRobot(foundFruitEdge(f.get(v).getLocation()).getSrc());
		}
	}

}
