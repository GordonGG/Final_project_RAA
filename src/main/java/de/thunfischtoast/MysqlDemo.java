package de.thunfischtoast;
import java.sql.*; 
/**
 * 
 * @author Gao Tianhong
 * Database operations
 *
 */
public class MysqlDemo { 
	Connection con; 
	public Connection getConnection() { 
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Successful no¡£1");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try { 
			con = DriverManager.getConnection("jdbc:mysql:" + "//127.0.0.1:3306/project?"
					+ "useUnicode = true£¦characterEncoding = utf-8£¦useSSL = false&serverTimezone = GMT", "root", "123456");
			System.out.println("Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con; 
	}
    public void AddReward(int t,double reward1,double reward2,double reward3){ 
    	Statement stt;
    	ResultSet res;
    	MysqlDemo c = new MysqlDemo();
    	con = c.getConnection();
        try {
        	String sql = "insert into reward values( "+t+" , "+reward1+" , "+reward2+","+reward3+" ) ;";
        	stt = con.createStatement();
        	stt.executeUpdate(sql);
        	con.close();
        }catch(Exception e){
        	e.printStackTrace();
        }
        
    }
    public void AddVehicle(int t,int vehicleID,double context1, double context2,int context3){
    	Statement stt;
    	ResultSet res;
    	MysqlDemo c = new MysqlDemo();
    	con = c.getConnection();
        try {
        	String sql = "insert into vehicle values( "+t+" , "+vehicleID+" , "+context1+","+context2+","+context3+" ) ;";
        	stt = con.createStatement();
        	stt.executeUpdate(sql);
        	con.close();
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
    

}
