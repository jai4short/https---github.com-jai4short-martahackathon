package com.lastmilenoproblem.www.data;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.lastmilenoproblem.www.utils.sendHttpRequest;

public class putStops extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		String input = req.getParameter("input");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity entity = new Entity("Stops");
		try {
			JSONObject inputObj = new JSONObject(input);
			entity.setProperty("stop_id", inputObj.get("stop_id"));
			entity.setProperty("stop_code", inputObj.get("stop_code"));
			entity.setProperty("stop_name", inputObj.get("stop_name"));
			entity.setProperty("stop_lat", inputObj.get("stop_lat"));
			entity.setProperty("stop_lon", inputObj.get("stop_lon"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("There was an error uploading " + input);
		}
		
		datastore.put(entity);
		
		System.out.println(input + " uploaded successfully");
	}
}
