package MainClasses;

public abstract class Bulk {
	private int expDate;
	private int amount;
	private int sizeOfCans; //small, med, large etc.
	
	public Bulk(int expDate, int amount, int sizeOfCans) {
		this.expDate = expDate;
		this.amount = amount;
		this.sizeOfCans = sizeOfCans;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public int getSizeType(){
		return sizeOfCans;
	}
	
	public int getExpDate(){
		return expDate;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public void decrementExpDate(){
		expDate--;
	}
	
	//getters and setters

}
