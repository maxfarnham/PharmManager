package businessLayer;
import java.util.ArrayList;

public class ShipmentIterator implements Iterator{
	private ArrayList<Shipment> shpmts;
	int position = -1;
	
	public ShipmentIterator(ArrayList<Shipment> shpmts){
		this.shpmts = shpmts;
	}

	@Override
	public boolean next() {
		if(position + 1 < shpmts.size()) {
			position += 1;
			return true;
		}
		
		return false;
	}
	
	@Override
	public Object curr() {
		return shpmts.get(position);
	}
	
	@Override
	public Object first() {
		position = 0;
		return curr();
	}

}
