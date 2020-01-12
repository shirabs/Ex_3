package gameClient;

import java.util.ArrayList;

import Server.fruits;
import utils.Point3D;


public class Fruits {

	private double value;
	private enum type {banana , apple};
	private Point3D pos;
	private type fruit; 
	
	public Fruits(double v,Point3D p,int t) {
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

	public Point3D getPos() {
		return this.pos;
	}











}


