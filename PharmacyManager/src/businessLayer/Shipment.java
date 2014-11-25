package businessLayer;

public class Shipment {
	private int shpmtID;
	private int expired;
	private int inStock;
	private int sizeType;
	private int expDate;
	private int sold;

	public Shipment(int shpmtID, int expired, int inStock, int sizeType, int expDate){
		this.shpmtID = shpmtID;
		this.expired = expired;
		this.inStock = inStock;
		this.sizeType = sizeType;
		this.expDate = expDate;
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
}
