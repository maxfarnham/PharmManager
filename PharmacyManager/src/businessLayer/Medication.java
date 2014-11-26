package businessLayer;

import java.util.ArrayList;

public class Medication implements ClockObserver{
	private String name;
	private int stock = 0;
	private int overFlag;
	private int lowFlag;
	private int sold = 0; //This will be needed to keep track of popular medications. The ones that sell the most.
	private ArrayList<Bulk> bulks = new ArrayList<Bulk>();
	
	public Medication(String name, int overFlag, int lowFlag) {
		this.name = name;
		this.overFlag = overFlag;
		this.lowFlag = lowFlag;
	}
	
	public String getName(){
		return name;
	}
	
	public int getStock(){
		return stock;
	}
	
	public int getSold(){
		return sold;
	}
	
	public void addBulk(Bulk bulk){
		bulks.add(bulk);
		stock += bulk.getAmount();
		
		if(stock >= overFlag){
			System.out.println("Medication " + getName() +
					           " is overstocked with " + getStock() + " items." );
		}
	}
	
	public void saleMade(int sold){
		this.sold += sold;
		this.stock -= sold;
		
		if(stock <= lowFlag){
			System.out.println("Medication " + getName() + 
					           " is low on stock with " + getStock() + " items.");
		}
	}
	
	//required from interface Aggregate
	public Iterator createIterator(){
		return new BulkIterator(bulks);
	}
	
	//required from ClockObserver
	public void update() {
		System.out.println("Daily alerts for stock on " + getName() + ":");
		
		Iterator bulkIterator = createIterator();
		
		while(bulkIterator.hasNext()){
			Bulk bulk = (Bulk) bulkIterator.next();
			bulk.decrementExpDate();
			
			if(bulk.getExpDate() <= 0){
				System.out.println("A Bulk has expired!"); //TODO Ask user to remove bulk.
				bulkIterator.removeCurrent();
				stock -= bulk.getAmount();
			}
			
			if(bulk.getExpDate() <= 7)
				System.out.println("A Bulk is about to expire in " + bulk.getExpDate() + " days.");
			
		}
			
		if(stock >= overFlag)
			System.out.println("Overstocked with " + getStock() + " items." );
		if(stock <= lowFlag)
			System.out.println("Low on stock with " + getStock() + " items.");
		
		
		System.out.println();
	}
}
