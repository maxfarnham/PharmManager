package MainClasses;

public abstract class BulkStore {

	public BulkStore() {
	}
	
	public Bulk orderBulk(int expDate, int amount, int sizeOfCans){
		return createBulk(expDate, amount, sizeOfCans);
	}
	
	//abstract method
	protected abstract Bulk createBulk(int expDate, int amount, int sizeOfCans);

}
