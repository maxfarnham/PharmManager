package userInterface;

import java.util.ArrayList;

import businessLayer.Medicine;

import common.Observer;

public class guiLowStockObserver implements Observer {
	private GUI guiInstance = null;
	
	public guiLowStockObserver(GUI instance) {
		this.guiInstance = instance;
	}
	
	@Override
	public void update(ArrayList<Object> args) {
		if (args.size() > 0) {
			Medicine m = (Medicine)args.get(0);
			guiInstance.printScreen(m.getName() + " is low on stock.");
		}
	}
}