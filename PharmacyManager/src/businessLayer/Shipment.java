package businessLayer;

import java.sql.SQLException;
import java.util.Map;

import dataLayer.DataLayer;

public class Shipment {
	private int shpmtID;
	private int expired;
	private int inStock;
	private int sizeType;
	private int expDate;
	private int sold;
	private int medicineID;

	public Shipment(DataLayer DB, Map<String, Object> RowData){
		this.shpmtID    = (int)RowData.get("ShipmentID");
		this.expired    = (int)RowData.get("Expired");
		this.inStock    = (int)RowData.get("InStock");
		this.sold  	    = (int)RowData.get("Sold");
		this.sizeType   = (int)RowData.get("Size");
		this.expDate    = (int)RowData.get("ExpDate");
		this.medicineID = (int)RowData.get("MedicineID");
	}

	public int getShipmentID(){
		return shpmtID;
	}

	public int getExpired(){
		return expired;
	}

	public int getInStock(){
		return inStock;
	}

	public int getSizeType(){
		return sizeType;
	}

	public int getExpDate(){
		return expDate;
	}

	public int getSold() {
		return sold;
	}

	public int getMedicineID() {
		return medicineID;
	}

	public static void CreateTable(DataLayer DB) throws ClassNotFoundException, SQLException {
		DB.executeNonQuery("CREATE TABLE Shipments " +
				"(ShipmentID 	INT PRIMARY KEY     NOT NULL," +
				" Sold          INT	    			NOT NULL, " + 
				" InStock		INT     			NOT NULL, " + 
				" Size			INT					NOT NULL, " +
				" Expired		INT					NOT NULL, " +
				" ExpDate		INT					NOT NULL, " +
				" MedicineID	INT					NOT NULL)");
	}
}
