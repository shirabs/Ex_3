package dataStructure;

import java.util.Collection;
import java.util.HashMap;


public class DGraph implements graph{

	private String type="DGraph";
	private HashMap<Integer,node_data> DataMap;
	private HashMap<Integer, HashMap<Integer, edge_data>> EdgeMap;
	private int countEdge=0;
	private int mc;

	public DGraph() {
		this.DataMap=new HashMap<Integer, node_data>();
		this.EdgeMap=new HashMap<Integer, HashMap<Integer, edge_data>>();
		this.countEdge=0;
		this.mc=0;
	}
	

	@Override
	public node_data getNode(int key) {
		if(this.DataMap.get(key)==null) {
			return null;
		}
		return this.DataMap.get(key);
	}


	@Override
	public edge_data getEdge(int src, int dest) {
		if(this.EdgeMap.containsKey(src))
			if(this.EdgeMap.get(src).get(dest)!=null) {
				return this.EdgeMap.get(src).get(dest);
			}
		return null;
	}


	@Override
	public void addNode(node_data n) {
		int key=n.getKey();
		DataMap.put(key, (NodeData) n);
		mc++;
	}


	@Override
	public void connect(int src, int dest, double w) {
		if(this.DataMap.get(src)==null||this.DataMap.get(dest)==null) {
			System.out.println("connect faild");
		}
		else {
			EdgeData x=new EdgeData(src, dest, w);
			if(this.EdgeMap.get(src)==null) {
				this.EdgeMap.put(src,new HashMap<Integer,edge_data>());
				this.EdgeMap.get(src).put(dest, x);
				countEdge++;
				mc++;
			}
			else {

				if(!(EdgeMap.get(src).containsKey(dest))) {
					this.EdgeMap.get(src).put(dest, x);
					countEdge++;
					mc++;
				}
			}
		}
	}

	@Override
	public Collection<node_data> getV() {

		return this.DataMap.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {

		if(EdgeMap.get(node_id)==null)
			return  null;

		HashMap<Integer, edge_data> t= this.EdgeMap.remove(node_id);
		this.EdgeMap.put(node_id, t);
		return t.values();
	}

	@Override
	public node_data removeNode(int key) {

		if(DataMap.containsKey(key)) {
			//delete from DataMap
			node_data t= this.DataMap.remove(key);
			//delete the edge that out from the node key
			if( EdgeMap.containsKey(key)) {
				Collection<edge_data> rem=this.EdgeMap.remove(key).values();
				countEdge-=rem.size();
			}
			//delete the edge that enter to the node key
			for(int i=0;i<=NodeData.id;i++) {
				if(EdgeMap.containsKey(i)&&EdgeMap.get(i).containsKey(key)) {
					removeEdge(i, key);
					mc--;
				}
			}
			mc++;
			return t;
		}
		return null;

	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		if(this.EdgeMap.get(src).get(dest)==null) {
			return null;
		}
		mc++;
		countEdge--;
		return this.EdgeMap.get(src).remove(dest);
	}


	@Override
	public int nodeSize() {

		return DataMap.size() ;
	}

	@Override
	public int edgeSize() {
		return this.countEdge;
	}

	@Override
	public int getMC() {
		return mc;
	}
	public String toString() {
		
		return  "the countedge= "+countEdge+"\n"+"   "+this.EdgeMap+"\n"+ "size Nodedata= "+this.getV().size();

	}

}
