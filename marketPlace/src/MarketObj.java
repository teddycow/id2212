import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("serial")
public class MarketObj extends UnicastRemoteObject implements MarketInterf {
	private String market;
	private Map<String, TraderInterf> traders = new HashMap<String, TraderInterf>();



	public MarketObj(String market)  throws RemoteException  {
		super();
		this.market = market;
	}

	public synchronized TraderInterf registerTrader(String name) throws RemoteException, RejectedException {
		TraderObj trader = (TraderObj) traders.get(name);
		if (trader != null) {
			System.out.println("Account [" + name + "] exists!!!");
			throw new RejectedException("Rejected: Market: " + market +
					" Trader for: " + name + " already exists: " + trader);
		}
		trader = new TraderObj(name);
		traders.put(name, trader);
		System.out.println("Market: " + market + " Trader: " + trader +
				" has been registered for " + name);
		return trader;
	}

	public synchronized boolean unregisterTrader(String name) {
		if (traders.get(name) == null) {
			return false;
		}
		traders.remove(name);
		System.out.println("Market: " + market +
				" Trader for " + name + " has been unregistered");
		return true;
	}
	
	public synchronized TraderInterf getTrader(String name) {
		return traders.get(name);
	}
}
