package com.ds.assignment2.SensorClient;

import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.*;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        getJsondata();
    }
    public static String readRESTAPI() {
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

        return output;
    }


    public static void getJsondata() {
        String output = readRESTAPI();


        JSONObject jsonObject = new JSONObject(output);

        JSONArray array = jsonObject.getJSONArray("data");


        for (int i = 0; i < array.length(); i++) {

            int id = array.getJSONObject(i).getInt("Id");
            int floor = array.getJSONObject(i).getInt("floorNo");
            String room = array.getJSONObject(i).getString("roomNo");
            int smoke = array.getJSONObject(i).getInt("smokeLevel");
            int co2 = array.getJSONObject(i).getInt("Co2Level");

            Random random = new Random();
            
            smoke = random.nextInt(10 - 1 + 1) + 1;
            co2 = random.nextInt(10 - 1 + 1) + 1;

             UpdateSensor(id,smoke,co2);
            	
              
        }
    
    
        Timer timer = new Timer();
      
        timer.schedule(new TimerTask() {
           @Override
           public void run() {
               //call the method
               getJsondata();
           }
        }, 10000, 10000);
  
	
    }
    
public static void UpdateSensor(int id,int smokeLevel, int Co2Level) {
		
		
		String UPDATE_PARAMS = "{\n" + "\"Id\":"+id+",\r\n" +
		        "    \"smokeLevel\":"+smokeLevel+",\r\n" +
		        "    \"Co2Level\":"+Co2Level+"\r\n}";
		
		
		try {
			URL url = new URL("http://localhost:8000/api/sensors");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(UPDATE_PARAMS.getBytes());
			outputStream.flush();
			outputStream.close();
			
			int responseCode = conn.getResponseCode();
		    System.out.println("Update Response Code :  " + responseCode);
		    System.out.println("Update Response Message : " + conn.getResponseMessage());
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
		    }
			
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
}


