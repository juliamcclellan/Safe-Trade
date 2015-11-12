/*
 * File: PriceComparator.java
 * Name: Giacalone/Kelly/McClellan/Wing
 * Date: 11/11/2015
 * ------------------------------------
 * A price comparator for trade orders.
 */

import java.util.Comparator;

public class PriceComparator implements Comparator<TradeOrder> {
	
	private boolean ascending;
	
	/*
	 * Constructs a price comparator that compares two orders in ascending order
	 */
	public PriceComparator() {
		this.ascending = true;
	}
	
	/*
	 * Constructs a price comparator that compares two orders in ascending or descending order.
	 */
	public PriceComparator(boolean asc) {
		this.ascending = asc;
	}
	
	/*
	 * Compares two trade orders.
	 * 0 if both orders are market orders;
	 * -1 if order1 is market and order2 is limit;
	 * 1 if order1 is limit and order2 is market;
	 * the difference in prices, rounded to the nearest cent, if both order1 and order2 are limit orders. 
	 * In the latter case, the difference returned is cents1 - cents2 or cents2 - cents1, depending on whether 
	 * this is an ascending or descending comparator (ascending is true or false).
	 */
	public int compare(TradeOrder order1, TradeOrder order2) {
		if(order1.isMarket() && order2.isMarket())
			return 0;
		else if(order1.isMarket() && !order2.isMarket())
			return -1;
		else if(!order1.isMarket() && order2.isMarket())
			return 1;
		else { //returns the difference in CENTS
			if(ascending) //if ascending mode
				return (int) ((order1.getPrice() * 100) - (order2.getPrice() * 100));
			else //if descending mode
				return (int) ((order2.getPrice() * 100) - (order1.getPrice() * 100));
		}
	}
	
}
