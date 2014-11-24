package dataLayer;
import java.sql.*;
import java.util.ArrayList;

//import businessLayer.Medicine;

public class commandHandler {
	
	/*  // exp date perhaps?...I'll put it in later -j
	*--------------------------medicine------------------------------------
	*|  medicineID  |  name  |  lowThreshold  |  overThreshold  |  size   |
	*|----------------------------------------------------------|---------|
	*|     Int      |varchar |      Int       |       Int       | varChar |
	*|----------------------------------------------------------|---------|
	*|      0       |'asprin'|       3        |       300       | 'small' |
	*|----------------------------------------------------------|---------|
	*|     ...      |  ...   |      ...       |       ...       |   ...   |
	*----------------------------------------------------------------------
	*/
	
	 // create is called once at the beginning to create database + tables
	 public static void create(){
		 Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:pharmacy.db");
		      
		      stmt = c.createStatement();
		      String sql = 	"CREATE TABLE medicine " +
		                   	"(medicineID 	INT PRIMARY KEY     NOT NULL," +
		                   	" name          VARCHAR				NOT NULL, " + 
		                   	" lowThreshold	INT     			NOT NULL, " + 
		                   	" overThreshold	INT					NOT NULL, " +
		                   	" size			VARCHAR    			NOT NULL)";
		      stmt.executeUpdate(sql);
		    /* If another table is required in database, use this
		      sql = 		"CREATE TABLE medicine " +
	                   		"(medicineID 	INT PRIMARY KEY     NOT NULL," +
	                   		" name          VARCHAR    NOT NULL, " + 
	                   		" lowThreshold	INT     NOT NULL, " + 
	                   		" overThreshold	INT		NOT NULL)";
		      stmt.executeUpdate(sql);
	      */
		      stmt.close();
		      c.close();
		      
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      //System.exit(0);		     
		    }
		    System.out.println("Opened database successfully");	 
	 }
	 public static int executeNonQuery(String sql){   // non-query
		 Connection c = null;
		 Statement stmt = null;
		 try {
			Class.forName("org.sqlite.JDBC");
		 	c = DriverManager.getConnection("jdbc:sqlite:pharmacy.db");
		      
		 	stmt = c.createStatement();
		 	stmt.executeUpdate(sql);		    
		    stmt.close();
		    c.close();
		    return 1;
		      
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      return 0;		     
		    } 
	 }
	 public static Object executeScalar(String sql){   // scalar
		 Connection c = null;
		 Statement stmt = null;		 
		 try {
			Class.forName("org.sqlite.JDBC");
		 	c = DriverManager.getConnection("jdbc:sqlite:pharmacy.db");
		    Object result;
		 	stmt = c.createStatement();
		 	ResultSet rs = stmt.executeQuery(sql);
		 	result = rs.getObject(1);
		 	rs.close();
		    stmt.close();
		    c.close();
		    
		    return result;
		      
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      return null;		     
		    } 
	 }
	 public static ArrayList<ArrayList<Object>> executeNonScalar(String sql){   // Non scalar
		 Connection c = null;
		 Statement stmt = null;		 
		 try {
			Class.forName("org.sqlite.JDBC");
		 	c = DriverManager.getConnection("jdbc:sqlite:pharmacy.db");
		    //Object result;
		    int cols, i;
		 	stmt = c.createStatement();
		 	ResultSetMetaData meta;
		 	ArrayList<ArrayList<Object>> resultList = new ArrayList<ArrayList<Object>>();
		 	
		 	
		 	ResultSet rs = stmt.executeQuery(sql);
		 	meta = rs.getMetaData();
		 	cols = meta.getColumnCount();
		 	while(rs.next()){
		 		ArrayList<Object> subList = new ArrayList<Object>();
		 		for(i = 1; i <= cols; i++){
		 			subList.add(rs.getObject(i));		 		
		 		}
		 		resultList.add(subList);
		 	}
		 	
		 	rs.close();
		    stmt.close();
		    c.close();
		    
		    return resultList;
		      
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      return null;		     
		    } 
	 }
}
