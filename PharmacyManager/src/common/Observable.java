package common;

import java.util.ArrayList;

public abstract class Observable {
	private ArrayList<Observer> observers = null;
	
	public Observable() {
		this.observers = new ArrayList<Observer>();
	}
	
	public boolean addObserver(Observer o) {
		return this.observers.add(o);
	}
	
	public boolean deleteObserver(Observer o) {
		return this.observers.remove(o);
	}
	
	protected void notifyObservers(ArrayList<Object> args) {
		for(Observer o : this.observers)
			o.update(args);
	}
}
