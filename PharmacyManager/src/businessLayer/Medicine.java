package businessLayer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import common.Iterator;
import dataLayer.DataLayer;

public class Medicine {
	private DataLayer DB;
	private String name;
	private int medicineId;
	private int overThreshold;
	private int lowThreshold;
	private int expSoonThreshold;
	private ArrayList<Shipment> shpmts = new ArrayList<Shipment>();

	public Medicine(DataLayer DB, Map<String, Object> RowData) throws ClassNotFoundException, SQLException{
		this.name 		      = (String) RowData.get("Name");
		this.overThreshold    = (int) RowData.get("OverStockThreshold");
		this.lowThreshold     = (int) RowData.get("LowStockThreshold");
		this.expSoonThreshold = (int) RowData.get("ExpSoonThreshold");
		this.medicineId       = (int) RowData.get("MedicineID");
		
		this.DB = DB;

		ArrayList<Map<String, Object>> rs = DB.executeQuery("SELECT * " +
															"FROM Shipments " +
															"WHERE MedicineID = " + medicineId);
		
		for(Map<String, Object> shipmentData : rs){
			shpmts.add(new Shipment(DB, shipmentData));
		}
	}

	public String getName(){
		return name;
	}

	public int getOverStockThreshold(){
		return overThreshold;
	}

	public int getLowStockThreshold(){
		return lowThreshold;
	}

	public int getExpSoonThreshold(){
		return expSoonThreshold;
	}

	public int getSold() throws ClassNotFoundException, SQLException{
		if(DB.executeScalar("SELECT SUM(Sold) " +
                            "FROM Shipments WHERE MedicineId = " + this.medicineId) == null) return 0;
		
		return (int)DB.executeScalar("SELECT SUM(Sold) " +
                                     "FROM Shipments WHERE MedicineId = " + this.medicineId);
	}

	public int getStock() throws ClassNotFoundException, SQLException{
		if(DB.executeScalar("SELECT SUM(InStock) " +
							"FROM Shipments WHERE MedicineId = " + this.medicineId) == null) return 0;
		
		return (int)DB.executeScalar("SELECT SUM(InStock) " +
									 "FROM Shipments WHERE MedicineId = " + this.medicineId);
	}

	public int getMedicineId() {
		return medicineId;
	}

	public Iterator getShipmentIterator(){
		return new ShipmentIterator(shpmts);
	}
	
	public static Medicine get(DataLayer DB, int medicineId) {
		try {
			ArrayList<Map<String, Object>> rs;

			rs = DB.executeQuery("SELECT * " +
					" FROM Medicine " +
					" WHERE MedicineID = " + medicineId +
					" LIMIT 1");

			if(rs == null || rs.size() < 1) 
				return null;

			Map<String, Object> medicineData = rs.get(0);
			return new Medicine(DB, medicineData);
		}
		catch (Exception e) {
			//TODO - logging?
			return null;
		}
	}
	
	public static Medicine get(DataLayer DB, String medicineName) {
		try {
			ArrayList<Map<String, Object>> rs;
			
			rs = DB.executeQuery("SELECT * " +
				     " FROM Medicine " +
				     " WHERE Name = '" + medicineName + "' " +
				     " LIMIT 1");

			if(rs == null || rs.size() < 1) 
				return null;
			
			Map<String, Object> medicineData = rs.get(0);
			
			return new Medicine(DB, medicineData);
		}
		catch (Exception e) {
			System.out.println("Error in Medicine.get()");
			return null;
		}
	}
	
	public static void CreateTable(DataLayer DB) throws ClassNotFoundException, SQLException {
		DB.executeNonQuery("CREATE TABLE Medicine " +
				"(MedicineID 			INT PRIMARY KEY     NOT NULL," +
				" Name    		      	VARCHAR				NOT NULL, " + 
				" LowStockThreshold		INT     			NOT NULL, " + 
				" OverStockThreshold	INT					NOT NULL, " +
				" ExpSoonThreshold		INT					NOT NULL)");
	}
}
