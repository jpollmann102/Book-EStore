import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/* Name: Joshua Pollmann
 * Course: CNT4714 - Fall 2018
 * Assignment title: Program 1 - Event-driven Programming
 * Date: Sunday September 9, 2018
 */
public class BookEStore extends JFrame implements ActionListener 
{
	
	private JLabel nbrItemsLabel;
	private JLabel bookIDLabel;
	private JLabel quantityLabel;
	private JLabel itemInfoLabel;
	private JLabel subtotalLabel;
	
	private JTextField nbrItemsField;
	private JTextField bookIDField;
	private JTextField quantityField;
	private JTextField itemInfoField;
	private JTextField subtotalField;
	
	private JButton processItemBtn;
	private JButton confirmItemBtn;
	private JButton viewOrderBtn;
	private JButton finishOrderBtn;
	private JButton newOrderBtn;
	private JButton exitBtn;
	
	private JPanel topPanel;
	private JPanel bottomPanel;
	
	private Scanner sc;
	
	private int itemCount = 1;
	private int itemsInOrder;
	private int itemQuant;
	private int discount;
	private double curItemPrice;
	private double tempSubtotal = 0.0;
	private double subtotal = 0.0;
	private String addToTransaction;
	
	private String[] order;
	private String[][] transaction;
	private int orderIdx = 0;
	
