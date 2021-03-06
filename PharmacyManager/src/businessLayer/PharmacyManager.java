package businessLayer;

import dataLayer.DataLayer;

import java.util.ArrayList;
import java.util.Map;
import java.sql.*;

import common.Iterator;
import common.Observer;

public class PharmacyManager {

	//dataLayer
	private DataLayer DB;
	//MedicationID and ShipmentID are not auto-generated by database, so we do so here.
	private int medIDs;
	private int shpIDs;

	private LowStockObservable low = new LowStockObservable();
	private ExpCloseObservable exp = new ExpCloseObservable();

	public PharmacyManager() throws ClassNotFoundException, SQLException {
		DB = new DataLayer("pharmacy");

		//Create tables, if needed
		//ignore exception 
		try{
		Medicine.CreateTable(DB);
		Shipment.CreateTable(DB);
		}
		catch(Exception e){
			
		}
		
		if((int) DB.executeScalar("SELECT COUNT(*) FROM Medicine") == 0 )
			medIDs = 1; //only on empty tables
		else
			medIDs = 1 + (int)DB.executeScalar("SELECT MAX(MedicineID) FROM Medicine"); 
		
		if((int) DB.executeScalar("SELECT COUNT(*) FROM Shipments") == 0 ) 
			shpIDs = 1;
		else
			shpIDs = 1 + (int)DB.executeScalar("SELECT MAX(ShipmentID) FROM Shipments"); 
	}
	
	public void RegisterLowStockObserver(Observer o) {
		low.addObserver(o);
	}
	
	public void RegisterExpCloseObserver(Observer o) {
		exp.addObserver(o);
	}

	//Returns Medicine ojbect or null if fail.
	public Medicine getMedicine(String name) {
		return Medicine.get(DB, name);
	}

	//TODO error check parameters
	public boolean addMedication(String name, int lowFlag, int overFlag){
		try {
			String sql;
			String safeName = name.replace("'", "''");

			//added ExpSoonThreshold
			sql = String.format("INSERT INTO Medicine " +
					            "(MedicineID, Name, LowStockThreshold, OverStockThreshold, ExpSoonThreshold) " +
					            "VALUES(%d, '%s', %d, %d, %d);", medIDs++, safeName, lowFlag, overFlag, 7);
			
			
			int rowsModified = DB.executeNonQuery(sql);

			if(rowsModified < 1) {
				System.out.println("Error in addMedication");
				return false;
			}

			return true;
		}
		catch (Exception e) {
			System.out.println("Error in addMedication");
			return false;
		}
	}

	//TODO error check parameters
	public boolean addShipment(String medication, int expDate, int amount, int sizeOfCans){
		try {
			String sql;

			sql = String.format("SELECT COALESCE(MedicineID, 0) " +
					            "FROM Medicine " +
					            "WHERE Name = '%s';", medication);
			
			int medicineID = (int)DB.executeScalar(sql);
			
			//medicine not found
			if(medicineID == 0) {
				System.out.println("Error in addShipment");
				return false;
			}

			sql = String.format("INSERT INTO Shipments " +
					            "(ShipmentID, Expired, InStock, Sold, ExpDate, Size, MedicineID) " +
					            "VALUES(%d, %d, %d, %d, %d, %d, %d);", shpIDs++, 0, amount, 0, expDate, sizeOfCans, medicineID);
			

			int rowsModified = DB.executeNonQuery(sql);

			//something went wrong
			if(rowsModified < 1){
				System.out.println("Error in addShipment");
				return false;
			}

			return true;
		}
		catch (Exception e) {
			System.out.println("Error in addShipment");
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

			Iterator iter = med.getShipmentIterator();
			while(iter.next() && amount > 0) {
				Shipment shp = (Shipment) iter.curr();

				//make sure it's the right size, not expired
				if(shp.getSizeType() == size && shp.getExpired() == 0) {
					int dif = shp.getInStock() - amount;

					if(dif >= 0){ //enough in this shipment
						DB.executeNonQuery(
								String.format("UPDATE Shipments " +
										"SET Sold = %d, InStock = %d " +
										"WHERE ShipmentID = %d;", 
										shp.getSold() + amount, dif, shp.getShipmentID() ));

						amount = 0; //done
					}
					else {//Not enough in this shipment, carry over to next shipment
						DB.executeNonQuery(
								String.format("UPDATE Shipments " +
										"SET Sold = %d, InStock = %d " +
										"WHERE ShipmentID = %d;", 
										shp.getSold() + shp.getInStock(), 0, shp.getShipmentID() ));

						amount = Math.abs(dif); //What is left to take out in another shipment
					}

				}
			}
			
			//raise stock changed event
			low.RecordChanged(DB, med);
			return amount;
		} catch (Exception e) {
			System.out.println("Error in purchased()");
			return -2;
		}
	}
	
	public MedicineIterator getAllMedications(){
		try {
			ArrayList<Map<String, Object>> medications;

			ArrayList<Medicine> meds = new ArrayList<Medicine>();

			medications = DB.executeQuery(
					String.format(
							"SELECT * " +
					        "FROM Medicine " +
							"ORDER BY Name ASC "));

			if(medications == null || medications.size() < 1) 
				return null;

			for(Map<String, Object> medicineData : medications){
				meds.add(new Medicine(DB, medicineData));
			}

			return new MedicineIterator(meds);
		}
		catch (Exception e) {
			//TODO - logging?
			return null;
		}
	}

	public MedicineIterator getTopMedications(int N){
		try {
			ArrayList<Map<String, Object>> medications;

			ArrayList<Medicine> meds = new ArrayList<Medicine>();

			medications = DB.executeQuery(
					String.format(
							"SELECT Medicine.MedicineID, Name, " +
							"LowStockThreshold, OverStockThreshold, ExpSoonThreshold, " +
							"SUM(Sold) As Sold " +
							"FROM Medicine INNER JOIN Shipments " +
							"ON Medicine.MedicineID = Shipments.MedicineID " +
							"GROUP BY Medicine.MedicineID " +
							"ORDER BY Sold DESC " +
							"LIMIT %d;", N));

			if(medications == null || medications.size() < 1) 
				return null;

			for(Map<String, Object> medicineData : medications){
				meds.add(new Medicine(DB, medicineData));
			}

			return new MedicineIterator(meds);
		}
		catch (Exception e) {
			//TODO - logging?
			return null;
		}
	}

	public void aDayHasPassed() throws ClassNotFoundException, SQLException{
		DB.executeNonQuery("UPDATE Shipments SET ExpDate = ExpDate-1;");
		//Update
		DB.executeNonQuery("UPDATE Shipments SET Expired = InStock, InStock = 0 WHERE ExpDate <= 0;");
		
		ArrayList<Map<String, Object>> meds = DB.executeQuery("SELECT MedicineID FROM Medicine");
		
		//raise exp date changed event
		for(Map<String, Object> med : meds) {
			exp.RecordChanged(DB, med);
		}
	}

}