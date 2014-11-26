package businessLayer;
import java.util.ArrayList;

public class MedicineIterator implements Iterator {
	private ArrayList<Medicine> meds;
	private int position = -1;

	public MedicineIterator(ArrayList<Medicine> meds) {
		this.meds = meds;
	}

	@Override
	public boolean next() {
		if(position + 1 < meds.size()) {
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
		return meds.get(position);
	}

}
