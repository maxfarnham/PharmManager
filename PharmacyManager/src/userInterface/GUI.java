package userInterface;


/*
import dataLayer.*;
import java.util.Scanner;
import java.awt.Color;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.*;
import java.awt.event.*;
*/
import javax.swing.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JTextArea displayTA;
	private JSpinner numSpin, expSpin, lowSpin, overSpin;
	private JLabel medicineL, numberL, expireL, newMedicineL, lowL, overL, developerL;
	private JTextField medicineTF, newMedicineTF;
	private JButton restockB, saleB, reportB, dayB, newMedB, clearB;
	private JRadioButton smallRB, mediumRB, largeRB;
	private ButtonGroup sizeGroup;
	//private Font header = new Font("", null, 8);
	
	public void printToScreen(String str){
		displayTA.setText(str);
	}
	public void GUILaunch(){
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
		  int size = 0;
		  if(smallRB.isSelected())
			  size = 1;
		  if(mediumRB.isSelected())
			  size = 2;
		  if(largeRB.isSelected())
			  size = 3;
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
				  printToScreen(medicine);
			      //displayTA.setText(medicine);    
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
	    		  // execute method to restock
	    		  printToScreen("restock");
	    		  //displayTA.setText(medicine + "\n" + quantityStr + " " + size); // sample
	    		  
	    	  }
	      }
	      
	      if(m.getSource() == reportB){
	    	  //String result = d.executeNonScalar("SELECT * FROM MEDICINE").toString();
		      int next = (int)numSpin.getValue();
		      String str = String.format("%d", next);
		      
		      printToScreen(str);
		      
		      //displayTA.setText(str);  
	      }
	      if(m.getSource() == newMedB){
	    	  // New medicine needs newMed name, low threshold and over threshold
	    	  if(medicine.equals("") || low == 0 || over == 0){
	    		  newMedicineL.setForeground(Color.red);
	    		  lowL.setForeground(Color.red);
	    		  overL.setForeground(Color.red);
	    	  }
	    	  else{
	    		  printToScreen(newMedicine + " " + lowStr + " " + overStr);
	    		  //displayTA.setText(newMedicine + " " + lowStr + " " + overStr);
	    	  }
	      }
	      if(m.getSource() == clearB){
	    	  
	    	  sizeGroup.clearSelection();
	    	  medicineTF.setText("");
	    	  newMedicineTF.setText("");
	    	  numSpin.setValue(0);
	    	  expSpin.setValue(0);
	    	  lowSpin.setValue(0);
	    	  overSpin.setValue(0);
	    	  printToScreen("");
	    	  //displayTA.setText("");
	      }
	      
	  }
	
	
	public static void main (String[] args){
		GUI newGui = new GUI();
		newGui.GUILaunch();
		
		/*
		JFrame theGUI = new JFrame();
		Scanner reader = new Scanner(System.in);
		GUIhelper newGui = new GUIhelper();
		//newGui.init();
		theGUI.setSize(200, 300);
		theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theGUI.setTitle("GUI Window");
		theGUI.setVisible(true);
		*/
		
	}


}




