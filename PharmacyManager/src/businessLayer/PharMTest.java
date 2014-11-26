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
		pm.addMedication("MedX", 35, 1000000);
		
		 // (name, expDate, amount, size)
		pm.addShipment("Aspirin", 15, 30, 1);
		pm.addShipment("Aspirin", 20, 70, 3);
		pm.addShipment("Tylenol", 30, 50, 1);
		pm.addShipment("Tylenol", 30, 50, 2);
		pm.addShipment("MedX", 90, 150, 2);
		pm.addShipment("MedX", 120, 500, 2);
		
		Medicine med = pm.getMedicine("Aspirin");
		System.out.println("MedicineID: " + med.getMedicineID());
		System.out.println("Medicine: " + med.getName());
		System.out.println("Stock: " + med.getStock());
		System.out.println("Sold: " + med.getSold());
		System.out.println("LowStockThreshold: " + med.getLowStockThreshold());
		System.out.println("OverstockThreshold: " + med.getOverstockThreshold());
		
		System.out.println();
		
		printShipments(med.getShipmentIterator());
		
		
	}
	
	public static void printShipments(Iterator shps){
		while(shps.next()){
			Shipment shp = (Shipment) shps.curr();
			System.out.println("ShipmentID: " + shp.getShipmentID());
			System.out.println("ExpDate: " + shp.getExpDate());
			System.out.println("Expired?: " + shp.getExpired());
			System.out.println("InStock: " + shp.getInStock());
			System.out.println("Size: " + shp.getSizeType());
			System.out.println("Sold: " + shp.getSold());
			System.out.println();
		}
	}

}
