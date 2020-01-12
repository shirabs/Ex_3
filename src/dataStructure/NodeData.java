package dataStructure;

import utils.Point3D;

public class NodeData implements node_data {
	private String type="NodeData";
	private Point3D location;
	private int key;
	private double weight;
	private int tag;
	private String info;
	public static int id=1;

	public NodeData() {
		this.location=Point3D.ORIGIN;
		this.key=id;
		this.id++;
		this.weight= Double.MAX_VALUE;
		this.tag=0;
		this.info="";
	}

	public NodeData(Point3D locat) {
		this.location=locat;
		this.key=id;
		this.id++;
		this.weight= Double.MAX_VALUE;
		this.tag=0;
		this.info="";

	}


	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public Point3D getLocation() {
		return this.location;
	}

	@Override
	public void setLocation(Point3D p) {
		this.location=new Point3D(p.x(), p.y(), p.z());
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(double w) {
		this.weight=w;
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
		tag=t;

	}

	public String toString() {
		return "key ="+ this.key+ " tag ="+this.tag+ "location ="+this.location+ "wegith ="+this.weight;
	}
}
