import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface TraderInterf extends Remote  {
    List<Item> listItems() throws RemoteException;
    
    public void sell(String item, float value) throws RemoteException, RejectedException;
//    public void buy(float value) throws RemoteException, RejectedException;
}
