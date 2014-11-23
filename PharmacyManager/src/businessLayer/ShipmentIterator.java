package businessLayer;
import java.util.ArrayList;

public class ShipmentIterator implements Iterator{
	private ArrayList<Shipment> shpmts;
	int position = 0;
	
	public ShipmentIterator(ArrayList<Shipment> shpmts){
		this.shpmts = shpmts;
	}
	@Override
	public boolean hasNext() {
		if(position < shpmts.size())
			return true;
		
		return false;
	}

	public Object next() {
		Shipment shipment = shpmts.get(position);
		
		position += 1;
		
		return shipment;
	}

}
