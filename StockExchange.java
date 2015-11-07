/*
 * File: StockExchange.java
 * Name: Giacalone/Kelly/McClellan/Wing
 * Date: 11/07/2015
 * ------------------------------------
 * This class contains a hashmap of all the stocks in the trading system
 */

import java.util.HashMap;

public class StockExchange {
	
	private HashMap<String, Stock> stocks;
	
	//Constructs a new stock exchange object. Initializes listed stocks to an empty map (a HashMap).
	public StockExchange() {
		stocks = new HashMap<String, Stock>();
	}
	
	//Adds a new stock with given parameters to the listed stocks.
	public void listStock(java.lang.String symbol, java.lang.String name, double price) {
		stocks.add(symbol, new Stock(symbol, name, price));
	}
	
	//Returns a quote for a given stock.
	public String getQuote(java.lang.String symbol) {
		return stocks.get(symbol).getQuote();
	}
	
	//Places a trade order by calling stock.placeOrder for the stock specified by the stock symbol in the trade order.
	public void placeOrder(TradeOrder order) {
		stocks.get(order.getSymbol()).placeOrder();
	}
	
}
