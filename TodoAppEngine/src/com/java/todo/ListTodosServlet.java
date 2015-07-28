package com.java.todo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.log.Log;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.java.todo.dao.Dao;
import com.java.todo.model.Todo;



public class ListTodosServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
	
		//List<Todo> listOfTodos = Dao.INSTANCE.getTodos();
		final Logger log = Logger.getLogger(DeleteTodoServlet.class.getName());
		
		
		if((request.getParameter("day") != null) && (request.getParameter("month") != null) && (request.getParameter("year") != null)){
			String day = request.getParameter("day");
			String month = request.getParameter("month");
			String year = request.getParameter("year");
			
			log.warning("day:"+day);
			log.warning("month:"+month);
			log.warning("year:"+year);
			
			
			 

			String date = year + "/" + month + "/" + day;
			java.util.Date taskDate = null;

			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				taskDate = formatter.parse(date);
			} catch (ParseException e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}

			Filter propertyFilter = new FilterPredicate("taskdate",FilterOperator.EQUAL,taskDate);
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
				
				e.printStackTrace();
			}

			  
			}
			try {
				json.put("listoftodos", todoList);
			} catch (JSONException e) {
				
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
		
		/*else if((request.getParameter("cday") != null) && (request.getParameter("cmonth") != null) && (request.getParameter("cyear") != null)){
			String cday = request.getParameter("cday");
			String cmonth = request.getParameter("cmonth");
			String cyear = request.getParameter("cyear");
			
			log.warning("cday:"+cday);
			log.warning("cmonth:"+cmonth);
			log.warning("cyear:"+cyear);
			
			String date = cyear + "/" + cmonth + "/" + cday;
			java.util.Date taskDate = null;

			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				taskDate = formatter.parse(date);
			} catch (ParseException e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}

			Filter propertyFilter = new FilterPredicate("completeddate",FilterOperator.EQUAL,taskDate);
			Query q = new Query("Todo").setFilter(propertyFilter);
			
			PreparedQuery pq = datastore.prepare(q);


			log.warning("after executing query");
			JSONObject json = new JSONObject();
			JSONArray todoList = new JSONArray();
			JSONObject todo;
			for (Entity result : pq.asIterable()) {
			  String taskDescription = (String) result.getProperty("description");
			  log.warning("taskDescription:"+taskDescription);
			  Date taskDt = (Date) result.getProperty("taskdate");
			  log.warning("taskDt"+taskDt.toString());
			  String status = (String) result.getProperty("status");
			  log.warning("status"+status);
			  long idKey = result.getKey().getId();
				String id = Long.toString(idKey);
				log.warning("id"+id);
			  try {
				  todo = new JSONObject();
				  todo.put("description", taskDescription);
				  todo.put("date", taskDt);
				  todo.put("status", status);
				  todo.put("id", id);
				  todoList.put(todo);
				
			} catch (JSONException e) {
				
				e.printStackTrace();
			}

			  
			}
			try {
				json.put("listoftodos", todoList);
			} catch (JSONException e) {
				
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
		*/
		else if(request.getParameter("status") != null){
			
			String status = request.getParameter("status");
			Filter propertyFilter = new FilterPredicate("status",FilterOperator.EQUAL,status);
			Query q = new Query("Todo").setFilter(propertyFilter);
			
			PreparedQuery pq = datastore.prepare(q);


			JSONObject json = new JSONObject();
			JSONArray todoList = new JSONArray();
			JSONObject todo;
			for (Entity result : pq.asIterable()) {
			  String taskDescription = (String) result.getProperty("description");
			
			  Date taskDt = (Date) result.getProperty("taskdate");
			  String sTaskDt = taskDt.toString();
			 
			  String statusOfTask = (String) result.getProperty("status");
			  
			  long idKey = result.getKey().getId();
				String id = Long.toString(idKey);
			  try {
				  todo = new JSONObject();
				  todo.put("description", taskDescription);
				  todo.put("date", sTaskDt);
				  todo.put("status", statusOfTask);
				  todo.put("id", id);
				  todoList.put(todo);
				
			} catch (JSONException e) {
				
				e.printStackTrace();
			}

			  
			}
			try {
				json.put("listoftodos", todoList);
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
			response.setContentType("application/json");
			try {
				response.getWriter().write(json.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		else{
			Query query = new Query("Todo").addSort("taskdate", Query.SortDirection.ASCENDING);
		
		List<Entity> listOfTodos = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(10));
		
		JSONObject json = new JSONObject();
		JSONArray todoList = new JSONArray();
		JSONObject todo;
		Entity todoObj;
		
		for(int i=0;i<listOfTodos.size();i++){
			todoObj = listOfTodos.get(i);
			String description = (String)todoObj.getProperty("description");
			
			Date date = (Date)todoObj.getProperty("taskdate");
			String taskDate = date.toString();
			long idKey = todoObj.getKey().getId();
			String id = Long.toString(idKey);
			String status = (String) todoObj.getProperty("status");
			
		
			
			todo = new JSONObject();
			try {
				todo.put("description", description);
				todo.put("date", taskDate);
				todo.put("id", id);
				todo.put("status", status);
				todoList.put(todo);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		try {
			json.put("listoftodos", todoList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.setContentType("application/json");
		try {
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
}