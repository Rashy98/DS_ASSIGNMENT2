import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.model.FloorDetails;

public class ClientMain {
	
	ServerService serverService;
	public static ArrayList<FloorDetails> initialList;
	
	/**
	 * get all the sensor details
	 * @return floorDetails
	 */
	public ArrayList<FloorDetails> getSensors(){
		
		ArrayList<FloorDetails> floorDetails = new ArrayList<FloorDetails>();
		
		try {
			serverService = (ServerService) Naming.lookup("rmi://localhost/RMIServer");
			
			String output = serverService.readRESTAPI();
			
			JSONObject jsonObject = new JSONObject(output);
          
			JSONArray array= jsonObject.getJSONArray("data");
          
			for(int i = 0; i < array.length(); i++) {
      	
				FloorDetails details = new FloorDetails();
				details.setId(array.getJSONObject(i).getInt("Id"));
				details.setStatus(array.getJSONObject(i).getString("status"));
				details.setFloorNo(array.getJSONObject(i).getInt("floorNo"));
				details.setRoomNo(array.getJSONObject(i).getString("roomNo"));
				details.setSmokeLevel(array.getJSONObject(i).getInt("smokeLevel"));
				details.setCo2Level(array.getJSONObject(i).getInt("Co2Level"));
          
				floorDetails.add(details);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return floorDetails;
	}
	
	//sending the room number and floor number to rmi server to add a new sensor
	public void AddSensor(String roomNo, String floorNumber) {
		
		try {
			serverService = (ServerService) Naming.lookup("rmi://localhost/RMIServer");
			
			serverService.addSensor(Integer.parseInt(floorNumber), roomNo);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	//sending new values to edit sensor details
	public void EditSensor(HashMap<String, String> row) {
		try {
			serverService = (ServerService) Naming.lookup("rmi://localhost/RMIServer");
			
			serverService.editSensor(row);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//sending initial alert message body to rmi server
	public void sendInitialAlert(ArrayList<FloorDetails> alertList) {
		System.getProperty("java.security.policy");
		String alertMessage = "";
		initialList = alertList;
		
		try {
			serverService = (ServerService) Naming.lookup("rmi://localhost/RMIServer");
			
			if(initialList.size() > 0) {
			
				for(int i = 0; i < initialList.size(); i++) {
					alertMessage = alertMessage + "FloorNo: " + initialList.get(i).getFloorNo() + "\nRoomNo: " + initialList.get(i).getRoomNo() + 
							"\nCO2Level: " + initialList.get(i).getCo2Level() + "\nSmokeLevel: " + initialList.get(i).getSmokeLevel() + "\n\n";
				}
				
				serverService.sendAlert(alertMessage);
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sending alert message if new sensor fired
	 * maintain the list of rooms with higher CO2 and smoke levels
	 */
	public void sendNewAlert() {
		
		//getting new sensor details
		ArrayList<FloorDetails> list = getSensors();
		String alertMessage = "";
		
		for(int i = 0; i < list.size(); i++) {
			
			int id = list.get(i).getId();
			
			//finding the rooms with higher CO2 and Smoke levels
			if((list.get(i).getCo2Level() >= 5) || (list.get(i).getSmokeLevel() >= 5)) {
				
				//if not found in the previous list, creating the allert message body and adding to the list of higher smoke and CO2 level rooms
				if(!initialList.stream().filter(o -> o.getId() == id).findFirst().isPresent()) {
					alertMessage = alertMessage + "FloorNo: " + list.get(i).getFloorNo() + "\nRoomNo: " + list.get(i).getRoomNo() + 
							"\nCO2Level: " + list.get(i).getCo2Level() + "\nSmokeLevel: " + list.get(i).getSmokeLevel() + "\n\n";
					
					initialList.add(list.get(i));
				}
			}
			else {
				
				//after fixing the room, removing from the list of containing higher gas level rooms
				if(initialList.stream().filter(o -> o.getId() == id).findFirst().isPresent()) {
					
					int j = 0;
					
					while(initialList.get(j).getId() != id) {
						j++;
					}
					
					initialList.remove(j);
				}
			}
			
		}
		
		if(alertMessage.length() > 0) {
			try {
				serverService = (ServerService) Naming.lookup("rmi://localhost/RMIServer");
				serverService.sendAlert(alertMessage);
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
