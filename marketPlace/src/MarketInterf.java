import java.rmi.Remote;
import java.rmi.RemoteException;


public interface MarketInterf  extends Remote  {
	public TraderInterf registerTrader(String name) throws RemoteException, RejectedException;
	public boolean unregisterTrader(String name) throws RemoteException;
	   public TraderInterf getTrader(String name) throws RemoteException;
}
