
public class Market {

	public Market(String marketName) {
		try {
			MarketInterf marketobj = new MarketObj(marketName);
			// Register the newly created object at rmiregistry.
			java.rmi.Naming.rebind(marketName, marketobj);
			System.out.println(marketName + " is open.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String marketName;
		if (args.length > 0) {
			marketName = args[0];
		} else {
			marketName = "Fishmarket";
		}

		new Market(marketName);

	}
}
