package dataLayer;
import java.sql.*;

public class commandHandler {
	
	/*  // exp date perhaps?...I'll put it in later -j
	*--------------------------medicine--------------------------
	*|  medicineID  |  name  |  lowThreshold  |  overThreshold  |  
	*|----------------------------------------------------------|
	*|     Int      |varchar |      Int       |       Int       |
	*|----------------------------------------------------------|
	*|      0       |'asprin'|       3        |       300       |
	*|----------------------------------------------------------|
	*|     ...      |  ...   |      ...       |       ...       |
	*------------------------------------------------------------
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
		                   	" overThreshold	INT					NOT NULL)";
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
		      System.exit(0);		     
		    }
		    System.out.println("Opened database successfully");	 
	 }

}
