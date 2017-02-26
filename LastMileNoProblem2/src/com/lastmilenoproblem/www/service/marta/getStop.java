package com.lastmilenoproblem.www.service.marta;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class getStop extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		Long stopID, stopCode;
		String stopName;

		Double lati, longi;
		
		JSONObject response = new JSONObject();
		
		lati = Double.parseDouble(req.getParameter("lati"));
		longi = Double.parseDouble(req.getParameter("longi"));
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Stops");
		query.addFilter("stop_lat", FilterOperator.EQUAL, lati);
		query.addFilter("stop_lon", FilterOperator.EQUAL, longi);
		
		PreparedQuery pq = datastore.prepare(query);
		
		Entity result = pq.asSingleEntity();
		stopID = (Long) result.getProperty("stop_id");
		stopCode = (Long) result.getProperty("stop_code");
		stopName = (String) result.getProperty("stop_name");
		
		try {
			response.put("stop_id", stopID);
			response.put("stop_code", stopCode);
			response.put("stop_name", stopName);
			
			res.getWriter().write(response.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
