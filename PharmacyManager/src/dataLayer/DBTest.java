package dataLayer;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import dataLayer.DataLayer;

// SQL Command execution examples
public class DBTest {
	 public static void main( String args[] )
	  {
		 try {
			 DataLayer DB = new DataLayer("testing");
		     String sql = "CREATE TABLE COMPANY " +
	                   "(ID INT PRIMARY KEY     NOT NULL," +
	                   " NAME           VARCHAR    NOT NULL, " + 
	                   " AGE            INT     NOT NULL, " + 
	                   " ADDRESS        CHAR(50), " + 
	                   " SALARY         REAL)"; 
			 DB.executeNonQuery(sql);
			 
			 DB.executeNonQuery("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
	                 "VALUES (1, 'Paul', 32, 'California', 20000.00 );");
			 DB.executeNonQuery("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
					 "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );");
			 DB.executeNonQuery("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
					 "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );");
			 DB.executeNonQuery("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
					 "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );");
		 
			 // printing scalar directly
			 System.out.print("Max Salary (direct): ");
			 System.out.println(DB.executeScalar("SELECT MAX(SALARY) FROM COMPANY"));
			 
			 // storing values from scalar query into values
			 double salary = (double)DB.executeScalar("SELECT MAX(SALARY) FROM COMPANY");
			 System.out.println("Max salary (stored): " + salary);
			 
			 // printing non-scalar query directly
			 System.out.println(DB.executeQuery("SELECT NAME, AGE FROM COMPANY"));
			 
			 // storing values from non-scalar query into values
			 ArrayList<Map<String, Object>> testArrayList = DB.executeQuery("SELECT NAME, AGE FROM COMPANY");
			 int arraySize = testArrayList.size();
			 System.out.println("using the array list gets");
			 int i = 0;
			 for(i = 0; i < arraySize; i++){
				 for(Entry<String, Object> entry : testArrayList.get(i).entrySet()){
					 System.out.println(entry.getKey() + " " + entry.getValue().toString());
				 }
			 }
			 
			 //can also access values as follows if you know column name before hand:
			 //testArrayList.get(rowIndex).get('columnName')
		 }
		 catch (Exception e) {
			 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		 }

	  }
}

