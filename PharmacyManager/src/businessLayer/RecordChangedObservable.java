package businessLayer;

import java.util.ArrayList;
import java.util.Arrays;

import common.Observable;
import dataLayer.DataLayer;

public abstract class RecordChangedObservable extends Observable {
	
	public void RecordChanged(DataLayer DB, Object record) {
		if(testCondition(DB, record))
			notifyObservers(new ArrayList<Object>(Arrays.asList(record)));
	}
	
	public abstract boolean testCondition(DataLayer DB, Object record);
}
