package businessLayer;
import java.util.ArrayList;

public class Medicine {
	private String name;
	private int medicineId;
	private int overFlag;
	private int lowFlag;
	private int sold = 0;
	private ArrayList<Shipment> shpmts;
	
	public Medicine(String name, int overFlag, int lowFlag, int sold, ArrayList<Shipment> shpmts){
		this.name = name;
		this.overFlag = overFlag;
		this.lowFlag = lowFlag;
		this.sold = sold;
		
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
	
	public int getMedicineId() {
		return medicineId;
	}
	
	public Iterator getShipmentIterator(){
		return new ShipmentIterator(shpmts);
	}
}
