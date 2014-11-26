package businessLayer;

import java.sql.SQLException;

public class PharMTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		PharmacyManager pm = null;
		try {
			pm = new PharmacyManager();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		
		//TODO Add medications from text file?
		
		pm.addMedication("Aspirin", 40, 1000); //Upper limit(notify when overstocked), lower limit (notify when low on stock)
		pm.addMedication("Tylenol", 20, 500);
		
		pm.addShipment("Aspirin", 15, 30, 1);
		pm.addShipment("Aspirin", 20, 35, 1);
		pm.addShipment("Tylenol", 8, 30, 1); // (name, expDate, amount, size)
		
		Medicine med = pm.getMedicine("Aspirin");
		
		System.out.println("MedicineID: " + med.getMedicineId());
		System.out.println();
		System.out.println("Medicine: " + med.getName());
		System.out.println();
		System.out.println("Stock: " + med.getStock());
		System.out.println();
		System.out.println("Sold: " + med.getSold());
		System.out.println();
		System.out.println("LowStockThreshold: " + med.getLowStockThreshold());
		System.out.println();
		System.out.println("OverstockThreshold: " + med.getOverstockThreshold());
		
		
		
		//pm.displayMyStock();
		
		//System.out.println("leftover: " + pm.purchased("Aspirin", 15, 1));
		//System.out.println("leftover: " + pm.purchased("Aspirin", 15, 1));
		//System.out.println("leftover: " + pm.purchased("Aspirin", 15, 1));
		//System.out.println("leftover: " + pm.purchased("Aspirin", 15, 1));
		//System.out.println("leftover: " + pm.purchased("Aspirin", 15, 1));
		
		/*
		pm.displayMyStock();

		pm.aDayHasPassed();
		
		pm.displayMyStock();
		*/
	}

}