/*import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
*/
/*
public class GUIhelper extends JApplet implements ItemListener, ActionListener
{
  private JCheckBox tomatoCB, greenPepperCB, blackOlivesCB, mushroomsCB, extraCheeseCB, pepperoniCB, sausageCB;
  private JRadioButton smallRB, mediumRB, largeRB;
  private ButtonGroup sizeGroup;
  private JComboBox crustDD;
  private JTextArea orderTA;
  private String blankCrust = " ",thinCrust = "Thin Crust", panCrust = "Pan Crust", deepDishCrust = "Deep Dish";
  private String[] crustNames = {blankCrust, thinCrust, panCrust, deepDishCrust};
  private JLabel nameLabel, toppingsLabel, crustLabel, sizeLabel;

  private int topCount = 0;
  private int sizeCount = 0;
  
  private String toppings = "Toppings: ";
  private String crust = "Crust: ";
  private String size = "Size: ";
  private String dashes = "--------------------------------------------";
  private Font header = new Font("", Font.ITALIC + Font.BOLD, 12);  // Header formatting
  
  private JButton resetB;
  
  
  
  public void init() 
  {
      Container c = getContentPane();
      c.setLayout(null);
      setSize(450, 450);
      setName("Pizza Shop");
      nameLabel = new JLabel("Mama Jake's Authentic Italian Restaurant");
      nameLabel.setFont(new Font("", Font.ITALIC + Font.BOLD, 18));
      nameLabel.setForeground(Color.red);
      
      resetB = new JButton("Reset");
             
      toppingsLabel = new JLabel("Each topping: $1.50");
      toppingsLabel.setFont(header);
      toppingsLabel.setForeground(Color.red);
      crustLabel = new JLabel("Crust:");
      crustLabel.setFont(header);
      crustLabel.setForeground(Color.red);
      sizeLabel = new JLabel("Size:");
      sizeLabel.setFont(header);
      sizeLabel.setForeground(Color.red);
      
      tomatoCB = new JCheckBox("Tomatos");
      greenPepperCB = new JCheckBox("Green Peppers");
      blackOlivesCB = new JCheckBox("Black Olives");
      mushroomsCB = new JCheckBox("Mushrooms");
      extraCheeseCB = new JCheckBox("Extra Cheese");
      pepperoniCB = new JCheckBox("Pepperoni");
      sausageCB = new JCheckBox("Sausage");
      
      smallRB = new JRadioButton("Small $6.50");
      mediumRB = new JRadioButton("Medium $8.00");
      largeRB = new JRadioButton("Large $10.00");
      
      orderTA = new JTextArea();
      orderTA.setWrapStyleWord(true);
      orderTA.setLineWrap(true);
      orderTA.setEditable(false);
      
      
      crustDD = new JComboBox<String>(crustNames);
      crustDD.setMaximumRowCount(4);
      
      // Item sizes and locations-------------
      nameLabel.setSize(400, 30);
      nameLabel.setLocation(20, 5);
      
      // Reset --------
      resetB.setSize(100, 30);
      resetB.setLocation(300, 250);
              
      // Toppings  ---
      toppingsLabel.setSize(130, 30);
      tomatoCB.setSize(120, 30);
      greenPepperCB.setSize(120, 30);
      blackOlivesCB.setSize(120, 30);
      mushroomsCB.setSize(120, 30);
      extraCheeseCB.setSize(120, 30);
      pepperoniCB.setSize(120, 30);
      sausageCB.setSize(120, 30);
      
      toppingsLabel.setLocation(20, 30);
      tomatoCB.setLocation(30, 70);
      greenPepperCB.setLocation(30, 100);
      blackOlivesCB.setLocation(30, 130);
      mushroomsCB.setLocation(30, 160);
      extraCheeseCB.setLocation(30, 190);
      pepperoniCB.setLocation(30, 220);
      sausageCB.setLocation(30, 250);
      
      // Crust ----
      crustLabel.setSize(100, 30);
      crustDD.setSize(100, 30);
      
      crustLabel.setLocation(150, 30);
      crustDD.setLocation(170, 70);
              
      // Size ----
      sizeLabel.setSize(120, 30);
      smallRB.setSize(120, 30);
      mediumRB.setSize(120, 30);
      largeRB.setSize(120, 30);
      
      sizeLabel.setLocation(280, 30);
      smallRB.setLocation(300, 70);
      mediumRB.setLocation(300, 100);
      largeRB.setLocation(300, 130); 
      
      // Order summary / Text Area
      orderTA.setLocation(20, 300);
      orderTA.setSize(400, 120);
      orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
      
      // adds---------------------------
      c.add(nameLabel);
      
      c.add(resetB);

      c.add(toppingsLabel);
      c.add(tomatoCB);
      c.add(greenPepperCB);
      c.add(blackOlivesCB);
      c.add(mushroomsCB);
      c.add(extraCheeseCB);
      c.add(pepperoniCB);
      c.add(sausageCB);
      
      c.add(sizeLabel);
      c.add(smallRB);
      c.add(mediumRB);
      c.add(largeRB);
      
      c.add(crustLabel);
      c.add(crustDD);
      
      c.add(orderTA);
      // grouping buttons-----------------
      
      sizeGroup = new ButtonGroup();
      sizeGroup.add(smallRB);
      sizeGroup.add(mediumRB);
      sizeGroup.add(largeRB);
      
      // item listeners------------------
      resetB.addActionListener(this);
      
      tomatoCB.addItemListener(this);
      greenPepperCB.addItemListener(this);
      blackOlivesCB.addItemListener(this);
      mushroomsCB.addItemListener(this);
      extraCheeseCB.addItemListener(this);
      pepperoniCB.addItemListener(this);
      sausageCB.addItemListener(this);
      
      crustDD.addItemListener(this);
      
      smallRB.addItemListener(this);
      mediumRB.addItemListener(this);
      largeRB.addItemListener(this);
  }
  public void itemStateChanged(ItemEvent m)
  {
      if(m.getSource() == tomatoCB)
      {        
          if (m.getStateChange() == ItemEvent.SELECTED)
          {
             toppings = toppings.replaceAll("\\. ", ""); // to get rid of the period so we can add one later
              
             toppings = toppings + ", tomatos. ";
             toppings = toppings.replaceAll(": , ", ": "); // to make sure we don't have any commas after "Toppings: "
             topCount++;
             orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
          }
          
          if (m.getStateChange() == ItemEvent.DESELECTED)
          {
              toppings = toppings.replaceAll("tomatos", "");
              
              toppings = toppings.replaceAll(", , ", ", ");
              toppings = toppings.replaceAll(", . ", ". ");
              toppings = toppings.replaceAll(":. " , ": ");
              toppings = toppings.replaceAll(": . " , ": ");
              topCount--;
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
          }
          
      }  
      if(m.getSource() == greenPepperCB)
      {        
          if (m.getStateChange() == ItemEvent.SELECTED)
          {
              toppings = toppings.replaceAll("\\. ", ""); // to get rid of the period so we can add one later
              
              toppings = toppings + ", green peppers. " ;
              toppings = toppings.replaceAll(": , ", ": "); // to make sure we don't have any commas after "Toppings: "
              topCount++;
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
              
          }
          
          if (m.getStateChange() == ItemEvent.DESELECTED)
          {
              toppings = toppings.replaceAll("green peppers", "");
              toppings = toppings.replaceAll(", , ", ", ");
              toppings = toppings.replaceAll(", . ", ". ");
              toppings = toppings.replaceAll(": . ", ": ");
              topCount--;
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
              
          }
          
      }  
      if(m.getSource() == blackOlivesCB)
      {        
          if (m.getStateChange() == ItemEvent.SELECTED)
          {
             toppings = toppings.replaceAll("\\. ", ""); // to get rid of the period so we can add one later
              
             toppings = toppings + ", black olives. ";
             toppings = toppings.replaceAll(": , ", ": "); // to make sure we don't have any commas after "Toppings: "
             topCount++; 
             orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
             
          }
          
          if (m.getStateChange() == ItemEvent.DESELECTED)
          {
              toppings = toppings.replaceAll("black olives", "");
              
              toppings = toppings.replaceAll(", , ", ", ");
              toppings = toppings.replaceAll(", . ", ". ");
              toppings = toppings.replaceAll(":. " , ": ");
              toppings = toppings.replaceAll(": . " , ": ");
              topCount--;
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
              
          }
          
      }
      if(m.getSource() == mushroomsCB)
      {        
          if (m.getStateChange() == ItemEvent.SELECTED)
          {
             toppings = toppings.replaceAll("\\. ", ""); // to get rid of the period so we can add one later
              
             toppings = toppings + ", mushrooms. ";
             toppings = toppings.replaceAll(": , ", ": "); // to make sure we don't have any commas after "Toppings: "
             topCount++; 
             orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));

          }
          
          if (m.getStateChange() == ItemEvent.DESELECTED)
          {
              toppings = toppings.replaceAll("mushrooms", "");
              
              toppings = toppings.replaceAll(", , ", ", ");
              toppings = toppings.replaceAll(", . ", ". ");
              toppings = toppings.replaceAll(":. " , ": ");
              toppings = toppings.replaceAll(": . " , ": ");
              topCount--;
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
              
          }
          
      }
      if(m.getSource() == extraCheeseCB)
      {        
          if (m.getStateChange() == ItemEvent.SELECTED)
          {
             toppings = toppings.replaceAll("\\. ", ""); // to get rid of the period so we can add one later
              
             toppings = toppings + ", extra cheese. ";
             toppings = toppings.replaceAll(": , ", ": "); // to make sure we don't have any commas after "Toppings: "
             topCount++; 
             orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
             
          }
          
          if (m.getStateChange() == ItemEvent.DESELECTED)
          {
              toppings = toppings.replaceAll("extra cheese", "");
              
              toppings = toppings.replaceAll(", , ", ", ");
              toppings = toppings.replaceAll(", . ", ". ");
              toppings = toppings.replaceAll(":. " , ": ");
              toppings = toppings.replaceAll(": . " , ": ");
              topCount--;
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
              
          }
          
      }
      if(m.getSource() == pepperoniCB)
      {        
          if (m.getStateChange() == ItemEvent.SELECTED)
          {
             toppings = toppings.replaceAll("\\. ", ""); // to get rid of the period so we can add one later
              
             toppings = toppings + ", pepperoni. ";
             toppings = toppings.replaceAll(": , ", ": "); // to make sure we don't have any commas after "Toppings: "
             topCount++; 
             orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
             
          }
          
          if (m.getStateChange() == ItemEvent.DESELECTED)
          {
              toppings = toppings.replaceAll("pepperoni", "");
              
              toppings = toppings.replaceAll(", , ", ", ");
              toppings = toppings.replaceAll(", . ", ". ");
              toppings = toppings.replaceAll(":. " , ": ");
              toppings = toppings.replaceAll(": . " , ": ");
              topCount--;
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
              
          }
          
      }
      if(m.getSource() == sausageCB)
      {        
          if (m.getStateChange() == ItemEvent.SELECTED)
          {
             toppings = toppings.replaceAll("\\. ", ""); // to get rid of the period so we can add one later
              
             toppings = toppings + ", sausage. ";
             toppings = toppings.replaceAll(": , ", ": "); // to make sure we don't have any commas after "Toppings: "
             topCount++; 
             orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
             
          }
          
          if (m.getStateChange() == ItemEvent.DESELECTED)
          {
              toppings = toppings.replaceAll("sausage", "");
              
              toppings = toppings.replaceAll(", , ", ", ");
              toppings = toppings.replaceAll(", . ", ". ");
              toppings = toppings.replaceAll(":. " , ": ");
              toppings = toppings.replaceAll(": . " , ": ");
              topCount--;
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
              
          }
          
      }
      if(m.getSource() == crustDD)
      {
          if(crustNames[crustDD.getSelectedIndex()] == thinCrust)
          {
              crust = crust.replaceAll(thinCrust, "");
              crust = crust.replaceAll(deepDishCrust, "");
              crust = crust.replaceAll(panCrust, "");
              
              crust = crust + "Thin Crust";
              
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
          }
      }
      if(m.getSource() == crustDD)
      {
          if(crustNames[crustDD.getSelectedIndex()] == deepDishCrust)
          {
              crust = crust.replaceAll(thinCrust, "");
              crust = crust.replaceAll(deepDishCrust, "");
              crust = crust.replaceAll(panCrust, "");
              
              crust = crust + "Deep Dish";
              
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
          }
      }
      if(m.getSource() == crustDD)
      {
          if(crustNames[crustDD.getSelectedIndex()] == panCrust)
          {
              crust = crust.replaceAll(thinCrust, "");
              crust = crust.replaceAll(deepDishCrust, "");
              crust = crust.replaceAll(panCrust, "");
              
              crust = crust + panCrust;
              
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
          }
      }
      if(m.getSource() == crustDD)
      {
          if(crustNames[crustDD.getSelectedIndex()] == blankCrust)
          {
              crust = crust.replaceAll(thinCrust, "");
              crust = crust.replaceAll(deepDishCrust, "");
              crust = crust.replaceAll(panCrust, "");
                              
              orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
          }
      }
      if(m.getSource() == smallRB)
      {        
         size = "Size: Small";
         sizeCount = 1;
         orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
      }
      
      if(m.getSource() == mediumRB)
      {        
         size = "Size: Medium";
         sizeCount = 2;
         orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
      }
      
      if(m.getSource() == largeRB)
      {        
         size = "Size: Large";
         sizeCount = 3;
         orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
      }
      
  }
  private String totalStr(int n, int m) // n refers to size, m is number of toppings
  {
      double cost = 0;
      if(n == 0)
      {
          cost = 0;
      }
      if(n == 1)
      {
          cost = 6.5;
      }
      if(n == 2)
      {
          cost = 8.50;
      }
      if(n == 3)
      {
          cost = 10.00;
      }
      
      cost = cost + (m * 1.5);
      return String.format("Total: $%.2f", cost);
  }
  
  public void actionPerformed(ActionEvent m)
  {
              
      //smallRB.setSelected(false);  // Doesn't work?  not sure why not...
      //mediumRB.setSelected(false);
      //largeRB.setSelected(false);
      
      sizeGroup.clearSelection();
      
      crustDD.setSelectedIndex(0);
      
      tomatoCB.setSelected(false);
      greenPepperCB.setSelected(false);
      blackOlivesCB.setSelected(false);
      mushroomsCB.setSelected(false);
      extraCheeseCB.setSelected(false);
      pepperoniCB.setSelected(false);
      sausageCB.setSelected(false);
      
      topCount = 0;
      sizeCount = 0;
      
      String toppings = "Toppings: ";
      String crust = "Crust: ";
      String size = "Size: ";
      
      orderTA.setText(size + "\n" + crust + "\n" + toppings + "\n" + dashes + "\n" + totalStr(sizeCount, topCount));
      
  }
}
*/

