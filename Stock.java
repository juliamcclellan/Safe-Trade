/* 
 * File: Stock.java
 * Name: Giacalone/Kelly/McClellan/Wing
 * Date: 11/11/2015
 * ------------------------------------
 * Represents a stock in the SafeTrade project.
 */

import java.util.Iterator;
import java.util.PriorityQueue;

public class Stock {
	private String symbol, name;
	private double lowPrice, highPrice, lastPrice;
	private int volume;
	private PriorityQueue<TradeOrder> sellOrders, buyOrders;
	
	/*
	 * Constructs a new stock with a given symbol, company name, and starting price. Sets low price, high price, and last price to the same opening price. 
	 * Sets "day" volume to zero. Initializes a priority queue for sell orders to an empty PriorityQueue with a PriceComparator configured for comparing orders
	 * in ascending order; initializes a priority queue for buy orders to an empty PriorityQueue with a PriceComparator configured for comparing orders in 
	 * descending order.
	 */
	public Stock(String symbol, String name, double price){
		this.symbol = symbol;
		this.name = name;
		this.lowPrice = price;
		this.highPrice = price;
		this.lastPrice = price;
		this.volume = 0;
		this.sellOrders = new PriorityQueue<TradeOrder>(new PriceComparator(false));
		this.buyOrders = new PriorityQueue<TradeOrder>(new PriceComparator(true));
	}
	
	/*
	 * Returns a quote string for this stock. The quote includes: the company name for this stock; the stock symbol; last sale price; the lowest and highest 
	 * day prices; the lowest price in a sell order (or "market") and the number of shares in it (or "none" if there are no sell orders); the highest price in 
	 * a buy order (or "market") and the number of shares in it (or "none" if there are no buy orders)
	 */
	public String getQuote(){
		TradeOrder highBuy = null;
		Iterator<TradeOrder> iter = buyOrders.iterator();
		if(iter.hasNext())
		{
			highBuy = iter.next();
		}
		while(iter.hasNext()){
			TradeOrder order = iter.next();
			if(order.getPrice() > highBuy.getPrice())
				highBuy = order;
		}
		String highestBuy;
		if(highBuy == null)
			highestBuy = "Bid: none";
		else
			highestBuy = "Bid: " + highBuy.getPrice() + "size: " + highBuy.getShares();
		TradeOrder lowSell = null;
		iter = sellOrders.iterator();
		if(iter.hasNext())
		{
			lowSell = iter.next();
		}
		while(iter.hasNext()){
			TradeOrder order = iter.next();
			if(order.getPrice() > lowSell.getPrice())
				highBuy = order;
		}
		String lowestSell;
		if(lowSell == null)
			lowestSell = "Ask: none";
		else
			lowestSell = "Ask: " + highBuy.getPrice() + "size: " + highBuy.getShares();
		return name + " (" + symbol + ")\nPrice: " + lastPrice + " hi: " + highPrice + " lo: " + lowPrice
				+ " vol: " + volume + "\n" + highestBuy + " " + lowestSell;
	}
	
	/*
	 * Places a trading order for this stock. Adds the order to the appropriate priority queue depending on whether this is a buy or sell order. Notifies the 
	 * trader who placed the order that the order has been placed, by sending a message to that trader. Executes pending orders by calling executeOrders.
	 */
	public void placeOrder(TradeOrder order){
		String orderCommand = "";
			if(order.isBuy()){
				buyOrders.add(order);
				orderCommand = "Buy";
			}
			else if(order.isSell()){
				sellOrders.add(order);
				orderCommand = "Sell";
			}
			String price = "$" + order.getPrice();
			if(price.equals("$0.0"))
				price = "$" + lastPrice;
			Trader t = order.getTrader();
			t.receiveMessage("New Order: " + orderCommand + " " + symbol + "(" + name + ")\n" + order.getShares() + " shares at " + price);
			executeOrders();
	}
	
	/*
	 * Executes all orders possible.
	 */
	private void executeOrders()
	{
		if(sellOrders.size() == 0 || buyOrders.size() == 0) return;
		
		Iterator<TradeOrder> buyIterator = buyOrders.iterator();
		for(TradeOrder buy = buyIterator.next(); buyIterator.hasNext() && sellOrders.size() != 0; buy = buyIterator.next())
		{
			double buyPrice;
			if(buy.isLimit()) buyPrice = buy.getPrice();
			else buyPrice = lowPrice;
			boolean complete = false;
			Iterator<TradeOrder> sellIterator = sellOrders.iterator();
			for(TradeOrder sell = sellIterator.next(); sellIterator.hasNext() && !complete; sell = sellIterator.next())
			{
				double sellPrice;
				if(sell.isLimit()) sellPrice = sell.getPrice();
				else sellPrice = highPrice;
				if(buyPrice <= sellPrice)
				{
					int shares = buy.getShares();
					if(sell.getShares() > shares)
					{
						sell.subtractShares(buy.getShares());
						complete = true;
					}
					if(sell.getShares() == shares)
					{
						sellIterator.remove();
						complete = true;
					}
					if(complete)
					{
						sell.getTrader().receiveMessage(getMessage(sell, shares, sellPrice));
						buy.getTrader().receiveMessage(getMessage(buy, shares, sellPrice));
						buyIterator.remove();
					}
				}
			}
		}
	}
	
	/*
	 * Generates a message to send to a trader when one of their orders has been completed.
	 */
	private String getMessage(TradeOrder order, int shares, double price)
	{
		String msg = "You ";
		if(order.isBuy()) msg += " bought ";
		else msg += " sold ";
		msg += shares + " " + symbol + " at " + price + " amt " + (price * shares);
		return msg;
	}
}