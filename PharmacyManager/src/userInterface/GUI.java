package userInterface;

import dataLayer.*;
import javax.swing.*;

import java.util.Scanner;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JTextArea displayTA;
	private JSpinner numSpin, expSpin;
	private JLabel medicineL, numberL, expireL;
	private JTextField medicineTF;
	private JButton restockB, saleB, reportB;
	private JRadioButton smallRB, mediumRB, largeRB;
	ButtonGroup sizeGroup;
	
	public void GUILaunch(){
		int col1 = 20;
		int col2 = 330;
		int col3 = 455;
		int row = 20;
		
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
			
		displayTA = new JTextArea();
		displayTA.setWrapStyleWord(true);
		displayTA.setLineWrap(true);
		displayTA.setEditable(false);
		displayTA.setLocation(col1, 20);
		displayTA.setSize(300, 400);
		c.add(displayTA);
		
		///////////////////
		medicineL = new JLabel("Medicine: ");
		medicineL.setSize(125, 25);
		medicineL.setLocation(col2, row);
		c.add(medicineL);
		medicineTF = new JTextField();
		medicineTF.setSize(100,25);
		medicineTF.setLocation(col3, row);
		c.add(medicineTF);
		row += 40;
		////////////////////////////
		expireL = new JLabel("Days until expiration: ");
		SpinnerModel expModel;
		expModel = new SpinnerNumberModel(initial, min, max, step);
		expireL.setSize(125,30);
		expireL.setLocation(col2, row);
		c.add(expireL);
		expSpin = new JSpinner(expModel);
		expSpin.setSize(100,30);
		expSpin.setLocation(col3, row);		
		c.add(expSpin); 
		row += 40;
		///////////////////////
		smallRB = new JRadioButton("Small");
		mediumRB = new JRadioButton("Medium");
		largeRB = new JRadioButton("Large");
		smallRB.setSize(75,25);
		mediumRB.setSize(75,25);
		largeRB.setSize(75,25);
		smallRB.setLocation(col2, row);
		mediumRB.setLocation(col2+75, row);
		largeRB.setLocation(col2+150, row);		
		
		sizeGroup = new ButtonGroup();
	    sizeGroup.add(smallRB);
	    sizeGroup.add(mediumRB);
	    sizeGroup.add(largeRB);
		
		c.add(smallRB);
		c.add(mediumRB);
		c.add(largeRB);
		row += 40;
		///////////////////////
		SpinnerModel numModel;
		numModel = new SpinnerNumberModel(initial, min, max, step);
		
		numberL = new JLabel("Quantity: ");
		numberL.setSize(125,30);
		numberL.setLocation(col2, row);
		c.add(numberL);
		numSpin = new JSpinner(numModel);
		numSpin.setSize(100,30);
		numSpin.setLocation(col3, row);		
		c.add(numSpin);
		row += 40;
		///////////////////
		saleB = new JButton("Sale");
		saleB.setSize(100, 30);
		saleB.setLocation(col3, row);
		c.add(saleB);
		saleB.addActionListener(this);
		row += 40;
		///////////////////////////  
		restockB = new JButton("Restock");
		restockB.setSize(100, 30);
		restockB.setLocation(col3, row);
		c.add(restockB);
		restockB.addActionListener(this);
		row += 40;
		///////////////////////////  
		reportB = new JButton("Report");
		reportB.setSize(100, 30);
		reportB.setLocation(col3, row);
		c.add(reportB);
		reportB.addActionListener(this);
		row += 40;
		////////////////////////////

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
		  int quantity = (int)numSpin.getValue();
		  String quantityStr = String.format("%d", quantity);
		  
	      if (m.getSource() == restockB){
	    		  
	    	  //DataLayer.executeQuery("SELECT * FROM MEDICINE");

		      //String result = DataLayer.executeQuery("SELECT * FROM MEDICINE").toString();
		      //String next = medicineTF.getText();
		      displayTA.setText(medicine + "\n" + quantityStr + " " + size);	    	  
	      }
	      if(m.getSource() == saleB){
	    	  //String result = commandHandler.executeNonScalar("SELECT * FROM MEDICINE").toString();
		      String next = medicineTF.getText();
		      displayTA.setText(next);  
	      }
	      if(m.getSource() == reportB){
	    	  //String result = d.executeNonScalar("SELECT * FROM MEDICINE").toString();
		      int next = (int)numSpin.getValue();
		      String str = String.format("%d", next);
		      
		      displayTA.setText(str);  
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