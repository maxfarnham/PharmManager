package MainClasses;

import java.util.ArrayList;

public class Clock implements ClockObservable {
	private ArrayList<ClockObserver> observers = new ArrayList<ClockObserver>();
	
	@Override
	public void addObserver(ClockObserver o) {
		observers.add(o);
	}

	@Override
	public void deleteObserver(ClockObserver o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for(ClockObserver o : observers)
			o.update();
	}
	
	public void aDayHasPassed(){
		notifyObservers();
	}
}
