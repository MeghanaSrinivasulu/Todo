package com.java.todo;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.java.todo.model.Todo;

public class DeleteTodoServlet extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response){
		
		final Logger log = Logger.getLogger(DeleteTodoServlet.class.getName());
		String tasksString = request.getParameter("tasksToDelete");
		String tasks = tasksString.substring(1, tasksString.length()-1);
		log.warning("tasks-get parameter-------"+tasks);
		StringTokenizer tokenizer = new StringTokenizer(tasks, ",");
		log.warning("tokenizer---"+tokenizer);
		ArrayList<String> itemsToBeDeleted = new ArrayList<String>();
		
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		while(tokenizer.hasMoreTokens()){
			itemsToBeDeleted.add(tokenizer.nextToken().trim());
		}
		
		log.warning("itemsToBeDeleted"+itemsToBeDeleted.size());
		for(int i=0; i < itemsToBeDeleted.size(); i++){
			String id = itemsToBeDeleted.get(i);
			log.warning("id=="+id);
			long idToDelete = Long.parseLong(id);
			log.warning("idToDelete=="+Long.toString(idToDelete));
			Key key = KeyFactory.createKey("Todo", idToDelete);
			log.warning("key--"+key);
			datastore.delete(key); //delete it
			log.warning("deleted id--"+id);
		}
		   
	}
}
