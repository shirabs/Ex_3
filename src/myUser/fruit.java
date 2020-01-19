package myUser;

import java.util.ArrayList;
import Server.fruits;
import oop_utils.OOP_Point3D;

public class fruit {
	
	private double value;
	private enum type {banana , apple};
	private OOP_Point3D pos;
	private type fruit; 
	
	public fruit(double v,OOP_Point3D p,int t) {
		this.value=v;
		this.pos=p;
		if(t==1) {
			fruit=type.apple;
		}
		else if(t==-1) {
			fruit=type.banana;
		}
		else {
			fruit=null;
		}

	}

	public double getValue() {
		return this.value;
	}

	public type getType() {
		return this.fruit;
	}

	public OOP_Point3D getPos() {
		return this.pos;
	}

}


