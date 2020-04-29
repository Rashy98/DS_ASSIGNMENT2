import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;



public class ReadRESTAPI extends UnicastRemoteObject implements ServerService{
	
	public static String reciever = "rashinikavindya@gmail.com"; 
	public static String PHONE_NUMBER = "+940717795556";
    public static String mymail = "test.purpose.lanka@gmail.com"; 
    public static String host = "smtp.gmail.com";
    public static String ACCOUNT_SID = "ACe3f5d5a6da41f1668e6293d885be39da";
	public static String AUTH_TOKEN = "af97f2aebcf1268dfad57b918ef2ac9d";

	protected ReadRESTAPI() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public String readRESTAPI() {
		String output = null;
		
		try {

            URL url = new URL("http://localhost:8000/api/sensors");//your url i.e fetch data from .
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            output = br.readLine();
            

            System.out.println(output);

            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
		
		Timer timer = new Timer();
	    
	    timer.schedule(new TimerTask() {
	       @Override
	       public void run() {
	           //call the method
	      	 readRESTAPI();
	       }
	    }, 15000, 15000);
		
		return output;
		
		
	}
	
	public void addSensor(int floorNo, String roomNo) {
		
		
		String POST_PARAMS = "{\n" + "\"floorNo\":"+"\""+floorNo+"\",\r\n" +
		        "    \"roomNo\":"+"\""+roomNo+"\",\r\n" +
		        "    \"smokeLevel\":"+"\""+0+"\",\r\n" +
		        "    \"Co2Level\":"+"\""+0+"\",\r\n" +
		        "	\"status\": \"Inactive\"" + "\n}";
		
		try {
			URL url = new URL("http://localhost:8000/api/sensors");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);
			
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(POST_PARAMS.getBytes());
			outputStream.flush();
			outputStream.close();
			
			int responseCode = conn.getResponseCode();
		    System.out.println("POST Response Code :  " + responseCode);
		    System.out.println("POST Response Message : " + conn.getResponseMessage());
		    if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
		        BufferedReader in = new BufferedReader(new InputStreamReader(
		            conn.getInputStream()));
		        String inputLine;
		        StringBuffer response = new StringBuffer();
		        while ((inputLine = in .readLine()) != null) {
		            response.append(inputLine);
		        } in .close();
		        // print result
		        System.out.println(response.toString());
		    } else {
		        System.out.println("POST NOT WORKED");
		    }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//your url i.e fetch data from .
		
	}

	@Override
	public void editSensor(HashMap<String, String> row) throws RemoteException {
		// TODO Auto-generated method stub
		
		String PUT_PARAMS = "{\n" + "\"Id\":"+"\""+row.get("id")+"\",\r\n" +
		        "    \"roomNo\":"+"\""+row.get("roomNo")+"\",\r\n" +
		        "    \"floorNo\":"+"\""+row.get("floorNo")+"\",\r\n" +
		        "	\"status\":"+"\""+ row.get("status") +"\"\n}";
		
		try {
			URL url = new URL("http://localhost:8000/api/sensors/admin");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);
			
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(PUT_PARAMS.getBytes());
			outputStream.flush();
			outputStream.close();
			
			int responseCode = conn.getResponseCode();
		    System.out.println("POST Response Code :  " + responseCode);
		    System.out.println("POST Response Message : " + conn.getResponseMessage());
		    if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
		        BufferedReader in = new BufferedReader(new InputStreamReader(
		            conn.getInputStream()));
		        String inputLine;
		        StringBuffer response = new StringBuffer();
		        while ((inputLine = in .readLine()) != null) {
		            response.append(inputLine);
		        } in .close();
		        // print result
		        System.out.println(response.toString());
		    } else {
		        System.out.println("POST NOT WORKED");
		    }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//your url i.e fetch data from .
	}
	
	public void sendAlert(String messageBody) {
		
		System.out.println(messageBody);
		
		sendMail(reciever, messageBody);
		sendSMS(messageBody);
		
	}
	
	//sending alert email
	public void sendMail(String reciever, String messageBody) {
		//system properties 
	      Properties properties = System.getProperties(); 
	  
	      // Set mail server 
	      properties.put("mail.smtp.host", "smtp.gmail.com");
	      properties.put("mail.smtp.port", "465");
	      properties.put("mail.smtp.ssl.enable", "true");
	      properties.put("mail.smtp.auth", "true");
	      
	      // creating session object to get properties 
	      Session session = Session.getDefaultInstance(properties, new Authenticator() {
		  
	    	  protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("test.purpose.lanka@gmail.com", "Test_purpose123");
			}
	      }); 
	      
	      session.setDebug(true);

		 try 
	      { 
	         // Message object. 
	         MimeMessage message = new MimeMessage(session); 
	  
	         //Adding sender email . 
	         message.setFrom(new InternetAddress(mymail)); 
	  
	         //Adding reciever's email. 
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciever)); 
	  
	         //Subject of the email 
	         message.setSubject("Sensor Alert"); 
	  
	         //Body of the email. 
	         message.setText(messageBody); 
	  
	         //COnfirmation MEssage. 
	         Transport.send(message); 
	         System.out.println("Mail successfully sent"); 
	      } 
	      catch (MessagingException mex)  
	      { 
	         mex.printStackTrace(); 
	      } 
	}
	
	//sending text message alert
	public void sendSMS (String messageBody) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		//if import at top of the class, it will collide with Message import for mail
		com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber(PHONE_NUMBER), new PhoneNumber("+13344630244"), messageBody).create();
		
		//System.out.println(message.getSid());
	}
	
	

	
	  
}
