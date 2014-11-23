package businessLayer;
import java.util.ArrayList;

public class Medicine {
	private String name;
	private int stock;
	private int overFlag;
	private int lowFlag;
	private int sold = 0;
	private ArrayList<Shipment> shpmts;
	
	public Medicine(String name, int stock, int overFlag, int lowFlag, int sold, ArrayList<Shipment> shpmts){
		this.name = name;
		this.stock = stock;
		this.overFlag = overFlag;
		this.lowFlag = lowFlag;
		this.sold = sold;
		this.shpmts = shpmts;
	}
	
	public String getName(){
		return name;
	}
	
	public int getStock(){
		return stock;
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
	
	public Iterator getShipmentIterator(){
		return new ShipmentIterator(shpmts);
	}
}
