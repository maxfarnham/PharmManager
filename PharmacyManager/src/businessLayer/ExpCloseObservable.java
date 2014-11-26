package businessLayer;

import dataLayer.DataLayer;

public class ExpCloseObservable extends RecordChangedObservable {

	@Override
	public boolean testCondition(DataLayer DB, Object record) {
		if (record instanceof Shipment) {
			Shipment s = (Shipment)record;
			Medicine m = Medicine.get(DB, s.getMedicineID());
			return s.getExpDate() <= m.getExpSoonThreshold();
		}
		return false;
	}
}
