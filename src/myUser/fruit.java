package myUser;

import java.util.ArrayList;

import org.json.JSONObject;

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

	public fruit(String s) {
		if(!s.isEmpty()){
			try{
				JSONObject obj = new JSONObject(s);
				JSONObject fruit = (JSONObject) obj.get("Fruit");
				this.value = fruit.getInt("value");
				this.type = fruit.getInt("type");
				String pos = fruit.getString("pos");
				String[] point = pos.split(",");
				double x = Double.parseDouble(point[0]);
				double y = Double.parseDouble(point[1]);
				double z = Double.parseDouble(point[2]);
				this.pos = new OOP_Point3D(x, y, z);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
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


