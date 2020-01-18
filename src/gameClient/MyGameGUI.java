package gameClient;


import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
import oop_elements.OOP_NodeData;
import oop_utils.OOP_Point3D;
import utils.StdDraw;
import java.util.Scanner;


public class MyGameGUI {

	private game_service game;
	private oop_graph g;
	private List<Fruit> f=new ArrayList<Fruit>();
	//	private List<RobotG> r= new ArrayList<RobotG>();

	public MyGameGUI() {

	}
	private void initGame() {
		//set x-axis and y-axis	
		StdDraw.setCanvasSize(1300,600);
		setCanvas(g);
	}


	//drow the level game
	private void guiGame() {
		drowGraph();
		drowFruit();
		drowRobot();
	}

	//drow the fruit on the graph
	private void drowFruit() {
		initfruit();
		Iterator<Fruit> it2=f.iterator();
		while (it2.hasNext()) {
			Fruit temp=it2.next();
			double x= temp.getLocation().x();
			double y= temp.getLocation().y();
			if(temp.getType()==1)
				StdDraw.picture(x, y, "banna.png",0.0005,0.0004);
			else
				StdDraw.picture(x, y, "tree.png",0.0005,0.0004);
		}
	}

	//draw the robot on the graph
	private void drowRobot() {
		List<String> robot=game.getRobots();
		System.out.println(robot);
		Iterator<String> it=robot.iterator();
		try {
			while(it.hasNext()) {
				JSONObject obj = new JSONObject(it.next());
				JSONObject fr = (JSONObject) obj.get("Robot");
				String pos = fr.getString("pos");
				String[] point = pos.split(",");
				double x = Double.parseDouble(point[0]);
				double y = Double.parseDouble(point[1]);
				double z = Double.parseDouble(point[2]);
				StdDraw.picture(x, y, "robot.png",0.0005,0.0005);
			}
		}
		catch (Exception e) {
		}
	}
	//draw the robot in graph menul
	private void drawRobotMenul(OOP_NodeData a) {
		
		try {
				double x=a.getLocation().x();
				double y=a.getLocation().y();
				StdDraw.picture(x, y, "robot.png",0.0005,0.0005);
			
		}
		catch (Exception e) {
		}
	}




	//drow the graph
	private void drowGraph() {
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

	//init the graph of the choose level
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

	//update the fruit from data game
	private void initfruit() {
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
				oop_edge_data edge=findFruitEdge(pos1);
				if(type==-1) {
					Fruit t= new Fruit(value,pos1,new OOP_Edge(edge.getDest(), edge.getSrc()));
					f.add(t);
				}
				else {
					Fruit t= new Fruit(value,pos1,edge);
					f.add(t);
				}
			}
		}
		catch (Exception e) {
			System.out.println("hhhh");
		}
	}

	private oop_edge_data findFruitEdge(OOP_Point3D pos) {
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


	//------------------------------------------------------------------------>>>>	

	public void playManualGame() {

		String num = JOptionPane.showInputDialog(null, "Enter a scenario you want to play : ");
		int scenario_num = Integer.parseInt(num);
		if(scenario_num>=0 && scenario_num<=23) {
			game = Game_Server.getServer(scenario_num);
			g=init();
			initGame();
			//System.out.println(g);
			guiGame();
			try {
				JSONObject line = new JSONObject(game.toString());
				JSONObject gs = line.getJSONObject("GameServer");
				int rs = gs.getInt("robots");
				for(int a = 0;a<rs;a++) {
					String rob = JOptionPane.showInputDialog(null, "chose were you want to put the robot  : ");
					putRobotMenul(Integer.parseInt(rob));
					System.out.println(rob);
					oop_node_data ezer=g.getNode(Integer.parseInt(rob));
					drawRobotMenul((OOP_NodeData) ezer);
				}

			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


	public void PlayAotuGame() {
		String num = JOptionPane.showInputDialog(null, "Enter a scenario you want to play : ");
		int scenario_num = Integer.parseInt(num);
		if(scenario_num>=0 && scenario_num<=23) {
			game = Game_Server.getServer(scenario_num);
			g=init();
			initGame();
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
			//			System.out.println(game.move());
			//			System.out.println(game.getRobots());

			while(game.isRunning()) {
				StdDraw.clear();
				List<String> robots = game.move();
				for(String robot : robots) {
					long t = game.timeToEnd();
					String robot_json = robot;
					line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");

					if(dest==-1) {	
						dest = nextNode(src);
						game.chooseNextEdge(rid, dest);

						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						System.out.println(ttt);
					}
				}
				//										game.move();
				guiGame();
				Thread.sleep(150);

			}
		}

		catch (Exception e) {
			// TODO: handle exception
		}

	}


	private  int nextNode( int src) {
		int ans = -1;
		Collection<oop_edge_data> ee = g.getE(src);
		Iterator<oop_edge_data> itr = ee.iterator();
		Iterator<Fruit> it = f.iterator();
		while(itr.hasNext()){
			OOP_Point3D t=g.getNode(itr.next().getSrc()).getLocation();
			while(it.hasNext()) {
				if(t.distance2D(it.next().getLocation())<0.0003)
					return src;
			}
		}
		ans = itr.next().getDest();
		return ans;

	}



	private void putRobot(int v) {
		if(v<f.size()) {
			game.addRobot(findFruitEdge(f.get(v).getLocation()).getSrc());
		}
	}

	private void putRobotMenul(int a) {
		game.addRobot(pointch(a));

	}

	private int pointch(int a) {
		
		oop_node_data loc=g.getNode(a);
		return loc.getKey();
	}
}
