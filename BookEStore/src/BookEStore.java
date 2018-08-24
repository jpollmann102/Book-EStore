import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
	private int currentBookID;
	private int discount;
	private double subtotal = 0.0;
	
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
				
				String bookID = bookIDLabel.getText();
				String[] q = query(bookID);
				
				if(!q[0].equals(""))
				{
					if(itemCount < 5) discount = 0;
					else if(itemCount < 10) discount = 10;
					else if(itemCount < 15) discount = 15;
					else discount = 20;
					
					itemQuant = Integer.parseInt(quantityField.getText());
					
					itemInfoField.setText(bookID + " " + q[0] + " " + q[1] + " " + itemCount + " " + discount + "% " + "$" + subtotal);
					processItemBtn.setEnabled(false);
					confirmItemBtn.setEnabled(true);
				}
			}
		});
		
		confirmItemBtn.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent e)
			{
				itemCount += itemQuant;
				
				bookIDField.setText("");
				quantityField.setText("");
				
				if(itemCount > 2) itemInfoLabel = new JLabel("Item #" + itemCount + " info:");
				
				bookIDLabel = new JLabel("Enter Book ID for item #" + itemCount + ":");
				quantityLabel = new JLabel("Enter quantity for item #" + itemCount + ":");
				subtotalLabel = new JLabel("Order subtotal for " + (itemCount - 1) + " item(s):");
				
				processItemBtn.setEnabled(true);
				confirmItemBtn.setEnabled(false);
				
				processItemBtn = new JButton("Process Item #" + itemCount);
				confirmItemBtn = new JButton("Confirm Item #" + itemCount);
				
				
			}
		});
		
		viewOrderBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		
		finishOrderBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		
		newOrderBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		
		exitBtn.addActionListener(new ActionListener() {
			
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
			String thisID = sc.next();
			if(thisID.equals(id))
			{
				// success, found in inventory
				ret[0] = sc.next();
				ret[1] = sc.next();
				return ret;
			}
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
