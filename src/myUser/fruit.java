package myUser;

import java.util.ArrayList;
import Server.fruits;
import oop_utils.OOP_Point3D;

public class fruit {
	
	private double value;
	private int type ;
	private OOP_Point3D pos;
	 
	
	public fruit(double v,OOP_Point3D p,int t) {
		this.value=v;
		this.pos=p;
		this.type=t;
	}

	public double getValue() {
		return this.value;
	}

	public int getType() {
		return this.type;
	}

	public OOP_Point3D getPos() {
		return this.pos;
	}
	

}


