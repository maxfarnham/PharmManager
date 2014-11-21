package MainClasses;

public class PharMTest {

	public static void main(String[] args) {
		PharmacyManager pm = new PharmacyManager();
		
		//TODO Add medications from text file?
		pm.addMedication("Aspirin", 50, 20); //Upper limit(notify when overstocked), lower limit (notify when low on stock)
		pm.addMedication("Tylenol", 50, 20);
		
		pm.insertBulk("Aspirin", 15, 30, 1);
		pm.insertBulk("Aspirin", 20, 35, 1);
		pm.insertBulk("Tylenol", 8, 30, 1); // (name, expDate, amount, size)
		
		//pm.displayMyStock();
		
		//System.out.println("leftover: " + pm.purchased("Aspirin", 15, 1));
		//System.out.println("leftover: " + pm.purchased("Aspirin", 15, 1));
		//System.out.println("leftover: " + pm.purchased("Aspirin", 15, 1));
		//System.out.println("leftover: " + pm.purchased("Aspirin", 15, 1));
		//System.out.println("leftover: " + pm.purchased("Aspirin", 15, 1));
		
		pm.displayMyStock();

		pm.aDayHasPassed();
		
		pm.displayMyStock();
	}

}
