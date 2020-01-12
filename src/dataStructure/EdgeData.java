package dataStructure;

import java.awt.Color;

public class EdgeData implements edge_data{

	private String type="EdgeData";
	private int src;
	private int dest;
	private String info;
	private double weight;
	private int tag;
	
//	public static Color[]color= {Color.WHITE,Color.GRAY,Color.BLACK};


	public EdgeData(int start,int end,double w) {

		this.src=start;
		this.dest=end;
		this.weight=w;
		this.info="";
		this.tag=0;

	}
	public EdgeData(EdgeData e) {

		this.src=e.src;
		this.dest=e.dest;
		this.weight=e.weight;
		this.info="";
		this.tag=0;

	}
	public EdgeData(int start,int end,double w,String s) {

		this.src=start;
		this.dest=end;
		this.weight=w;
		this.info=s;
		this.tag=0;

	}


	@Override
	public int getSrc() {

		return this.src;
	}

	@Override
	public int getDest() {

		return this.dest;
	}

	@Override
	public double getWeight() {

		return this.weight;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info=s;
	}

	@Override
	public int getTag() {
		return tag;
	}

	@Override
	public void setTag(int t) {
		this.tag=t;

	}
	public String toString() {
		return "  src="+this.src+ "----->  dest ="+this.dest+"   weight="+this.weight;
	}
}
