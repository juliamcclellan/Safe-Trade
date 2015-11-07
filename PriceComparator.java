/*
 * File: PriceComparator.java
 * Name: Giacalone/Kelly/McClellan/Wing
 * Date: 11/07/2015
 * ------------------------------------
 * A price comparator for trade orders.
 */

import java.util.Comparator;

public class PriceComparator implements Comparator {
	
	private boolean ascending;
	
	//Constructs a price comparator that compares two orders in ascending order
	public PriceComparator() {
		this.ascending = true;
	}
	
	//Constructs a price comparator that compares two orders in ascending or descending order.
	public PriceComparator(boolean asc) {
		this.ascending = asc;
	}
	
	//Compares two trade orders.
	//0 if both orders are market orders;
	//-1 if order1 is market and order2 is limit;
	//1 if order1 is limit and order2 is market;
	public int compare(TradeOrder order1, TradeOrder order2) {
		if(order1.isMarket() && order2.isMarker())
			return 0;
		else if(order1.isMarket() && !order2.isMarker())
			return -1;
		else
			return 1;
	}
	
}
