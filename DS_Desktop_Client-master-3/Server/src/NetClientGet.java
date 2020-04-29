import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;


public class NetClientGet {
	
    public static void main(String[] args) {
        
    	try {
    		ReadRESTAPI readRESTAPI = new ReadRESTAPI();
    		
    		Naming.rebind("rmi://localhost/RMIServer", readRESTAPI);
    		
    		System.out.println("Server started");
    	} catch (RemoteException | MalformedURLException e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    }
}

