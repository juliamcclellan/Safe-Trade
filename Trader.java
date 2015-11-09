/*
 * File: Trader.java
 * Name: Giacalone/Kelly/McClellan/Wing
 * Date: 11/07/2015
 * ------------------------------------
 * A Trader represents a trader in the StockExchange; it can request quotes and place orders and recieve
 * messages and store them in a mailbox.
 */

import java.lang.ClassCastException;
import java.util.ArrayList;

public class Trader implements Comparable<Trader> {
	
	private Brokerage brokerage;
	private String name;
	private String pswd;
	private ArrayList<String> mailbox;
	private TraderWindow myWindow;
	
	//Constructs a new trader, affiliated with a given brockerage, with a given screen name and password.
	public Trader(Brokerage brokerage, String name, String pswd) {
		this.brokerage = brokerage;
		this.name = name;
		this.pswd = pswd;
		this.mailbox = new ArrayList<String>();
	}
	
	//Returns the screen name for this trader.
	public String getName() {
		return name;
	}
	
	//Returns the password for this trader.
	public String getPassword() {
		return pswd;
	}
	
	//Compares this trader to another by comparing their screen names case blind.
	public int compareTo(Trader other) {
		return this.getName().compareTo(other.getName());
	}
	
	//Indicates whether some other trader is "equal to" this one, based on comparing their screen names case blind. 
	//this method will throw a ClassCastException if other is not an instance of Trader.
	public boolean equals(Object other) {
		if(other instanceof Trader)
			return this.compareTo((Trader) other) == 0;
		else
			throw new ClassCastException();
	}
	
	//Creates a new TraderWindow for this trader and saves a reference to it in myWindow. Removes and displays all
	//the messages, if any, from this trader's mailbox by calling myWindow.show(msg) for each message.
	public void openWindow() {
		myWindow = new TraderWindow(this);
		for(String msg: mailbox)
			myWindow.showMessage(msg);
		while(mailbox.size() != 0)
			mailbox.remove(0);
	}
	
	//Returns true if this trader has any messages in its mailbox.
	public boolean hasMessages() {
		return mailbox.size() > 0;
	}
	
	//Adds msg to this trader's mailbox and displays all messages. If this trader is logged in (myWindow is not null)
	//removes and shows all the messages in the mailbox by calling myWindow.showMessage(msg) for each msg in the mailbox.
	public void receiveMessage(String msg) {
		mailbox.add(msg);
		if(myWindow != null) {
			for(String message: mailbox)
				myWindow.showMessage(message);
			while(mailbox.size() > 0)
				mailbox.remove(0);
		}
	}
	
	//Requests a quote for a given stock symbol from the brokerage by calling brokerage's getQuote.
	public void getQuote(String symbol) {
		brokerage.getQuote(symbol, this);
	}
	
	//Places a given order with the brokerage by calling brokerage's placeOrder.
	public void placeOrder(TradeOrder order) {
		brokerage.placeOrder(order);
	}
	
	//Logs out this trader. Calls brokerage's logout for this trader. Sets myWindow to null (this method is called 
	//from a TraderWindow's window listener when the "close window" button is clicked).
	public void quit() {
		brokerage.logout(this);
		myWindow = null;
	}
	
}