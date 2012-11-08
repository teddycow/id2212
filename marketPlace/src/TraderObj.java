import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class TraderObj extends UnicastRemoteObject implements TraderInterf  {


	private String traderName;
	public List<Item> itemTable = new ArrayList<Item>();


	public TraderObj(String name) throws RemoteException {
		super();
		this.traderName = name;
	}

	public synchronized void sell(String name, float price) throws RemoteException, 
	RejectedException {
		if (price < 0) {
			throw new RejectedException("Rejected: Trader " + name + 
					": Illegal price: " + price);
		}
		Item item = new Item(name, price);
		itemTable.add(item);
		System.out.println("Transaction: Trader " + traderName + ": selling item: " + name + " price: $" +
				price);
	}

	/*	public synchronized void withdraw(float value) throws RemoteException, 
	RejectedException {
		if (value < 0) {
			throw new RejectedException("Rejected: Account " + name + 
					": Illegal value: " + value);
		}
		if ((balance - value) < 0) {
			throw new RejectedException("Rejected: Account " + name + 
					": Negative balance on withdraw: " +
					(balance - value));
		}
		balance -= value;
		System.out.println("Transaction: Account " + name + ": withdraw: $" +
				value + ", balance: $" + balance);
	}
*/
	public List<Item> listItems() {
		return(itemTable);
	}


}
