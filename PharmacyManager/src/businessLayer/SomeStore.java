package businessLayer;

public class SomeStore extends BulkStore {

	public SomeStore() {
	}

	//here the factory chooses the appropriate instance of Bulk to return, depending on size
	protected Bulk createBulk(int expDate, int amount, int sizeOfCans) {
		if(sizeOfCans == 1)
			return new SmallBulk(expDate, amount);
		else if (sizeOfCans == 2) //TODO write subclass MedBulk
			return null; //new MedBulk(expIn, amount);
		else if (sizeOfCans == 3) //TODO write subclass LargeBulk
			return null; //new LargeBulk(expIn, amount);
		else
			return null; //error?
	}

}
