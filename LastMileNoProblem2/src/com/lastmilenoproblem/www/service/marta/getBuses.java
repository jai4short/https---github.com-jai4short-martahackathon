package com.lastmilenoproblem.www.service.marta;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lastmilenoproblem.www.utils.sendHttpRequest;

public class getBuses extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		String latStart, longStart, latStop, longStop, routeInfoResponse;
		JSONObject input, responseObj, getBusInfoResponse;
		
		latStart = req.getParameter("latStart");
		longStart = req.getParameter("longStart");
		latStop = req.getParameter("latStop");
		longStop = req.getParameter("longStop");		
		
		input = new JSONObject();
		responseObj = new JSONObject();
		try {
			input.put("latStart", latStart);
			input.put("longStart", longStart);
			input.put("latStop", latStop);
			input.put("longStop", longStop);
			
			responseObj.put("input", input);
			
			routeInfoResponse = getRouteInfo(input);
			getBusInfoResponse = new JSONObject(getBusInfo(routeInfoResponse));
			
			responseObj.put("input", input);
			responseObj.put("response", getBusInfoResponse);
			
			res.getWriter().write(responseObj.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getRouteInfo(JSONObject input){
		String response = "";
		sendHttpRequest martaReq = new sendHttpRequest();
		try {
			response = martaReq.sendGet("http://maps.googleapis.com/maps/api/directions/json?transit_mode=bus&origin=" + input.get("latStart") + "," + input.get("longStart") + "&destination=" + input.get("latStop") + "," + input.getDouble("longStop")).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	public static String getBusInfo(String routeInfo){
		JSONObject routeJSON, route, leg, s_location, e_location, duration, stopResJSON, busInfo, busInfoRes;
		JSONArray routes, legs, busResults;
		String stopResponse, stopID, routeID;
		busInfoRes = new JSONObject();


		try {
			busResults = new JSONArray(getBuses());
			routeJSON = new JSONObject(routeInfo);
			routes = routeJSON.getJSONArray("routes");
			for(int i = 0; i < routes.length(); i++){
				route = (JSONObject) routes.get(i);
				legs = route.getJSONArray("legs");
				for(int j = 0; j < legs.length(); j++){
					leg = legs.getJSONObject(j);
					duration = leg.getJSONObject("duration");
					s_location = leg.getJSONObject("start_location");
					e_location = leg.getJSONObject("end_location");
					
					busInfoRes.put("duration", duration);
					busInfoRes.put("start_location", s_location);
					busInfoRes.put("end_location", e_location);
					
					
					stopResponse = getStopInfo(s_location);
										
					stopResJSON = new JSONObject(stopResponse);

					stopID = stopResJSON.getString("stop_id");
					
					for(int h = 0; h < busResults.length(); h++){
						busInfo = busResults.getJSONObject(h);
						if(busInfo.get("STOPID").equals(stopID)){
							routeID = busInfo.getString("ROUTE");
							stopResJSON.put("route_number", routeID);
							break;
						}
					}
					
					busInfoRes.put("stop_info", stopResJSON);		
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return busInfoRes.toString();
	}
	
	public static String getBuses(){
		sendHttpRequest martaReq = new sendHttpRequest();
		String response = "";
		try {
			response = martaReq.sendGet("http://developer.itsmarta.com/BRDRestService/RestBusRealTimeService/GetAllBus").toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
	
	public static String getStopInfo(JSONObject start){
		sendHttpRequest request = new sendHttpRequest();
		String response = "";
		try {
			response = request.sendGet("/services/marta/get/stop?lati=" + start.get("lat") + "&longi=" + start.getString("lng")).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
}
