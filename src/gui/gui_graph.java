package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import java.util.Scanner;
import utils.StdDraw;

public class gui_graph {


	public static void drowgraph(graph g) {
		graph_algorithms ga=new Graph_Algo();
		ga.init(g);
		ga.save("graph");

		//update the range
		StdDraw.setCanvasSize();
		setCanvas(g);

		//action on the button is connect that show the graph in green color if the graph connect
		//else in the red color		
		StdDraw.isConnectedButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(ga.isConnected()) 
					drowLine(Color.GREEN, g);
				else drowLine(Color.RED, g);
				System.out.println(ga.isConnected());
			}
		});
		//action on the button shortestPath that show the short path in black color
		StdDraw.spButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				drowgraph(g);
				System.out.println("input src:");
				Scanner sc1=new Scanner(System.in);
				System.out.println("input dest:");
				Scanner sc2=new Scanner(System.in);
				int src=sc1.nextInt();
				int dest=sc2.nextInt();
				Collection<node_data> sp=ga.shortestPath(src,dest);
				Collection<node_data> sp1=ga.shortestPath(src,dest);
				Iterator< node_data> it=sp.iterator();
				Iterator<node_data>it2=sp.iterator();
				while(it2.hasNext()&&it.hasNext()) {
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.setPenRadius(0.005);
					it.hasNext();
					node_data ns=it.next();
					node_data nd=it2.next();
					if(ns.equals(nd)) {
						it2.hasNext();
						nd=it2.next();
					}
					double xs=ns.getLocation().x();
					double ys=ns.getLocation().y();
					double xd=nd.getLocation().x();
					double yd=nd.getLocation().y();
					StdDraw.line(xs, ys, xd, yd);
					StdDraw.setPenRadius(0.01);
					StdDraw.setPenColor(Color.ORANGE);
					StdDraw.point((0.6*xs+9.4*xd)/10, (0.6*ys+9.4*yd)/10);
				}

				System.out.println(sp);
			}

		});


		//drow point of node_location
		Collection<node_data> v =g.getV();
		Iterator<node_data> it=v.iterator();
		StdDraw.setPenRadius(0.015);
		StdDraw.setPenColor(Color.BLACK);
		while (it.hasNext()) {
			node_data node=it.next();
			double x=node.getLocation().x();
			double y=node.getLocation().y();
			StdDraw.point(x,y);
			StdDraw.textLeft(x+0.5, y+0.5, node.getKey()+"");
		}
		drowLine(Color.BLUE, g);
	}

	private static void drowLine(Color color,graph g) {
		//drow line of edge , weight of edge and the direction of the edge 
		Collection<node_data> v =g.getV();
		Iterator<node_data> it=v.iterator();
		it=v.iterator();
		while (it.hasNext()) {
			Collection<edge_data> e =g.getE(it.next().getKey());
			if(e!=null) {
				Iterator<edge_data> it2=e.iterator();
				while (it2.hasNext()) {
					edge_data edge=it2.next();
					StdDraw.setPenRadius(0.0005);
					StdDraw.setPenColor(color);
					double x1=g.getNode(edge.getSrc()).getLocation().x();
					double x2=g.getNode(edge.getDest()).getLocation().x();
					double y1=g.getNode(edge.getSrc()).getLocation().y();
					double y2=g.getNode(edge.getDest()).getLocation().y();
					StdDraw.line(x1, y1, x2, y2);
					StdDraw.text((x1+x2)/2, (y1+y2)/2, edge.getWeight()+"");
					StdDraw.setPenRadius(0.01);
					StdDraw.setPenColor(Color.ORANGE);
					StdDraw.point((0.6*x1+9.4*x2)/10, (0.6*y1+9.4*y2)/10);
				}
			}
		}
	}

	//update the range
	private static void setCanvas(graph g) {	
		double xmin=Double.MAX_VALUE;
		double xmax=Double.MIN_VALUE;
		double ymin=Double.MAX_VALUE;
		double ymax=Double.MIN_VALUE;

		Collection<node_data> v =g.getV();
		Iterator<node_data> it=v.iterator();
		while(it.hasNext()) {
			node_data node=it.next();
			if(node.getLocation().x()<xmin) xmin=node.getLocation().x();
			if(node.getLocation().x()>xmax) xmax=node.getLocation().x();
			if(node.getLocation().y()<ymin) ymin=node.getLocation().y();
			if(node.getLocation().y()>xmax) ymax=node.getLocation().y();
		}
		StdDraw.setXscale(xmin-10,xmax+10);
		StdDraw.setYscale(ymin-10,ymax+10);
	}

}