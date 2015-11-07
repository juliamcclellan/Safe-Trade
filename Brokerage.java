/*
 * Represents a Brokerage which keeps track or registered traders and logged-in traders. Receives requests from traders and relays them to the StockExchange.
 * 
 * Authors: Giacalone/Kelly/McClellan/Wing
 * Version: 11/07/15
 */

import java.util.TreeMap;
import java.util.TreeSet;

public class Brokerage implements Login
{
	StockExchange exchange;
	TreeMap<String, Trader> traders;
	TreeSet<Trader> logged;
	
	/*
	 * Constructs new brokerage affiliated with a given stock exchange. Initializes the map of traders to an empty map (a TreeMap<String, Trader>), keyed by 
	 * trader's name; initializes the set of active (logged-in) traders to an empty set (a TreeSet).
	 */
	public Brokerage(StockExchange exchange)
	{
		this.exchange = exchange;
		traders = new TreeMap<String, Trader>();
		logged = new TreeSet<Trader>();
	}
	
	/*
	 * Tries to register a new trader with a given screen name and password. If successful, creates a Trader object for this trader and adds this trader to 
	 * the map of all traders (using the screen name as the key).
	 * Returns 0 if successful, -1 if name is incorrect length, -2 if password is incorrect length, and -3 if the name is already taken.
	 */
	public int addUser(String name, String password)
	{
		if(name.length() < 4 || name.length() > 10)
		{
			return -1;
		}
		if(password.length() < 2 || name.length() > 10)
		{
			return -2;
		}
		if(traders.get(name) != null)
		{
			return -3;
		}
		traders.put(name, new Trader(this, name, password));
		return 0;
	}
	
	/*
	 * Tries to login a trader with a given screen name and password. If no messages are waiting for the trader, sends a "Welcome to SafeTrade!" message to the 
	 * trader. Opens a dialog window for the trader by calling trader's openWindow() method. Adds the trader to the set of all logged-in traders.
	 * Returns 0 if successful, -1 if screen name not found, -2 if the password is invalid, -3 if user is already logged in.
	 */
	public int login(String name, String password)
	{
		final String welcome = "Welcome to SafeTrade!";
		Trader login = traders.get(name);
		if(login == null)
		{
			return -1;
		}
		if(!login.getPassword().equals(password))
		{
			return -2;
		}
		if(logged.contains(login))
		{
			return -3;
		}
		logged.add(login);
		
		if(!login.hasMessages()) 
		{
			login.receiveMessage(welcome);
		}		
		login.openWindow();
		return 0;
	}
	
	/*
	 * Removes a specified trader from the set of logged-in traders.
	 */
	public void logout(Trader trader)
	{
		logged.remove(trader);
	}
	
	/*
	 * Requests a quote for a given stock from the stock exchange and passes it along to the trader by calling trader's receiveMessage method.
	 */
	public void getQuote(String symbol, Trader trader)
	{
		trader.receiveMessage(exchange.getQuote(symbol));
	}
	
	/*
	 * Places a trade order by calling stock.placeOrder for the stock specified by the stock symbol in the trade order.
	 */
	public void placeOrder(TradeOrder order)
	{
		exchange.placeOrder(order);
	}
}