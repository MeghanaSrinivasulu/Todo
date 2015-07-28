package com.java.todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;



public class MarkAsCompleteServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response){
		final Logger log = Logger.getLogger(MarkAsCompleteServlet.class.getName());
		String tasksToMark = request.getParameter("tasksToMarkAsComplete");
		log.warning("tasksToMarkAsComplete"+tasksToMark);
		String tasks = tasksToMark.substring(1, tasksToMark.length()-1);
		log.warning("IDs"+tasks);
		StringTokenizer tokenizer = new StringTokenizer(tasks, ",");
		ArrayList<String> itemsToBeMarkedAsComplete = new ArrayList<String>();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		while(tokenizer.hasMoreTokens()){
			itemsToBeMarkedAsComplete.add(tokenizer.nextToken().trim());
		}
		Query query = new Query("Todo");
		
		for(int i=0;i<itemsToBeMarkedAsComplete.size();i++){
			try {
				log.warning("before getting the Entity corresponding to the ID");
				Entity toDoEntity = datastore.get(KeyFactory.createKey("Todo", Long.parseLong(itemsToBeMarkedAsComplete.get(i))));
				log.warning("before updating the status");
				toDoEntity.setProperty("status", "completed");
				log.warning("after updating the status");
				Date date = new Date();
				String modifiedDate= new SimpleDateFormat("yyyy/MM/dd").format(date);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				Date taskDate = formatter.parse(modifiedDate);
				toDoEntity.setProperty("completeddate", taskDate);
				log.warning("after setting the completed date");
				datastore.put(toDoEntity);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
