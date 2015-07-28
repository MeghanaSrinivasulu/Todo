package com.java.todo;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class GetPendingTasksServlet extends HttpServlet{
public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Filter propertyFilter = new FilterPredicate("taskdate",FilterOperator.LESS_THAN_OR_EQUAL,new Date());
		Query q = new Query("Todo").setFilter(propertyFilter);
		
		PreparedQuery pq = datastore.prepare(q);


		JSONObject json = new JSONObject();
		JSONArray todoList = new JSONArray();
		JSONObject todo;
		for (Entity result : pq.asIterable()) {
		  String taskDescription = (String) result.getProperty("description");
		  Date taskDt = (Date) result.getProperty("taskdate");
		  String status = (String) result.getProperty("status");
		  long idKey = result.getKey().getId();
			String id = Long.toString(idKey);
		  try {
			  todo = new JSONObject();
			  todo.put("description", taskDescription);
			  todo.put("date", taskDt);
			  todo.put("status", status);
			  todo.put("id", id);
			  todoList.put(todo);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  
		}
		try {
			json.put("listoftodos", todoList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("application/json");
		try {
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
