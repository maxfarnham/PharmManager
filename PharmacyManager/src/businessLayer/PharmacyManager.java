package businessLayer;

import dataLayer.DataLayer;

import java.util.ArrayList;
import java.util.Map;
import java.sql.*;

public class PharmacyManager {

	//dataLayer
	DataLayer DB;

	private Clock clock = new Clock(); //The concrete observable thing

	public PharmacyManager() throws ClassNotFoundException, SQLException {
		DB = new DataLayer("pharmacy");
	}

	//Returns Medicine ojbect or null if fail.
	public Medicine getMedicine(String name) {
		try {
			ArrayList<Map<String, Object>> rs;
			ArrayList<Shipment> shpmts = new ArrayList<Shipment>();

			rs = DB.executeQuery("SELECT TOP 1 MedicineID, LowStockThreshold, OverstockThreshold " +
					"FROM Medications " +
					"WHERE Name = '" + name + "'");

			if(rs == null || rs.size() < 1) 
				return null;

			Map<String, Object> medicine = rs.get(0);
			int overFlag   = (int) medicine.get("OverstockThreshold"), 
					lowFlag    = (int) medicine.get("LowStockThreshold"),
					medicineID = (int) medicine.get("MedicineID"),
					stock      = (int) DB.executeScalar("SELECT COALESCE(SUM(InStock), 0) FROM Shipment WHERE MedicineID = " + medicineID), 
					sold       = (int) DB.executeScalar("SELECT COALESCE(SUM(Sold), 0) FROM Shipment WHERE MedicineID = " + medicineID); 

			rs = DB.executeQuery("SELECT ShipmentID, Expired, InStock, Size, ExpDate " +
					"FROM Shipments" +
					"WHERE MedicineID = " + medicineID + ";");

			for(Map<String, Object> shipment : rs){
				int shpmtID  = (int)shipment.get("ShipmentID"),
						expired  = (int)shipment.get("Expired"),
						inStock  = (int)shipment.get("InStock"),
						sizeType = (int)shipment.get("Size"),
						expDate  = (int)shipment.get("ExpDate");

				shpmts.add(new Shipment(shpmtID, expired, inStock, sizeType, expDate));
			}

			return new Medicine(name, overFlag, lowFlag, sold, shpmts);
		}
		catch (Exception e) {
			//TODO - logging?
			return null;
		}
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
		try {
			String sql;
			String safeName = name.replace("'", "''");

			sql = String.format("INSERT INTO Medications"
					+ "(Name, Sold, LowStockThreshold, OverstockThreshold)"
					+ "VALUES(%s, %d, %d, %d);", safeName, 0, lowFlag, overFlag);

			int rowsModified = DB.executeNonQuery(sql);

			if(rowsModified < 1) 
				return false;

			return true;
		}
		catch (Exception e) {
			//TODO - logging?
			return false;
		}
	}

	//When a new bulk is added to inventory
	//TODO error check parameters
	public boolean insertBulk(String medication, int expDate, int amount, int sizeOfCans) throws ClassNotFoundException, SQLException{
		try {
			String sql;

			sql = String.format("SELECT TOP 1 COALESCE(MedicineID, 0) " +
					"FROM Medications" +
					"WHERE Name = '%s';", medication);

			int medicineID = (int)DB.executeScalar(sql);

			if(medicineID == 0) 
				return false;

			sql = String.format("INSERT INTO Shipments" +
					"(Expired, InStock, ExpDate, Size, MedicineID)" +
					"VALUES(%d, %d, %d, %d, %d)", 0, amount, expDate, sizeOfCans, medicineID);

			int rowsModified = DB.executeNonQuery(sql);

			if(rowsModified < 1) 
				return false;

			return true;
		}
		catch (Exception e) {
			//TODO - logging?
			return false;
		}
	}


	//When a purchase is made
	//
	//TODO implement another algorithm? Always take from bulk with lowest ExpDate or
	// take from a specified bulk (may need to add ID's to different bulks)
	//TODO error check parameters
	public int purchased(String medication, int amount, int size){
		try {
			Medicine med = getMedicine(medication);

			//BUG - do we want this to decrement all our stock even though the sale can't be completed? 
			//Should warn before hand, or roll back the purchse if there's not enough
			//Justo: Ideally, you warn and let the user decide if they want to take what they can or nothing at all. 
			Iterator iter = med.getShipmentIterator();
			while(iter.next() && amount > 0) {
				Shipment shp = (Shipment) iter.curr();

				//make sure it's the right size, not expired
				if(shp.getSizeType() == size && shp.getExpired() == 0) {
					int dif = shp.getInStock() - amount;

					DB.executeNonQuery(String.format("UPDATE Shipment" +
							"SET Sold = %d" +
							"WHERE ShipmentID = %d;", shp.getSold() + amount, med.getMedicineId()));

					DB.executeNonQuery(String.format("UPDATE Shipments" +
							"SET InStock = %d" +
							"WHERE ShipmentID = %d;", dif, shp.getShipmentID()));

					if(dif > 0)
						amount = dif;
					else
						amount = 0;	
				}
			}

			return amount; //medication has ran out, return what couldn't be taken out.
		} catch (Exception e) {
			return -1;
		}
	}

	public void aDayHasPassed(){
		clock.aDayHasPassed();
	}

	//TODO
	public void displayPopularMedications(){

	}

}