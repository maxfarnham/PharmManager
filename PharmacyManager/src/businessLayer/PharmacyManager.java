package businessLayer;

import dataLayer.commandHandler;
import java.util.ArrayList;
import java.sql.*;

public class PharmacyManager {
	
	//dataLayer
	commandHandler db;
	
	private int totalStock = 0; //Maybe not needed?
	//private ArrayList<Medication> meds = new ArrayList<Medication>(); //Aspirin, Tylenol, etc..
	//private BulkStore bulkStore = new SomeStore();// the factory method pattern
	private Clock clock = new Clock(); //The concrete observable thing
	
	public PharmacyManager() {
		db = new commandHandler();
	}
	
	//Returns Medicine ojbect or null if fail.
	public Medicine getMedicine(String name){
		String sql;
		ArrayList<ArrayList<Object>> rs;
		int overFlag, lowFlag, stock, sold;
		int medicineID;
		ArrayList<Shipment> shpmts = new ArrayList<Shipment>();
		
		sql = "SELECT MedicineID, Sold, LowStockThreshold, OverstockThreshold " +
		      "FROM Medications " +
		      "WHERE Name = " + name + ";";
		
		rs = db.executeNonScalar(sql);
		
		if(rs == null) return null;
		
		medicineID = (int)rs.get(0).get(0);
		sold = (int)rs.get(0).get(1);
		lowFlag  = (int)rs.get(0).get(2);
		overFlag = (int)rs.get(0).get(3);
		
		sql = "SELECT SUM(InStock)"  +
		      "FROM Shipments" +
	              "WHERE MedicineID = " + medicineID + ";";
		
		
		Object obj = db.executeScalar(sql);
		
		//this may not be right?
		if(obj == null) stock = 0;
		else stock = (int)obj;
		
		sql = "SELECT ShipmentID, Expired, InStock, Size, ExpDate " +
	              "FROM Shipments" +
	              "WHERE MedicineID = " + medicineID + ";";
		
		rs = db.executeNonScalar(sql);
		
		if(rs == null) return null;
		//fill ArrayList
		for(ArrayList<Object> shipment : rs){
			int shpmtID  = (int)shipment.get(0);
			int expired  = (int)shipment.get(1);
			int inStock  = (int)shipment.get(2);
			int sizeType = (int)shipment.get(3);
			int expDate  = (int)shipment.get(4);

			shpmts.add(new Shipment(shpmtID, expired, inStock, sizeType, expDate));
		}
		
		return new Medicine(name, stock, overFlag, lowFlag, sold, shpmts);
	}
	
	//display all the bulks for each Medication
	//Actualy display to be done at GUI level
	/*
	public Iterator displayMyStock(){
		
		System.out.println("Overall total: " + this.getTotalStock());
		System.out.println();
		
		for(Medication med : meds){
			System.out.println("Stock for " + med.getName() );			
			System.out.println("Total Stock:" + med.getStock() );
			
			Iterator bulkIterator = med.createIterator();
			
			while(bulkIterator.hasNext()){
				Bulk bulk = (Bulk) bulkIterator.next();
				System.out.println("Bulk amount: " + bulk.getAmount() );
				System.out.println("Size type: " + bulk.getSizeType() );
				System.out.println("Expires in " + bulk.getExpDate() + " days." );
			}
			System.out.println();
		}
		
	} */
	
	
	//Add medication types, with overstock flag and low on stock flag (ints)
	//TODO error check parameters
	//returns rowsModified (0 on fail)
	public boolean addMedication(String name, int overFlag, int lowFlag){
		String sql;
		String safeName = name.replace("'", "''");
		
		sql = String.format("INSERT INTO Medications"
				  + "(Name, Sold, LowStockThreshold, OverstockThreshold)"
			          + "VALUES(%s, %d, %d, %d);", safeName, 0, lowFlag, overFlag);
		
		int rowsModified = db.executeNonQuery(sql);
		
		if(rowsModified < 1) return false;
		
		return true;
		
		/*
		Medication newMed = new Medication(name, overFlag, lowFlag);
		
		meds.add(newMed);
		clock.addObserver(newMed);
		*/
		
	}
	
