package businessLayer;

import dataLayer.DataLayer;

public class LowStockObservable extends RecordChangedObservable {

	@Override
	public boolean testCondition(DataLayer DB, Object record) {
		if (record instanceof Medicine) {
			Medicine m = (Medicine)record;
			
			//Suppress errors
			try {
				return m.getStock() <= m.getLowStockThreshold();
			}
			catch (Exception e) {
				//TODO- logging?
				return false;
			}
		}
		return false;
	}

}
