package myUser;

import oop_utils.OOP_Point3D;

public class robot {

	private  static int id=0;
	private double value;
	private int src,dest;
	private final int speed=1; 
	private OOP_Point3D pos;
	
	
	public robot(OOP_Point3D p,int s,int d,double v) {
		this.id=id;
		id++;
		this.pos=p;
		this.src=s;
		this.dest=d;
		this.value=v;
		
	}
	
	public double getValue() {
		return this.value;
	}
	
	public int getId() {
		return this.id;
	}
	
	public OOP_Point3D getPos() {
		return this.pos;
	}
	
	public int getSrc() {
		return this.src;
	}
	
	public int getDest() {
		return this.dest;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setSrc(int a) {
		this.src=a;
	}
	
	public void setDest(int a) {
		this.dest=a;
	}
	
	public void setValue(int a) {
		this.value=a;
	}
	
	public void setPos(double x,double y) {
		this.pos= new OOP_Point3D(x, y, 0);
	}
}
