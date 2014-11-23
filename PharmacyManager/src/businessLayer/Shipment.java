package businessLayer;

public class Shipment {
	private int expired;
	private int inStock;
	private int sizeType;
	private int expDate;

	public Shipment(int expired, int inStock, int sizeType, int expDate){
		this.expired = expired;
		this.inStock = inStock;
		this.sizeType = sizeType;
		this.expDate = expDate;
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
}
