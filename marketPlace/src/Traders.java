import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.StringTokenizer;


public class Traders {
	private String marketName;
	MarketInterf marketobj;
	TraderInterf trader;
	String traderName;
	
	static enum CommandName {
		registertrader, unregistertrader,sell, buy,listitems, help, quit;
	};
	
	public Traders(String marketName) {
		this.marketName = marketName;
		try {
			marketobj = (MarketInterf)Naming.lookup(marketName);
		} catch (Exception e) {
			System.out.println("The runtime failed: " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Connected to market: " + marketName);
	}

	public void run() { 
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print(traderName + "@" + marketName + ">");
			try {
				String input = in.readLine();
				execute(parse(input));
				
			}catch (RejectedException re) {
				System.out.println(re);				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private Command parse(String userInput) {
		if (userInput == null) {
			return null;
		}
		StringTokenizer tokenizer = new StringTokenizer(userInput);
		if (tokenizer.countTokens() == 0) {
			return null;
		}

		CommandName commandName = null;
		String userName = null;
		String item = null;
		float price = 0;
		int userInputTokenNo = 1;

		while (tokenizer.hasMoreTokens()) {
			switch (userInputTokenNo) {
			case 1:
				try {
					String commandNameString = tokenizer.nextToken();
					commandName = CommandName.valueOf(CommandName.class, commandNameString);
				} catch (IllegalArgumentException commandDoesNotExist) {
					System.out.println("Illegal command");
					return null;
				}
				break;
			case 2:
				userName = tokenizer.nextToken();
				break;
			case 3:
				item = tokenizer.nextToken();
				break;
			case 4:
				try {
					price = Float.parseFloat(tokenizer.nextToken());
				} catch (NumberFormatException e) {
					System.out.println("Illegal amount");
					return null;
				}
				break;
			default:
				System.out.println("Illegal command");
				return null;
			}
			userInputTokenNo++;
		}
		return new Command(commandName, userName, item, price);
	}
	
	void execute(Command command) throws RemoteException, RejectedException {
		if (command == null) {
			return;
		}

		switch (command.getCommandName()) {

		case quit:
			System.exit(1);
		case help:
			for (CommandName commandName : CommandName.values()) {
				System.out.println(commandName);
			}
			return;
		}

		// all further commands require a name to be specified
		String userName = command.getUserName();
		if (userName == null) {
			userName = traderName;
		}

		if (userName == null) {
			System.out.println("name is not specified");
			return;
		}

		switch (command.getCommandName()) {
		case registertrader:
			traderName = userName;
			marketobj.registerTrader(userName);
			return;
		case unregistertrader:
			traderName = userName;
			marketobj.unregisterTrader(userName);
			return;
		}

		// all further commands require a Account reference
		TraderInterf tra = marketobj.getTrader(userName);
		if (tra == null) {
			System.out.println(userName + " does not exist in the market.");
			return;
		} else {
			trader = tra;
			traderName = userName;
		}

		switch (command.getCommandName()) {
	
		case sell:
			trader.sell(command.getItem(),command.getPrice());
			break;
	//	case buy:
			//		account.withdraw(command.getItem(),command.getPrice());
			//		break;
		case listitems:
			List<Item> items = trader.listItems();
			System.out.println(items.get(0));
			if(!items.isEmpty()) {
				for (Item item : items) {
					System.out.println(item);
				}
			}
			break;
		default:
			System.out.println("Illegal command");
		}
	}
	
	private class Command {
		private String traderName, item;
		private CommandName commandName;
		private float price;

		private String getUserName() {
			return traderName;
		}

		private CommandName getCommandName() {
			return commandName;
		}
		private String getItem() {
			return item;
		}

		private float getPrice() {
			return price;
		}

		private Command(Traders.CommandName commandName, String userName, String item, float price) {
			this.commandName = commandName;
			this.traderName = userName;
			this.item = item;
			this.price = price;
		}
	}
	
	public static void main(String[] args) {
		String marketName = "Fishmarket";
			new Traders(marketName).run();
		

	}

}
