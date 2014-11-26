package businessLayer;
import java.util.ArrayList;

public class Medicine {
	private String name;
	private int medicineID;
	private int overFlag;
	private int lowFlag;
	private int sold;
	private int stock;
	private ArrayList<Shipment> shpmts;
	
	public Medicine(String name, int medicineID, int overFlag, int lowFlag, int sold, int stock, ArrayList<Shipment> shpmts){
		this.name = name;
		this.medicineID = medicineID;
		this.overFlag = overFlag;
		this.lowFlag = lowFlag;
		this.sold = sold;
		this.stock = stock;
		
		//TODO - load shipments from DB
		this.shpmts = shpmts;
	}
	
	public String getName(){
		return name;
	}
	
	public int getOverstockThreshold(){
		return overFlag;
	}
	
	public int getLowStockThreshold(){
		return lowFlag;
	}
	
	public int getSold(){
		return sold;
	}
	
	public int getStock(){
		return stock;
	}
	
	public int getMedicineID() {
		return medicineID;
	}
	
	public Iterator getShipmentIterator(){
		return new ShipmentIterator(shpmts);
	}
}
