package utils;

import java.util.Iterator;
import java.util.LinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import oop_dataStructure.*;
import oop_elements.OOP_Edge;
import oop_elements.OOP_NodeData;
import java.util.ArrayList;
import java.util.Stack;


/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms{
	private oop_graph g;

	public Graph_Algo() {
		g=new OOP_DGraph();
	}

	public Graph_Algo(oop_graph g) {
		init(g);
	}

	@Override
	public void init(oop_graph g) {
		this.g=g;
	}

	@Override
	//init graph from file
	public void init(String file_name) {
		try 
		{
			FileReader reader = new FileReader(file_name);

			final TypeToken<oop_graph> requestListTypeToken = new TypeToken<oop_graph>() {};
			final RuntimeTypeAdapterFactory<oop_graph> graphTypeFactory = RuntimeTypeAdapterFactory
					.of(oop_graph.class, "type") 
					.registerSubtype(OOP_DGraph.class);

			final RuntimeTypeAdapterFactory<oop_node_data> nodeTypeFactory = RuntimeTypeAdapterFactory
					.of(oop_node_data.class, "type")
					.registerSubtype(OOP_NodeData.class);

			final RuntimeTypeAdapterFactory<oop_edge_data> edgeTypeFactory = RuntimeTypeAdapterFactory
					.of(oop_edge_data.class, "type")
					.registerSubtype(OOP_Edge.class);


			final GsonBuilder gson2 = new GsonBuilder();			
			final oop_graph result2 = gson2
					.registerTypeAdapterFactory(graphTypeFactory)
					.registerTypeAdapterFactory(nodeTypeFactory)
					.registerTypeAdapterFactory(edgeTypeFactory)
					.create()
					.fromJson(reader,requestListTypeToken.getType());
			this.g= result2;
			//			System.out.println(result2);

		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}

	@Override
	//save graph to file
	public void save(String file_name) {
		GsonBuilder gson= new GsonBuilder();
		String wj= gson.create().toJson(g);
		//		System.out.println(wj);
		try {
			PrintWriter pw= new PrintWriter(new File(file_name));
			pw.write(wj);
			pw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public boolean isConnected() {
		if(g.edgeSize()==0)
			return true;
		boolean a=connect(g);
		boolean b=Reversegraph(g);
		if(a==true&&b==true) {
			return true;
		}
		return false;
	}

	//check if the graph connect
	private boolean connect(oop_graph g) {
		oop_graph temp=this.copy();
		int counterPoint=1;
		int counterPoint2=temp.getV().size();
		boolean flag=true;
		if(counterPoint2==1) {
			return true;
		}
		Stack<Integer> point=new Stack<Integer>();
		Collection<oop_node_data> node=	temp.getV();
		Iterator<oop_node_data> it=node.iterator();
		int run=it.next().getKey();
		while(flag){
			Collection<oop_edge_data> edge=temp.getE(run);
			if(edge==null)
				break;
			Iterator<oop_edge_data> it2=edge.iterator();
			while(it2.hasNext()) {
				oop_edge_data t=it2.next();
				if(!point.contains(t.getDest())) {
					point.push(t.getDest());
					counterPoint++;
				}
			}
			temp.removeNode(run);

			if(!point.isEmpty()) {
				run=point.pop();
			}
			else {
				flag=false;			
			}
		}
		if((counterPoint-counterPoint2)==0) {
			return true;
		}
		return false;
	}

	//revers the graph 
	private boolean Reversegraph(oop_graph g) {
		OOP_DGraph temp=new OOP_DGraph();
		Collection<oop_node_data> node=	g.getV();
		Iterator<oop_node_data> it=node.iterator();
		while(it.hasNext()){
			temp.addNode(it.next());
		}
		it=node.iterator();

		while(it.hasNext()){
			Collection<oop_edge_data> edge=g.getE(it.next().getKey());
			if(edge==null)
				break;
			Iterator<oop_edge_data> it2=edge.iterator();
			while(it2.hasNext()) {
				oop_edge_data t=it2.next();
				temp.connect(t.getDest(), t.getSrc(), t.getWeight());
			}
		}
		return connect(temp);
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		try {
			List<oop_node_data> sp=shortestPath(src, dest);
			Iterator< oop_node_data> it=sp.iterator();
			double ans=0;
			while(it.hasNext()) {
				ans=it.next().getWeight();
			}
			return ans;
		}
		catch (Exception e) {
			throw new RuntimeException("not have src or dest");
		}
	}

	@Override
	public List<oop_node_data> shortestPath(int src, int dest) {
		//check that have in the graph the node src and drst
		if(!g.getV().contains(g.getNode(src))||!g.getV().contains(g.getNode(dest)))
			throw new RuntimeException("not have src or dest");
		upnode_max_and_notvisit_and_info();
		g.getNode(src).setWeight(0);
		while(!visitall()) {
			int s=smallest_node();
			oop_node_data ts = g.getNode(s);
			ts.setTag(1);
			Collection<oop_edge_data> edges= g.getE(s);
			if(edges!=null) {
				Iterator<oop_edge_data> it = edges.iterator();
				while(it.hasNext()) {
					oop_edge_data tedge = it.next();
					if(ts.getWeight()+tedge.getWeight()<=g.getNode(tedge.getDest()).getWeight()) {
						g.getNode(tedge.getDest()).setWeight(ts.getWeight()+tedge.getWeight());
						g.getNode(tedge.getDest()).setInfo(tedge.getSrc()+"");
					}
				}
			}
		}
		List<oop_node_data> sp = new ArrayList<oop_node_data>();
		sp.add(g.getNode(dest));
		oop_node_data t = g.getNode(dest);
		while(t.getKey()!=src) {

			try {
				int b=Integer.parseInt(t.getInfo());
				List<oop_node_data> tsp = new ArrayList<oop_node_data>();
				tsp.add(g.getNode(b));
				tsp.addAll(sp);
				sp=tsp;
				t=g.getNode(b);
			}
			catch (Exception e) {
				throw new RuntimeException("not conected");
			}
		}

		return sp;
	}

	@Override
	public List<oop_node_data> TSP(List<Integer> targets) {
		if(isConnected()==false) {
			throw new RuntimeException("this graph not isConnected");
		}
		List<Integer>tar=new ArrayList<Integer>(targets);
		Iterator<Integer> it=tar.iterator();
		int size=g.getV().size();
		//check all the node with the key from the targets in the graph
		while(it.hasNext()) {
			if(it.next()>size) {
				throw new RuntimeException("The point is not in the graph");
			}
		}
		it=tar.iterator();
		List<oop_node_data> temp=new ArrayList<oop_node_data>();
		//-------------------------------------------->good	
		int end=0;
		int start=it.next();
		temp.add(g.getNode(start));
		while(it.hasNext()) {
			if(end!=0) {
				start=end;
				end=it.next();
				List<oop_node_data> run=shortestPath(start,end) ;
				Iterator<oop_node_data> it2=run.iterator();

				while(it2.hasNext()) {
					oop_node_data node=it2.next();
					if(tar.contains(node.getKey()))
						tar.remove(tar.indexOf(node.getKey()));
				}
				run.remove(0);
				temp.addAll(run);
				it=tar.iterator();
//				System.out.println(tar);
			}
			else {
				end=start;
			}
		}

		return temp;
	}

	@Override
	public oop_graph copy() {
		oop_graph c= new OOP_DGraph();
		Collection<oop_node_data> node=	g.getV();
		Iterator<oop_node_data> it=node.iterator();
		while(it.hasNext()){
			c.addNode(it.next());
		}
		it=node.iterator();
		while(it.hasNext()){
			Collection<oop_edge_data> edge=g.getE(it.next().getKey());
			if(edge==null)
				break;

			Iterator<oop_edge_data> it2=edge.iterator();
			while(it2.hasNext()) {
				oop_edge_data t=it2.next();
				c.connect(t.getSrc(), t.getDest(), t.getWeight());
			}
		}
		return c;
	}

	//update the info and visit and max_value in all nodes in the graph
	private void upnode_max_and_notvisit_and_info() { 
		Collection<oop_node_data> col = g.getV();
		Iterator<oop_node_data> it = col.iterator();
		while(it.hasNext()) {
			oop_node_data t=it.next();
			t.setTag(0);
			t.setInfo("");
			t.setWeight(Double.MAX_VALUE);

		}
	}

	//found the node with the smalle weight that dont visit
	private int smallest_node() { 
		Collection<oop_node_data> col = g.getV();
		Iterator<oop_node_data> it = col.iterator();
		oop_node_data ans=it.next();
		it=col.iterator();

		while (it.hasNext()) {
			oop_node_data temp= it.next();
			if(temp.getTag()==0) {
				ans=temp;
				break;
			}
		}
		while(it.hasNext()) {
			oop_node_data t = it.next();
			if(t.getWeight()<ans.getWeight()&&t.getTag()==0) {
				ans=t;
			}
		}
		return ans.getKey();
	}

	//check if visit in all nodes on the graph
	private boolean visitall() { 
		Collection<oop_node_data> col = g.getV();
		Iterator<oop_node_data> it = col.iterator();
		while(it.hasNext()) {
			if(it.next().getTag()==0) {
				return false;
			}
		}
		return true;
	}


	public String toString() {
		return "mygraph"+ this.g;
	}
	
	
}