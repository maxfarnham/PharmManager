package businessLayer;

import dataLayer.DataLayer;

import java.util.ArrayList;
import java.util.Map;
import java.sql.*;

public class PharmacyManager {

	//dataLayer
	private DataLayer DB;

	private Clock clock = new Clock(); //The concrete observable thing

	public PharmacyManager() throws ClassNotFoundException, SQLException {
		DB = new DataLayer("pharmacy");
	}

	//Returns Medicine ojbect or null if fail.
	public Medicine getMedicine(String name) {
		try {
			ArrayList<Map<String, Object>> rs;
			ArrayList<Shipment> shpmts = new ArrayList<Shipment>();

			rs = DB.executeQuery("SELECT MedicineID, LowStockThreshold, OverstockThreshold " +
					             "FROM Medications " +
					             "WHERE Name = '" + name + "'");

			if(rs == null || rs.size() < 1){ 
				System.out.println("BAD SQL");
				return null;
			}

			Map<String, Object> medicine = rs.get(0);
			    int overFlag   = (int) medicine.get("OverstockThreshold"), 
					lowFlag    = (int) medicine.get("LowStockThreshold"),
					medicineID = (int) medicine.get("MedicineID"),
					stock      = (int) DB.executeScalar("SELECT COALESCE(SUM(InStock), 0) FROM Shipments WHERE MedicineID = " + medicineID), 
					sold       = (int) DB.executeScalar("SELECT COALESCE(SUM(Sold), 0) FROM Shipments WHERE MedicineID = " + medicineID); 

			rs = DB.executeQuery("SELECT ShipmentID, Expired, InStock, Sold, Size, ExpDate " +
					             "FROM Shipments" +
					             "WHERE MedicineID = " + medicineID + ";");

			for(Map<String, Object> shipment : rs){
				int shpmtID  = (int)shipment.get("ShipmentID"),
					expired  = (int)shipment.get("Expired"),
					inStock  = (int)shipment.get("InStock"),
					subSold  = (int)shipment.get("Sold"),
					sizeType = (int)shipment.get("Size"),
					expDate  = (int)shipment.get("ExpDate");

				shpmts.add(new Shipment(shpmtID, expired, inStock, sizeType, expDate, subSold));
			}

			return new Medicine(name, overFlag, lowFlag, sold, stock, shpmts);
		}
		catch (Exception e) {
			System.out.println("Caught Exception.");
			return null;
		}
	}

	//TODO error check parameters
	public boolean addMedication(String name, int lowFlag, int overFlag){
		try {
			String sql;
			String safeName = name.replace("'", "''");

			sql = String.format("INSERT INTO Medications" +
					            "(Name, LowStockThreshold, OverstockThreshold)" +
					            "VALUES('%s', %d, %d);", safeName, lowFlag, overFlag);

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

	//TODO error check parameters
	public boolean addShipment(String medication, int expDate, int amount, int sizeOfCans) throws ClassNotFoundException, SQLException{
		try {
			String sql;

			sql = String.format("SELECT TOP 1 COALESCE(MedicineID, 0) " +
					            "FROM Medications" +
					            "WHERE Name = '%s';", medication);

			int medicineID = (int)DB.executeScalar(sql);
			
			//medicine not found
			if(medicineID == 0) 
				return false;

			sql = String.format("INSERT INTO Shipments" +
					            "(Expired, InStock, Sold, ExpDate, Size, MedicineID)" +
					            "VALUES(%d, %d, %d, %d, %d)", 0, amount, 0, expDate, sizeOfCans, medicineID);

			int rowsModified = DB.executeNonQuery(sql);

			//something went wrong
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
			
			//there isn't enough return with -1
			//Or send message to GUI on what to do
			//Or recall functions with smaller amount
			if(med.getStock() < amount)
				return -1;

			//BUG - do we want this to decrement all our stock even though the sale can't be completed? 
			//Should warn before hand, or roll back the purchse if there's not enough
			Iterator iter = med.getShipmentIterator();
			while(iter.next() && amount > 0) {
				Shipment shp = (Shipment) iter.curr();

				//make sure it's the right size, not expired
				if(shp.getSizeType() == size && shp.getExpired() == 0) {
					int dif = shp.getInStock() - amount;
					
					if(dif >= 0){ //enough in this shipment
						DB.executeNonQuery(
						String.format("UPDATE Shipments" +
							          "SET Sold = %d, InStock = %d" +
							          "WHERE ShipmentID = %d;", 
							          shp.getSold() + amount, dif, shp.getShipmentID() ));
						
						amount = 0; //done
					}
					else {//Not enough in this shipment, carry over to next shipment
						DB.executeNonQuery(
						String.format("UPDATE Shipments" +
									  "SET Sold = %d, InStock = %d" +
									  "WHERE ShipmentID = %d;", 
									  shp.getSold() + shp.getInStock(), 0, shp.getShipmentID() ));
						
						amount = Math.abs(dif); //What is left to take out in another shipment
					}
					
				}
			}

			return amount;
		} catch (Exception e) {
			return -2;
		}
	}

	public MedicineIterator getTopMedications(int N){
		try {
			ArrayList<Map<String, Object>> medications, shipments;
			
			ArrayList<Medicine> meds = new ArrayList<Medicine>();

			medications = DB.executeQuery(
					//TOP may not work on every database!
						  String.format(
								  "SELECT TOP %d Medications.MedicineID, Name," +
								  "LowStockThreshold, OverstockThreshold," +
								  "SUM(InStock) As Stock, SUM(Sold) As Sold " +
					              "FROM Medications INNER JOIN Shipments" +
								  "ON Medications.MedicineID = Shipments.MedicineID" +
					              "GROUP BY Medications.MedicineID" +
								  "ORDER BY Sold DESC;", N));

			if(medications == null || medications.size() < 1) 
				return null;
			
			for(Map<String, Object> medicine : medications){
				ArrayList<Shipment> shpmts = new ArrayList<Shipment>();
				
				String name    = (String) medicine.get("Name");
			    int overFlag   = (int) medicine.get("OverstockThreshold"), 
					lowFlag    = (int) medicine.get("LowStockThreshold"),
					medicineID = (int) medicine.get("MedicineID"),
					stock      = (int) medicine.get("Stock"), 
					sold       = (int) medicine.get("Sold"); 
			    
			    shipments = DB.executeQuery("SELECT ShipmentID, Expired, InStock, Sold, Size, ExpDate " +
			                                "FROM Shipments" +
			                                "WHERE MedicineID = " + medicineID + ";");

			    for(Map<String, Object> shipment : shipments){
					int shpmtID  = (int)shipment.get("ShipmentID"),
						expired  = (int)shipment.get("Expired"),
						inStock  = (int)shipment.get("InStock"),
						subSold  = (int)shipment.get("Sold"),
						sizeType = (int)shipment.get("Size"),
						expDate  = (int)shipment.get("ExpDate");
			
						shpmts.add(new Shipment(shpmtID, expired, inStock, sizeType, expDate, subSold));
			    }
			    
			    meds.add(new Medicine(name, overFlag, lowFlag, sold, stock, shpmts));

			}

			return new MedicineIterator(meds);
		}
		catch (Exception e) {
			//TODO - logging?
			return null;
		}
	}
		
	public void aDayHasPassed(){
		clock.aDayHasPassed();
	}

}