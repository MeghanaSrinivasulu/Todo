package com.java.todo;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class EditTodoServlet extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response){
		String oldDescription = request.getParameter("oldDesc");
		String newDescription = request.getParameter("description");
		String day = request.getParameter("day");
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		
		GregorianCalendar newGregCal = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
		Date taskDate = newGregCal.getTime();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query query = new Query("Todo");
		query.addFilter("description", FilterOperator.EQUAL, oldDescription);
		PreparedQuery pq = datastore.prepare(query);
		Entity todo = pq.asSingleEntity();
		
		todo.setProperty("description", newDescription);
		todo.setProperty("taskdate", taskDate);
		
		datastore.put(todo);
	}
}