/*{
	private JTextField userTF, countTF;
    private JLabel titleLabel, inputLabel, countLabel;
    private JButton calculateB, resetB;
    
    private int count = 0;
    private int index = 0;
    
    public void init() 
    {
        Container c = getContentPane();
        c.setLayout(null);
        
        titleLabel = new JLabel("Pharmacy Manager");
        inputLabel = new JLabel("Enter in a word: ");
        countLabel = new JLabel("Vowels: ");
        
        resetB = new JButton("Reset");
        calculateB = new JButton("Calculate");       
        
               
        titleLabel.setSize(100, 30);
        titleLabel.setLocation(20, 5);
        inputLabel.setSize(100, 30);
        inputLabel.setLocation(20, 35);
        countLabel.setSize(100, 30);
        countLabel.setLocation(20, 65);
        //Buttons
        resetB.setSize(100, 30);
        resetB.setLocation(120, 95);
                
        calculateB.setSize(100, 30);
        calculateB.setLocation(120, 125);
        
        userTF = new JTextField(6);
        userTF.setLocation(120, 35);
        userTF.setSize(100, 30);
        
        countTF = new JTextField(6);
        countTF.setLocation(120, 65);
        countTF.setSize(100, 30);
        countTF.setEditable(false);
        
        // adds---------------------------
        c.add(titleLabel);
        c.add(inputLabel);
        c.add(countLabel);
        
        c.add(resetB);
        c.add(calculateB);
        
        c.add(userTF);
        c.add(countTF);
        
        resetB.addActionListener(this);
        calculateB.addActionListener(this);
        
        
    }
    public void actionPerformed(ActionEvent m)
    {
        if(m.getActionCommand().equals("Calculate"))
        {
            String userStr = userTF.getText();
            countTF.setText("" + vowelCount(userStr));
        }
        if(m.getActionCommand().equals("Reset"))
        {
            userTF.setText("");
            countTF.setText("");
            count =  0;
            index = 0;
        }
    }
    public int vowelCount(String x)
    {
        int length = x.length();
        length--;
        if(index > length)
            return count;
        char ch1 = x.charAt(index);
        
        //ch1 = toLowerCase(ch1);
        
        if(ch1 == 'a' || ch1 == 'e' || ch1 == 'i' || ch1 == 'o' || ch1 == 'u')
        {
            count++;
            index++;
            return vowelCount(x);
        }
        index++;
        return vowelCount(x);
    }    
    
	//@Override
	//public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	//}

}
*/