	//When a new bulk is added to inventory
	//TODO error check parameters
	public boolean insertBulk(String medication, int expDate, int amount, int sizeOfCans){
		String sql;
		
		sql = 
		String.format("SELECT MedicineID" +
			      "FROM Medications" +
		              "WHERE Name = %s;", medication);
		
		Object medicineID = db.executeScalar(sql);
		
		if(medicineID == null) return false;
		
		sql = 
		String.format("INSERT INTO Shipments" +
			      "(Expired, InStock, ExpDate, Size, MedicineID)" +
			      "VALUES(%d, %d, %d, %d, %d)", 0, amount, expDate, sizeOfCans, (int)medicineID);

		int rowsModified = db.executeNonQuery(sql);
		
		if(rowsModified < 1) return false;
		
		return true;
		
		//Find the correct medication
		/*
		for(Medication med : meds){
			if(med.getName() == medication){ //match the given name
				med.addBulk(newBulk);
				totalStock += amount;
				return true; //success
			}
		}
		
		return false; //medication not found
		
		*/

	}
	
	
	//When a purchase is made
	//
	//TODO implement another algorithm? Always take from bulk with lowest ExpDate or
	// take from a specified bulk (may need to add ID's to different bulks)
	//TODO error check parameters
	public int purchased(String medication, int amount, int size){
		String sql;
		
		sql = 
		String.format("SELECT MedicineID" +
			      "FROM Medications" +
		              "WHERE Name = %s;", medication);
				
		Object medicineID = db.executeScalar(sql);
				
		if(medicineID == null) return -1; //wrong medicine name
				
		sql = 
		String.format("SELECT ShipmentID, InStock" +
			      "FROM Shipments" + 
			      "WHERE MedicineID = %d AND Size = %d AND Expired = 0;", (int)medicineID, size);
		
		ArrayList<ArrayList<Object>> rs = db.executeNonScalar(sql);
		
		if(rs == null) return -2; //no shipments, or no matching size, or all expired
		
		int shipmentID, inStock, dif;
		
		for(ArrayList<Object> shipment : rs){
			shipmentID = (int)shipment.get(0);
			inStock    = (int)shipment.get(1);
			
			dif = inStock - amount;
			
			if(dif < 0){ //enough in this shipment
				sql = String.format("UPDATE Medications" +
						    "SET Sold = Sold + %d" +
						    "WHERE MedicineID = %d;", amount, (int)medicineID);
				
				if(db.executeNonQuery(sql) < 1) return -3; //error ?
				
				sql = String.format("UPDATE Shipments" +
						    "SET InStock = %d" +
						    "WHERE ShipmentID = %d;", dif, shipmentID);
				
				db.executeNonQuery(sql);
				
				return 0;
			}
			else if (dif == 0){ //exact amount in this shipment
				sql = String.format("UPDATE Medications" +
						    "SET Sold = Sold + %d" +
						    "WHERE MedicineID = %d;", amount, (int)medicineID);
				
				db.executeNonQuery(sql);
				
				sql = String.format("DELETE FROM Shipments" +
						    "WHERE ShipmentID = %d;", shipmentID);
				
				db.executeNonQuery(sql);
				
				return 0;
			}
			else{ //not enough in this shipment
				sql = String.format("UPDATE Medications" +
						    "SET Sold = Sold + %d" +
						    "WHERE MedicineID = %d;", inStock, (int)medicineID);
	
				db.executeNonQuery(sql);
	
				sql = String.format("DELETE FROM Shipments" +
						    "WHERE ShipmentID = %d;", shipmentID);
	
				db.executeNonQuery(sql);
				
				amount = Math.abs(dif); //carry over to next shipment
			}
		}//for each shipment loop
		
		return amount; //medication has ran out, return what couldn't be taken out.
		
		/*PREVIOUS CODE
		for(Medication med : meds){
				
			//search for matching medication
			if(med.getName() == medication){
				
				Iterator bulkIterator = med.createIterator(); //use of the iterator pattern
				
				//search for matching size
				while(bulkIterator.hasNext()){
					Bulk bulk = (Bulk) bulkIterator.next();
					
					//right size found
					if(bulk.getSizeType() == size){
						int dif = bulk.getAmount() - amount; //difference between the amount in the bulk and what the user actually wants
						
						if(dif > 0){ //there is enough in this bulk
							bulk.setAmount(dif); //whats left in the bulk
							med.saleMade(amount); //sold count updated
							totalStock -= amount;
							return 0;
						}
						else if (dif == 0){ //Exact amount in the bulk, so just take what's there and remove bulk
							med.saleMade(amount);
							totalStock -= amount;
							bulkIterator.removeCurrent();
							return 0;
						}
						else{ //not enough in this bulk, so just take what you can and return what was not taken
							  // to possibly use in another call to this function
							med.saleMade(bulk.getAmount()); //only take what was left in the bulk
							totalStock -= bulk.getAmount();
							bulkIterator.removeCurrent();
							return Math.abs(dif); //amount to take from another bulk
						}
							
					}
					
				} //while
				
				return -1; //no match in size type or no bulks in Medication
			}
			
		}//for
		
		
		return -1; //med not found
		*/
	}
	
	public void aDayHasPassed(){
		clock.aDayHasPassed();
	}
	
	//TODO
	public void displayPopularMedications(){
		
	}
	
	public int getTotalStock(){
		return totalStock;
	}

}
