
public class TradeOrder {
	Trader trader;
	java.lang.String symbol;
	boolean buyOrder;
	boolean marketOrder;
	int shares;
	double price;

//Constructor that takes in the trader, symbol, price, number of shares, whether or not it is a buy order, 
// and whether or not it is a market order
	TradeOrder(Trader trader, java.lang.String symbol, 
			boolean buyOrder, boolean marketOrder, int numShares, double price)
   {
	   this.trader = trader;
	   this.symbol = symbol;
	   this.buyOrder = buyOrder;
	   this.marketOrder = marketOrder;
	   shares = numShares;
	   this.price = price;
   }
//Returns the trader for this trade order.
	public Trader getTrader() {
		return trader;
	}
//Returns the stock symbol for this trade order.
	public java.lang.String getSymbol() {
		return symbol;
	}
//Returns true if this is a buy order; otherwise returns false.
	public boolean isBuy() {
		return buyOrder;
	}
//Returns true if this is a sell order; otherwise returns false.
	public boolean isSell() {
		return !buyOrder;
	}
//returns true number of shares to be traded in this order
	public int getShares() {
		return shares;
	}
//Returns the price per share for this trade order (used by a limit order).
	public double getPrice() {
		return price;
	}
//Returns true if this is a limit order; otherwise returns false.
	public boolean isLimit() {
	    return !marketOrder;
	}
//Returns true if this is a market order; otherwise returns false.
	public boolean isMarket() {
		return marketOrder;
	}

	public void subtractShares(int shares) {
		this.shares = this.shares - shares;
	}
}