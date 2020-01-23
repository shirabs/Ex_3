package gameClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class read_DB {

	public static String printLog(int id) {
		StringBuilder sb=new StringBuilder();
		sb.append("player:  "+id +"\n");
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection connection = 
					DriverManager.getConnection(SimpleDB.jdbcUrl, SimpleDB.jdbcUser, SimpleDB.jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs where userID="+id;

			int [] level= {0,1,3,5,9,11,13,16,19,20,23};
			int ind =0;
			int cl=0;
			for (int i = 0; i < level.length; i++) {
				ind=0;
				int maxscore=Integer.MIN_VALUE;
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);
				while(resultSet.next()){
					ind++;
					if(resultSet.getInt("levelID")==level[i]) {
						if(resultSet.getInt("score")>maxscore) {
							maxscore=resultSet.getInt("score");
						}
					}
					else if(resultSet.isLast()&&maxscore!=Integer.MIN_VALUE){
						sb.append("level: "+level[i]+"  the big grade:"+maxscore+"\n");
						cl=level[i];
					}
					if(i==level.length-1&&resultSet.isLast()) {
						sb.append("the current level:  "+cl+"\n");
					}	
				}
				resultSet.close();
			}
			sb.append("num of game in the server:  " +ind);
			statement.close();		
			connection.close();	
		}	

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	public static String ToughStages(int id){
		StringBuilder str=new StringBuilder();
		str.append("Place id: "+id+" in the class").append("\n");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(SimpleDB.jdbcUrl, SimpleDB.jdbcUser, SimpleDB.jdbcUserPassword);
			Statement statement = connection.createStatement();
			ResultSet resultSet = null;
			int [] levellst= {0,1,3,5,9,11,13,16,19,20,23};
			for(int level:levellst) {
				String allCustomersQuery = "SELECT * FROM Logs where levelID=" + level;
				resultSet = statement.executeQuery(allCustomersQuery);
				int ind = 0;
				boolean not_my_id=true;
				while (resultSet.next()&&not_my_id) {
					ind++;
					if(resultSet.getInt("userID")==id){
						not_my_id=false;
					}
				}
				str.append(level).append(") ").append(ind).append("\n");
			}
			assert resultSet != null;
			resultSet.close();
			statement.close();
			connection.close();
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return str.toString();
	}
}
