import java.util.Iterator;
import java.util.PriorityQueue;

public class Stock {
	private String symbol, name;
	private double lowPrice, highPrice, lastPrice;
	private int volume;
	private PriorityQueue<TradeOrder> sellOrders, buyOrders;
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
	}
}