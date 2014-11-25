package businessLayer;

import java.util.ArrayList;

public class BulkIterator implements Iterator {
	private ArrayList<Bulk> list;
	private int position = 0;
	
	public BulkIterator(ArrayList<Bulk> list) {
		this.list = list;
	}

	public boolean hasNext() {
		if(position < list.size())
			return true;
		
		return false;
	}

	public Object next() {
		Bulk bulk = list.get(position);
		
		position += 1;
		
		return bulk;
	}
	
	//Remove the current bulk
	public void removeCurrent(){
		list.remove(position-1);
	}

}
