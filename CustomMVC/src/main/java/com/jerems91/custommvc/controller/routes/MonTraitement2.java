package com.jerems91.custommvc.controller.routes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jerems91.custommvc.controller.dispatch.IRoute;

public class MonTraitement2 implements IRoute {

	public void routeRequete(HttpServletRequest request, HttpServletResponse response, String vue) throws IOException, ServletException {
		response.getWriter().println("Passage dans la classe MonTraitement2");
		response.getWriter().println("Vue à afficher : " + vue);
	}

}
