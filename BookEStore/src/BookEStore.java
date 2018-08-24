import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* Name: Joshua Pollmann
 * Course: CNT4714 - Fall 2018
 * Assignment title: Program 1 - Eent-driven Programming
 * Date: Sunday September 9, 2018
 */
public class BookEStore extends Frame implements ActionListener 
{
	private Label nbrItemsLabel;
	private Label bookIDLabel;
	private Label quantityLabel;
	private Label itemInfoLabel;
	private Label subtotalLabel;
	
	private TextField nbrItemsField;
	private TextField bookIDField;
	private TextField quantityField;
	private TextField itemInfoField;
	private TextField subtotalField;
	
	private Button processItemBtn;
	private Button confirmItemBtn;
	private Button viewOrderBtn;
	private Button finishOrderBtn;
	private Button newOrderBtn;
	private Button exitBtn;
	
	private Panel topPanel;
	private Panel bottomPanel;
	
	private int itemCount = 0;
	
	public BookEStore()
	{
		setLayout(new GridLayout());
		topPanel = new Panel(new GridLayout());
		bottomPanel = new Panel(new FlowLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		nbrItemsLabel = new Label("Enter number of items in this oder:");
		bookIDLabel = new Label("Enter Book ID for item #" + itemCount + ":");
		quantityLabel = new Label("Enter quantity for item #" + itemCount + ":");
		itemInfoLabel = new Label("Item #" + itemCount + " info:");
		subtotalLabel = new Label("Order subtotal for " + itemCount + " item(s):");
		
		nbrItemsField = new TextField();
		bookIDField = new TextField();
		quantityField = new TextField();
		itemInfoField = new TextField();
		subtotalField = new TextField();
		
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
		
		processItemBtn = new Button("Process Item #" + itemCount);
		confirmItemBtn = new Button("Confirm Item #" + itemCount);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new BookEStore();
	}

}