	public BookEStore()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		topPanel = new JPanel(new GridLayout(5,2,7,7));
		topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		topPanel.setBackground(Color.YELLOW);
		bottomPanel = new JPanel(new FlowLayout());
		bottomPanel.setBackground(Color.BLUE);
		this.add(topPanel, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		nbrItemsLabel = new JLabel("Enter number of items in this order:");
		bookIDLabel = new JLabel("Enter Book ID for item #" + itemCount + ":");
		quantityLabel = new JLabel("Enter quantity for item #" + itemCount + ":");
		itemInfoLabel = new JLabel("Item #" + itemCount + " info:");
		subtotalLabel = new JLabel("Order subtotal for " + (itemCount - 1) + " item(s):");
		
		nbrItemsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		bookIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		itemInfoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		subtotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		nbrItemsField = new JTextField();
		bookIDField = new JTextField();
		quantityField = new JTextField();
		itemInfoField = new JTextField();
		subtotalField = new JTextField();
		
		itemInfoField.setEditable(false);
		subtotalField.setEditable(false);
		
		topPanel.add(nbrItemsLabel);
		topPanel.add(nbrItemsField);
		topPanel.add(bookIDLabel);
		topPanel.add(bookIDField);
		topPanel.add(quantityLabel);
		topPanel.add(quantityField);
		topPanel.add(itemInfoLabel);
		topPanel.add(itemInfoField);
		topPanel.add(subtotalLabel);
		topPanel.add(subtotalField);
		
		processItemBtn = new JButton("Process Item #" + itemCount);
		confirmItemBtn = new JButton("Confirm Item #" + itemCount);
		viewOrderBtn = new JButton("View Order");
		finishOrderBtn = new JButton("Finish Order");
		newOrderBtn = new JButton("New Order");
		exitBtn = new JButton("Exit");
		
		confirmItemBtn.setEnabled(false);
		viewOrderBtn.setEnabled(false);
		finishOrderBtn.setEnabled(false);
		
		bottomPanel.add(processItemBtn);
		bottomPanel.add(confirmItemBtn);
		bottomPanel.add(viewOrderBtn);
		bottomPanel.add(finishOrderBtn);
		bottomPanel.add(newOrderBtn);
		bottomPanel.add(exitBtn);
		
		processItemBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				if(nbrItemsField.equals(""))
				{
					// need the number of items in the order
					JOptionPane.showMessageDialog(null, "Please enter the number of items in this order");
					return;
				}else if(quantityField.equals(""))
				{
					// need the number of the item to be ordered
					JOptionPane.showMessageDialog(null, "Please enter the quantity for the current item");
					return;
				}else
				{
					itemsInOrder = Integer.parseInt(nbrItemsField.getText());
					nbrItemsField.setEditable(false);
				}
				
				if(order == null) order = new String[itemsInOrder];
				if(transaction == null) transaction = new String[itemsInOrder][3];
				
				String bookID = bookIDField.getText();
				String[] q = query(bookID);
				
				if(!q[0].equals(""))
				{
					itemQuant = Integer.parseInt(quantityField.getText());
					
					if(itemQuant < 5) discount = 0;
					else if(itemQuant < 10) discount = 10;
					else if(itemQuant < 15) discount = 15;
					else discount = 20;
					
					curItemPrice = Double.parseDouble(q[1]);
					
					tempSubtotal += curItemPrice - (curItemPrice * ((double) discount / 10.0));
					tempSubtotal *= (double) itemQuant;
					
					String tempSubtotalString = String.format("%.2f", tempSubtotal);
					
					addToTransaction = String.format("%s, %s, %s, %d, %d, %s", bookID, q[0], q[1], itemQuant, discount, tempSubtotalString);
					
					itemInfoField.setText(bookID + " " + q[0] + " $" + q[1] + " " + itemQuant + " " + discount + "% " + "$" + tempSubtotalString);
					processItemBtn.setEnabled(false);
					confirmItemBtn.setEnabled(true);
				}
			}
		});
		
		confirmItemBtn.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, "Item #" + itemCount + " accepted");
				itemCount++;
				
				order[orderIdx] = (orderIdx + 1) + ". " + itemInfoField.getText();
				transaction[orderIdx][1] = addToTransaction;
				orderIdx++;
				
				itemQuant = 0;
				
				subtotal += tempSubtotal;
				tempSubtotal = 0;
				
				bookIDField.setText("");
				quantityField.setText("");
				
				if(itemCount > 2) itemInfoLabel = new JLabel("Item #" + itemCount + " info:");
				
				bookIDLabel.setText("Enter Book ID for item #" + itemCount + ":");
				quantityLabel.setText("Enter quantity for item #" + itemCount + ":");
				subtotalLabel.setText("Order subtotal for " + (itemCount - 1) + " item(s):");
				
				String subtotalString = String.format("%.2f", subtotal);
				subtotalField.setText("$" + subtotalString);
				
				viewOrderBtn.setEnabled(true);
				finishOrderBtn.setEnabled(true);
				
				if((itemCount - 1) >= itemsInOrder)
				{
					// done with order
					processItemBtn.setEnabled(false);
					confirmItemBtn.setEnabled(false);
					bookIDLabel.setText("");
					bookIDField.setEnabled(false);
					quantityLabel.setText("");
					quantityField.setEnabled(false);
				}else
				{
					processItemBtn.setEnabled(true);
					confirmItemBtn.setEnabled(false);
					
					processItemBtn.setText("Process Item #" + itemCount);
					confirmItemBtn.setText("Confirm Item #" + itemCount);
				}
			}
		});
		
		viewOrderBtn.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e)
			{
				String viewOrder = "";
				
				int i = 0;
				while(i < orderIdx)
				{
					viewOrder = viewOrder.concat(order[i] + "\n");
					i++;
				}
				
				JOptionPane.showMessageDialog(null, viewOrder);
			}
		});
		
		finishOrderBtn.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e)
			{
				String invoice = "";
				DateFormat dateFormat = new SimpleDateFormat("m/dd/yy h:mm:ss a");
				Date date = new Date();
				invoice = invoice.concat("Date: " + dateFormat.format(date) + " EDT\n\n");
				invoice = invoice.concat("Number of line items: " + itemsInOrder + "\n\n");
				invoice = invoice.concat("Item# / ID / Title / Price / Qty / Disc % / Subtotal:\n\n");
				
				int i = 0;
				while(i < orderIdx)
				{
					invoice = invoice.concat(order[i] + "\n");
					i++;
				}
				
				invoice = invoice.concat("\n\n\n");
				String subtotalString = String.format("%.2f", subtotal);
				invoice = invoice.concat("Order subtotal: $" + subtotalString + "\n\n");
				invoice = invoice.concat("Tax rate: 6%\n\n");
				
				double tax = 0.06 * subtotal;
				String taxString = String.format("%.2f", tax);
				
				invoice = invoice.concat("Tax amount: $" + taxString + "\n\n");
				
				String totalString = String.format("%.2f", (subtotal + tax));
				invoice = invoice.concat("Order total: $" + totalString + "\n\n");
				invoice = invoice.concat("Thanks for shopping at the Ye Olde Book Shoppe");
				
				JOptionPane.showMessageDialog(null, invoice);
				
				// generate transaction id
				dateFormat = new SimpleDateFormat("DDMMYYYYHHMM");
				long transactionID = Long.parseLong(dateFormat.format(date));
				System.out.println(transactionID);
				
				dateFormat = new SimpleDateFormat("h:mm:ss a");
				String transactionDate = dateFormat.format(date) + " EDT";
				
				i = 0;
				while(i < orderIdx)
				{
					transaction[orderIdx][0] = String.format("%f", transactionID);
					transaction[orderIdx][2] = transactionDate;
					i++;
				}
				
				try {
					PrintStream out = new PrintStream(new FileOutputStream("transactions.txt", true));
					System.setOut(out);
					
					i = 0;
					while(i < orderIdx)
					{
						String output = String.format("%s, %s %s\n", transaction[orderIdx][0], transaction[orderIdx][1], transaction[orderIdx][2]);
						out.println(output);
						i++;
					}
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				newTransaction();
			}
		});
		
		newOrderBtn.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e)
			{
				newTransaction();
			}
		});
		
		exitBtn.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		setLocationRelativeTo(null);
		setTitle("Book EStore");
		setResizable(false);
		pack();
		setVisible(true);
	}
	
	public void newTransaction()
	{
		order = null;
		transaction = null;
		itemCount = 1;
		itemQuant = 0;
		orderIdx = 0;
		subtotal = 0;
		tempSubtotal = 0;
		
		processItemBtn.setText("Process Item #" + itemCount);
		confirmItemBtn.setText("Confirm Item #" + itemCount);
		
		bookIDLabel.setText("Enter Book ID for item #" + itemCount + ":");
		quantityLabel.setText("Enter quantity for item #" + itemCount + ":");
		itemInfoLabel.setText("Item #" + itemCount + " info:");
		subtotalLabel.setText("Order subtotal for " + (itemCount - 1) + " item(s):");
		
		nbrItemsField.setText("");
		itemInfoField.setText("");
		subtotalField.setText("");
		
		confirmItemBtn.setEnabled(false);
		viewOrderBtn.setEnabled(false);
		finishOrderBtn.setEnabled(false);
		
		nbrItemsField.setEditable(true);
		bookIDField.setEnabled(true);
		quantityField.setEnabled(true);
	}
	
	public String[] query(String id)
	{
		String[] ret = new String[2];
		
		try {
			sc = new Scanner(new File("inventory.txt"));
			sc.useDelimiter(",|" + System.getProperty("line.separator"));
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "cannot find inventory.txt");
			ret[0] = "";
			ret[1] = "";
			return ret;
		}
		
		while(sc.hasNext())
		{
			String curID = sc.next();

			if(curID.equals(id))
			{
				// found it
				ret[0] = sc.next();
				ret[1] = sc.next();
				return ret;
			}
			
			sc.nextLine();
		}
		
		// could not find in inventory
		ret[0] = "";
		ret[1] = "";
		JOptionPane.showMessageDialog(null, "Book ID " + id + " not in file");
		return ret;
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
		
	}

	public static void main(String[] args)
	{
		new BookEStore();
	}

}
