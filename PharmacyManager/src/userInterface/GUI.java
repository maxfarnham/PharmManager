package userInterface;

/*
import dataLayer.*;
import java.util.Scanner;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.*;
import java.awt.event.*;
*/
import javax.swing.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import businessLayer.PharmacyManager;

public class GUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	// Objects in the GUI
	private JTextArea displayTA;
	private JSpinner numSpin, expSpin, lowSpin, overSpin;
	private JLabel medicineL, numberL, expireL, newMedicineL, lowL, overL, developerL;
	private JTextField medicineTF, newMedicineTF;
	private JButton restockB, saleB, reportB, dayB, newMedB, clearB;
	private JRadioButton smallRB, mediumRB, largeRB;
	private ButtonGroup sizeGroup;
	
	//Will store the instance of PharmacyManager
	private PharmacyManager PM;
	

	// Public access to print to display
	public void printScreen(String str){
		displayTA.append(str);
	}
	// Public access to clear display
	public void clearScreen(){
		displayTA.setText("");
	}
	
	public void GUILaunch(){
		// Creates a new instance of PharmacyManager Class
			//when the GUI is launched
		try {
			PM = new PharmacyManager();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int col1 = 20;
		int row1 = 20;  // index for col1
		int col2 = 330;
		int col3 = 455;
		int row23 = 20; // index for col2 and col3
		
		setTitle("Pharmacy Manager");
		setSize(600, 550);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(null);

		////////////////////////////
		// spinner properties
		int initial = 0;
		int max = 2147483647; // max int
		int min = 0;
		int step = 1;
		///////////////////
		
		developerL = new JLabel("By: Max, Dhruv, Justo, Chris, Jacob");
		developerL.setSize(300,25);
		developerL.setLocation(col1, row1 + 450);
		c.add(developerL);
		
		///////////////////	
		displayTA = new JTextArea();
		displayTA.setWrapStyleWord(true);
		displayTA.setLineWrap(true);
		displayTA.setEditable(false);
		displayTA.setLocation(col1, row1);
		displayTA.setSize(300, 400);
		c.add(displayTA);
		row1 += 400 + 20;
		
		///////////////////
		
		reportB = new JButton("Report");
		reportB.setSize(95, 30);
		reportB.setLocation(col1, row1);
		c.add(reportB);
		reportB.addActionListener(this);
		
		///////////////////
		
		dayB = new JButton("End of Day");
		dayB.setSize(95, 30);
		dayB.setLocation(col1+100, row1);
		c.add(dayB);
		dayB.addActionListener(this);
		///////////////////
				
		medicineL = new JLabel("Medicine: ");
		medicineL.setSize(125, 25);
		medicineL.setLocation(col2, row23);
		c.add(medicineL);
		medicineTF = new JTextField();
		medicineTF.setSize(100,25);
		medicineTF.setLocation(col3, row23);
		c.add(medicineTF);
		row23 += 40;
		///////////////////////
		smallRB = new JRadioButton("Small");
		mediumRB = new JRadioButton("Medium");
		largeRB = new JRadioButton("Large");
		smallRB.setSize(75,25);
		mediumRB.setSize(75,25);
		largeRB.setSize(75,25);
		smallRB.setLocation(col2, row23);
		mediumRB.setLocation(col2+75, row23);
		largeRB.setLocation(col2+150, row23);		
		
		sizeGroup = new ButtonGroup();
	    sizeGroup.add(smallRB);
	    sizeGroup.add(mediumRB);
	    sizeGroup.add(largeRB);
		
		c.add(smallRB);
		c.add(mediumRB);
		c.add(largeRB);
		row23 += 40;
		///////////////////////
		SpinnerModel numModel;
		numModel = new SpinnerNumberModel(initial, min, max, step);
		
		numberL = new JLabel("Quantity: ");
		numberL.setSize(125,30);
		numberL.setLocation(col2, row23);
		c.add(numberL);
		numSpin = new JSpinner(numModel);
		numSpin.setSize(100,30);
		numSpin.setLocation(col3, row23);		
		c.add(numSpin);
		row23 += 40;
		
		///////////////////
		saleB = new JButton("Sale");
		saleB.setSize(100, 30);
		saleB.setLocation(col3, row23);
		c.add(saleB);
		saleB.addActionListener(this);
		row23 += 40;
		////////////////////////////
		JSeparator HBreak1 = new JSeparator(JSeparator.HORIZONTAL);
		HBreak1.setSize(225, 5);
		HBreak1.setLocation(col2, row23 - 5);
		c.add(HBreak1);
		///////////////////////
		expireL = new JLabel("Days until expiration: ");
		expireL.setSize(125,30);
		expireL.setLocation(col2, row23);
		c.add(expireL);
		SpinnerModel expModel;
		expModel = new SpinnerNumberModel(initial, min, max, step);
		expSpin = new JSpinner(expModel);
		expSpin.setSize(100,30);
		expSpin.setLocation(col3, row23);		
		c.add(expSpin); 
		row23 += 40;
		///////////////////////////  
		restockB = new JButton("Restock");
		restockB.setSize(100, 30);
		restockB.setLocation(col3, row23);
		c.add(restockB);
		restockB.addActionListener(this);
		row23 += 40;
		////////////////////////////
		JSeparator HBreak2 = new JSeparator(JSeparator.HORIZONTAL);
		HBreak2.setSize(225, 5);
		HBreak2.setLocation(col2, row23 - 5);
		c.add(HBreak2);
		///////////////////////////
		newMedicineL = new JLabel("New Medicine: ");
		newMedicineL.setSize(125, 25);
		newMedicineL.setLocation(col2, row23);
		c.add(newMedicineL);
		newMedicineTF = new JTextField();
		newMedicineTF.setSize(100,25);
		newMedicineTF.setLocation(col3, row23);
		c.add(newMedicineTF);
		row23 += 40;	
		
		///////////////////////////
		
		lowL = new JLabel("Low Stock Threshold");
		lowL.setSize(125, 30);
		lowL.setLocation(col2, row23);
		c.add(lowL);
		SpinnerModel lowModel;
		lowModel = new SpinnerNumberModel(initial, min, max, step);
		lowSpin = new JSpinner(lowModel);
		lowSpin.setSize(100, 30);
		lowSpin.setLocation(col3, row23);
		c.add(lowSpin);
		row23 += 40;
		/////////////////////////////
		overL = new JLabel("Over Stock Threshold");
		overL.setSize(125, 30);
		overL.setLocation(col2, row23);
		c.add(overL);
		SpinnerModel overModel;
		overModel = new SpinnerNumberModel(initial, min, max, step);
		overSpin = new JSpinner(overModel);
		overSpin.setSize(100, 30);
		overSpin.setLocation(col3, row23);
		c.add(overSpin);
		row23 += 40;

		///////////////////////////  
		newMedB = new JButton("Add New");
		newMedB.setSize(100, 30);
		newMedB.setLocation(col3, row23);
		c.add(newMedB);
		newMedB.addActionListener(this);
		row23 += 40;
		/////////////////////////////
		clearB = new JButton("Clear All");
		clearB.setSize(100, 30);
		clearB.setLocation(col3, row1);
		c.add(clearB);
		clearB.addActionListener(this);
		
		setVisible(true);
	  }
	  
	  public void actionPerformed(ActionEvent m)
	  {
		  // Grab data from interface
		  int size = 0;
		  String sizeStr = null;
		  if(smallRB.isSelected()){
			  size = 1;
			  sizeStr = "small";
		  }
		  if(mediumRB.isSelected()){
			  size = 2;
			  sizeStr = "medium";
		  }
		  if(largeRB.isSelected()){
			  size = 3;
			  sizeStr = "large";
		  }
		  String medicine = medicineTF.getText();
		  String newMedicine = newMedicineTF.getText();
		  int quantity = (int)numSpin.getValue();
		  String quantityStr = String.format("%d", quantity);
		  int expire = (int)expSpin.getValue();
		  String expStr = String.format("%d", expire);
		  int low = (int)lowSpin.getValue();
		  String lowStr = String.format("%d", low);
		  int over = (int)overSpin.getValue();
		  String overStr = String.format("%d", over);
		  // Change all text color to black
		  smallRB.setForeground(Color.black);
		  mediumRB.setForeground(Color.black);
		  largeRB.setForeground(Color.black);
		  numberL.setForeground(Color.black);
		  medicineL.setForeground(Color.black);
		  expireL.setForeground(Color.black);
		  newMedicineL.setForeground(Color.black);
		  lowL.setForeground(Color.black);
		  overL.setForeground(Color.black);
		  
		  if(m.getSource() == saleB){
			  // sale needs Medicine name, qty, size, maybe
			  if(medicine.equals("") || size == 0 || quantity == 0){
				  smallRB.setForeground(Color.red);
	    		  mediumRB.setForeground(Color.red);
	    		  largeRB.setForeground(Color.red);
	    		  numberL.setForeground(Color.red);
	    		  medicineL.setForeground(Color.red);
			  }
			  else{
				  if(PM.getMedicine(medicine) != null){
					  // execute sale method
					  PM.purchased(medicine, quantity, size);
					  
					  printScreen("saleB " + medicine + " " + sizeStr + " " + quantityStr);
					  //displayTA.setText(medicine);   
				  }
				  {
					  printScreen(medicine + "is not in the DataBase!");
				  }
			  }
	    	  //String result = commandHandler.executeNonScalar("SELECT * FROM MEDICINE").toString();    
	      }
	      if (m.getSource() == restockB){
	    	  //Restock needs Medicine Name, Qty, Size, expire
	    	  if(medicine.equals("") || quantity == 0 || size == 0 || expire == 0){
	    		  smallRB.setForeground(Color.red);
	    		  mediumRB.setForeground(Color.red);
	    		  largeRB.setForeground(Color.red);
	    		  numberL.setForeground(Color.red);
	    		  medicineL.setForeground(Color.red);
	    		  expireL.setForeground(Color.red);	    		  
	    	  }
	    	  else{
	    		  //TODO:
	    		  // execute method to restock
	    		  // restock(medicine, quantity, size, expire);
	    		  
	    		  printScreen("restockB" + " " + medicine + " " + quantityStr + " " + sizeStr + " " + expStr);
	    		  //displayTA.setText("restockB" + medicine + " " + quantityStr + " " + size + " " + expStr); // sample	  
	    	  }
	      }
	      if(m.getSource() == newMedB){
	    	  // New medicine needs newMed name, low threshold and over threshold
	    	  if(medicine.equals("") || low == 0 || over == 0){
	    		  newMedicineL.setForeground(Color.red);
	    		  lowL.setForeground(Color.red);
	    		  overL.setForeground(Color.red);
	    	  }
	    	  else{
	    		  // TODO:
	    		  // execute method to insert new med
	    		  // newMed(newMedicine, low, high);
	    		  
	    		  printScreen("newMedB " + newMedicine + " " + lowStr + " " + overStr);
	    		  //displayTA.setText("newMedB" + newMedicine + " " + lowStr + " " + overStr);
	    	  }
	      }
	      if(m.getSource() == dayB){
	    	  // TODO:
	    	  // execute method to cycle day
	    	  // day();
	    	  
	    	  printScreen("dayB");
	    	  //displayTA.setText("dayB");
	      }
	      if(m.getSource() == reportB){
	    	  // execute method to run reports
	    	  // report();
	    	  
	    	  //String result = d.executeNonScalar("SELECT * FROM MEDICINE").toString();
		      printScreen("reportB");
		      
		      //displayTA.setText("reportB");  
	      }
	      if(m.getSource() == clearB){
	    	  
	    	  sizeGroup.clearSelection();
	    	  medicineTF.setText("");
	    	  newMedicineTF.setText("");
	    	  numSpin.setValue(0);
	    	  expSpin.setValue(0);
	    	  lowSpin.setValue(0);
	    	  overSpin.setValue(0);
	    	  printScreen("");
	    	  //displayTA.setText("");
	      }
	  }
	public static void main (String[] args){
		GUI newGui = new GUI();
		newGui.GUILaunch();
	}
}



