import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import com.model.FloorDetails;

public interface ServerService extends Remote{

	public String readRESTAPI() throws RemoteException;
	public void addSensor(int floorNo, String roomNo) throws RemoteException;
	public void editSensor(HashMap<String, String> row) throws RemoteException;
	public void sendAlert(String messageBody) throws RemoteException;
}
