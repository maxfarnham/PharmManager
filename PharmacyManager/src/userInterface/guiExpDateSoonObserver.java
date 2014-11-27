package userInterface;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import businessLayer.Shipment;
import common.Observer;

public class guiExpDateSoonObserver implements Observer {
	private GUI guiInstance = null;
	
	public guiExpDateSoonObserver(GUI instance) {
		this.guiInstance = instance;
	}
	
	@Override
	public void update(ArrayList<Object> args) {
		if (args.size() > 0) {
			Shipment s = (Shipment)args.get(0);
			JOptionPane.showMessageDialog(null, "Shipment ID: " + s.getShipmentID() + " is close to expiring.");
		}
	}
}
