package businessLayer;
import java.util.ArrayList;

import common.Iterator;

public class ShipmentIterator implements Iterator{
	private ArrayList<Shipment> shpmts;
	private int position = -1;
	
	public ShipmentIterator(ArrayList<Shipment> shpmts){
		this.shpmts = shpmts;
	}

	@Override
	public boolean next() {
		if(position + 1 < shpmts.size()) {
			position++;
			return true;
		}
		
		return false;
	}
	
	@Override
	public Object first() {
		position = 0;
		return curr();
	}
	
	@Override
	public Object curr() {
		return shpmts.get(position);
	}
	
}
