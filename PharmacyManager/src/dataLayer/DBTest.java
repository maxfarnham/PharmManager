package dataLayer;
import java.sql.*;
import java.util.ArrayList;

// SQL Command execution examples
public class DBTest {
	 public static void main( String args[] )
	  {
		 create(); // create and set up database if one doesn't exist
				 
		 // inserting/executeNonQuery
		 executeNonQuery("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                 "VALUES (1, 'Paul', 32, 'California', 20000.00 );");
		 executeNonQuery("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
				 "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );");
		 executeNonQuery("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
				 "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );");
		 executeNonQuery("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
				 "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );");		 
		 
		 // printing scalar directly
		 System.out.print("Max Salary (direct): ");
		 System.out.println(executeScalar("SELECT MAX(SALARY) FROM COMPANY"));
		 
		 // storing values from scalar query into values
		 double salary = (double)executeScalar("SELECT MAX(SALARY) FROM COMPANY");
		 System.out.println("Max salary (stored): " + salary);
		 
		 // printing non-scalar query directly
		 System.out.println(executeNonScalar("SELECT NAME, AGE FROM COMPANY"));
		 
		 // storing values from non-scalar query into values
		 ArrayList<ArrayList<Object>> testArrayList = executeNonScalar("SELECT NAME, AGE FROM COMPANY");
		 int arraySize = testArrayList.size();
		 System.out.println("using the array list gets");
		 int i = 0;
		 for(i = 0; i < arraySize; i++){
			 String name = (String)testArrayList.get(i).get(0);
			 int age = (int)testArrayList.get(i).get(1);
			 System.out.println(name + " " + age);
			 // if we wanted to print everything
			 // int j;
			 // for(j = 0; j < testArrayList.get(i).size(); j++){
			 //	 System.out.print(testArrayList.get(i).get(j) + " ");
			 //}
			 //System.out.print("\n");
			 
		 }
	  }
///////////////////////////////////////////////////////////////////////////////////////////////////////////	
	 // Create a table
	 public static void create(){
		 Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      
		      stmt = c.createStatement();
		      String sql = "CREATE TABLE COMPANY " +
		                   "(ID INT PRIMARY KEY     NOT NULL," +
		                   " NAME           VARCHAR    NOT NULL, " + 
		                   " AGE            INT     NOT NULL, " + 
		                   " ADDRESS        CHAR(50), " + 
		                   " SALARY         REAL)"; 
		      stmt.executeUpdate(sql);
		      stmt.close();
		      c.close();
		      
		    } catch ( Exception e ) {
		     // System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		     // System.exit(0);
		    }
		    System.out.println("Opened database successfully");	 
	 }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////	 
	 // non-query (delete, insert) - returns 1 if success, 0 if fail
	 public static int executeNonQuery(String sql){   // non-query
		 Connection c = null;
		 Statement stmt = null;
		 try {
			Class.forName("org.sqlite.JDBC");
		 	c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      
		 	stmt = c.createStatement();
		 	stmt.executeUpdate(sql);		    
		    stmt.close();
		    c.close();
		    return 1;		      
		    } catch ( Exception e ) {
		      //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      return 0;		     
		    } 
	 }
	 // scalar (max, count) - returns null if fail
	 public static Object executeScalar(String sql){   // scalar
		 Connection c = null;
		 Statement stmt = null;
		 
		 try {
			Class.forName("org.sqlite.JDBC");
		 	c = DriverManager.getConnection("jdbc:sqlite:test.db");
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
	 // non-scalar (more complex lists) - returns null if fail
	 public static ArrayList<ArrayList<Object>> executeNonScalar(String sql){   // Non scalar
		 Connection c = null;
		 Statement stmt = null;		 
		 try {
			Class.forName("org.sqlite.JDBC");
		 	c = DriverManager.getConnection("jdbc:sqlite:test.db");
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
/////// OBSOLETE CODE BELOW///////////////////////////////////////////////////////////////////
	 // Insert 
	 /*public static void insert(){
		 Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
		                   "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
		      stmt.executeUpdate(sql);

		      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
		            "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );"; 
		      stmt.executeUpdate(sql);

		      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
		            "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );"; 
		      stmt.executeUpdate(sql);

		      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
		            "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"; 
		      stmt.executeUpdate(sql);

		      stmt.close();
		      c.commit();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      //System.exit(0);
		    }
		    System.out.println("Records created successfully");
		 
	 }*/
	 // Select
	 /*public static ArrayList<TestClass> select(){
		 //TestClass temp = new TestClass();
		 Connection c = null;
		    Statement stmt = null;
		    ArrayList<TestClass> returnList = new ArrayList<TestClass>();
		    //ArrayList<HashMap<String, Object>> returnList = new ArrayList<HashMap<String, Object>>();
		    
		    //HashMap<String, Object> tempHash = new HashMap<String, Object>();
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
		      while ( rs.next() ) {
		    	  TestClass temp = new TestClass();
		         int id = rs.getInt("id");
		         String  name = rs.getString("name");
		         int age  = rs.getInt("age");
		         String  address = rs.getString("address");
		         float salary = rs.getFloat("salary");
		         
		         //System.out.println( "ID = " + id );
		         //System.out.println( "NAME = " + name );
		         //System.out.println( "AGE = " + age );
		         //System.out.println( "ADDRESS = " + address );
		         //System.out.println( "SALARY = " + salary );
		         //System.out.println();
		         
		         temp.setId(id);
		         temp.setName(name);
		         temp.setAge(age);
		         temp.setAddress(address);
		         temp.setSalary(salary);
		         returnList.add(temp);
		         
		         //tempHash.put("ID", id);
		         //tempHash.put("NAME", name);
		         //tempHash.put("AGE", age);
		         //tempHash.put("ADDRESS", address);
		         //tempHash.put("SALARY", salary);
		         //returnList.add(tempHash);
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Operation done successfully");
		    return returnList;
	 }*/
	 // Update
	 /*public static  void update(){
		 Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
		      stmt.executeUpdate(sql);
		      c.commit();

		      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
		      while ( rs.next() ) {
		         int id = rs.getInt("id");
		         String  name = rs.getString("name");
		         int age  = rs.getInt("age");
		         String  address = rs.getString("address");
		         float salary = rs.getFloat("salary");
		         System.out.println( "ID = " + id );
		         System.out.println( "NAME = " + name );
		         System.out.println( "AGE = " + age );
		         System.out.println( "ADDRESS = " + address );
		         System.out.println( "SALARY = " + salary );
		         System.out.println();
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Operation done successfully");	 
	 }*/
	 /*public static void delete()
	  {
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "DELETE from COMPANY where ID=2;";
	      stmt.executeUpdate(sql);
	      c.commit();

	      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
	      while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         int age  = rs.getInt("age");
	         String  address = rs.getString("address");
	         float salary = rs.getFloat("salary");
	         System.out.println( "ID = " + id );
	         System.out.println( "NAME = " + name );
	         System.out.println( "AGE = " + age );
	         System.out.println( "ADDRESS = " + address );
	         System.out.println( "SALARY = " + salary );
	         System.out.println();
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	  }
	 */
	 

