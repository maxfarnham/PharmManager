package MainClasses;

import java.util.ArrayList;
import java.sql.*;

public class PharmacyManager {
	
	//An instance of the dataLayer where I can send sql commands
	private DataTier datatier;
	
	
	private int totalStock = 0; //Maybe not needed?
	private ArrayList<Medication> meds = new ArrayList<Medication>(); //Aspirin, Tylenol, etc..
	private BulkStore bulkStore = new SomeStore();// the factory method pattern
	private Clock clock = new Clock(); //The concrete observable thing
	
	public PharmacyManager(String dbFileName) {
		datatier = new DataTier(dbFileName);
	}
	
	//new method
	public Medicine getMedicine(String name){
		String sql;
		ResultSet rs;
		int stock, overFlag, lowFlag, sold;
		ArrayList<Shipment> shpmts;
		
		sql = "SELECT LowStockThreshold, OverstockThreshold " +
		      "FROM Medications " +
		      "WHERE Name = " + name + ";";
		
		rs = datatier.executeQuery(sql);
		rs.next();
		
		//set thresholds
		lowFlag = rs.getInt("LowStockThreshold");
		overFlag = rs.getInt("OverstockThreshold");
		
		sql = "SELECT SUM(InStock) AS Stock, SUM(Sold) AS Sold"  +
			  "FROM Medications INNER JOIN Shipments " +
			  "ON Medications.MedicineID = Shipments.MedicineID " +
			  "WHERE Medications.Name = " + name + ";";
		
		rs = datatier.executeQuery(sql);
		rs.next();
		
		//set stock and sold
		stock = rs.getInt("Stock");
		sold = rs.getInt("Sold");
		
		sql = "SELECT Shipments.ShipmentID, " +
	          "Shipments.Expired, Shipments.InStock, Shipments.SizeType, Shipments.ExpDate " + //NOTE add SizeType to table
	          "FROM Medications INNER JOIN Shipments " +
	          "ON Medications.MedicineID = Shipments.MedicineID" +
	          "WHERE Medications.Name = " + name + ";";
		
		rs = datatier.executeQuery(sql);
		
		//fill ArrayList
		while(rs.next()){
			int expired = rs.getInt("Expired");
			int inStock = rs.getInt("InStock");
			int sizeType = rs.getInt("SizeType");
			int expDate = rs.getInt("ExpDate");

			shpmts.add(new Shipment(expired, inStock, sizeType, expDate));
		}
		
		return new Medicine(name, stock, overFlag, lowFlag, sold, shpmts);
	}
	//display all the bulks for each Medication
	//function to be renamed to getMedicationsIterator()?
	//Actualy display to be done at GUI level
	/*
	public Iterator displayMyStock(){
		//here is what I will like to ideally be able to do...
		String sql = "SELECT Medications.Name, Shipments.ShipmentID, " +
		             "Shipments.InStock, Shipments.SizeType, Shipments.ExpDate " + //NOTE add SizeType to table
		             "FROM Medications INNER JOIN Shipments " +
		             "ON Medications.MedicineID = Shipments.MedicineID";
		             
		ResultSet dataset1 = datatier.executeQuery(sql);
		
		sql = "SELECT SUM(InStock) " +
			  "FROM Shipments " +
			  "GROUP BY MedicineID";
			
	    ResultSet dataset2 = datatier.executeQuery(sql);
		
		Iterator medicineIterator = new MedicineIterator(dataset1, dataset2); 
		
		return medicineIterator;
		
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
	public void addMedication(String name, int overFlag, int lowFlag){
		Medication newMed = new Medication(name, overFlag, lowFlag);
		
		meds.add(newMed);
		clock.addObserver(newMed);
		
	}
	
	//When a new bulk is added to inventory
	//TODO error check parameters
	public boolean insertBulk(String medication, int expDate, int amount, int sizeOfCans){
		Bulk newBulk = bulkStore.orderBulk(expDate, amount, sizeOfCans); //use factory method to get a bulk
		
		//Find the correct medication
		for(Medication med : meds){
			if(med.getName() == medication){ //match the given name
				med.addBulk(newBulk);
				totalStock += amount;
				return true; //success
			}
		}
		
		return false; //medication not found

	}
	
	
	//When a purchase is made
	//
	//TODO implement another algorithm? Always take from bulk with lowest ExpDate or
	// take from a specified bulk (may need to add ID's to different bulks)
	//TODO error check parameters
	public int purchased(String medication, int amount, int size){
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
