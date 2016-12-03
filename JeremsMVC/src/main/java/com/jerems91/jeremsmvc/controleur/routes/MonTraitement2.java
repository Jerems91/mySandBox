package com.jerems91.jeremsmvc.controleur.routes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MonTraitement2 implements IRoute {
	
	private static final Logger logger = LogManager.getLogger(MonTraitement2.class);

	public void routeRequete(HttpServletRequest request, HttpServletResponse response, String vue) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("Passage dans la classe MonTraitement2");
			logger.debug("Vue � afficher : " + vue);			
		}
		request.setAttribute("vue", vue);
		request.setAttribute("traitement", "MonTraitement2");
		request.getRequestDispatcher(vue).forward(request, response);
	}

}
