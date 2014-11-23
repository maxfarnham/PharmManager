package MainClasses;

public interface ClockObservable {
	void addObserver(ClockObserver o);
	void deleteObserver(ClockObserver o);
	void notifyObservers();
}
