package com.java.todo;

import java.io.IOException;
//import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class CreateTodoServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		String description = request.getParameter("description");
		String day = request.getParameter("day");
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		String status = request.getParameter("status");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		/*
		 * Key todoKey = KeyFactory.createKey("Todo", "todoone"); Entity entity
		 * = new Entity(todoKey);
		 */

		Entity entity = new Entity("Todo");

		String date = year + "/" + month + "/" + day;
		java.util.Date taskDate = null;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			taskDate = formatter.parse(date);
		} catch (ParseException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

		entity.setProperty("description", description);
		entity.setProperty("taskdate", taskDate);
		entity.setProperty("status", status);

		datastore.put(entity);

		JSONObject json = new JSONObject();
		try {
			json.put("result", "success");
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
