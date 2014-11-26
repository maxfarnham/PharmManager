package dataLayer;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataLayer {
	
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
	
	private String dbPath = "";
	private Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
	    return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	}
	
	//Create tables to test
	public void createTwoTables(){
		 try{
	      String sql = 	"CREATE TABLE Medications " +
	                   	"(MedicineID 			INT PRIMARY KEY     NOT NULL," +
	                   	" Name    		      	VARCHAR				NOT NULL, " + 
	                   	" LowStockThreshold		INT     			NOT NULL, " + 
	                   	" OverstockThreshold	INT					NOT NULL)";
	      executeNonQuery(sql);
	    
	      sql = 		"CREATE TABLE Shipments " +
                 		"(ShipmentID 	INT PRIMARY KEY     NOT NULL," +
                 		" Sold          INT	    			NOT NULL, " + 
                 		" InStock		INT     			NOT NULL, " + 
                 		" Size			INT					NOT NULL, " +
	                   	" Expired		INT					NOT NULL, " +
                 		" ExpDate		INT					NOT NULL, " +
	                   	" MedicineID	INT					NOT NULL)";
	      executeNonQuery(sql);
		 }
		 catch(Exception e){
		 }
		 
	 }
	
	 // just verify we can establish a connection with the given database
	 public DataLayer(String dbPath) throws ClassNotFoundException, SQLException{
		 if (!dbPath.endsWith(".db"))
			 dbPath += ".db";
		 this.dbPath = dbPath;
		 getConn().close(); 
		 createTwoTables(); //testing...
	 }
	 
	 public int executeNonQuery(String sql) throws ClassNotFoundException, SQLException{   // non-query
		 Connection c = getConn();
		 Statement stmt = c.createStatement();
		 
		 int ret = stmt.executeUpdate(sql);		    
		 stmt.close();
		 c.close();
		 return ret;
	 }
	 
	 public Object executeScalar(String sql) throws ClassNotFoundException, SQLException{   // scalar query
		 Connection c = getConn();
		 Statement stmt = c.createStatement();
		 
		 Object result;
		 ResultSet rs = stmt.executeQuery(sql);
		 result = rs.getObject(1);
		 rs.close();
		 stmt.close();
		 c.close();
		    
		 return result;
	 }
	 
	 public ArrayList<Map<String, Object>> executeQuery(String sql) throws ClassNotFoundException, SQLException{   // Query
		 Connection c = getConn();
		 Statement stmt = c.createStatement();

		 int cols, i;
		 ResultSetMetaData meta;
		 ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		 	
		 	
		 ResultSet rs = stmt.executeQuery(sql);
		 meta = rs.getMetaData();
		 cols = meta.getColumnCount();
		 
		 while(rs.next()){
		 	Map<String, Object> dict = new HashMap<String, Object>();
		 	for(i = 1; i <= cols; i++){
		 		dict.put(meta.getColumnName(i), rs.getObject(i));		 		
		 	}
		 	resultList.add(dict);
		 }
		 	
		 rs.close();
		 stmt.close();
		 c.close();
		    
		 return resultList;
	 }
	 
	 
}
