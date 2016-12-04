package com.jerems91.jeremsmvc.controleur.traitements;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jerems91.jeremsmvc.controleur.dispatch.ITraitement;

public class Traitement implements ITraitement {
	
	private static final Logger logger = LogManager.getLogger(Traitement.class);

	public void routeRequete(HttpServletRequest request, HttpServletResponse response, String vue) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("Passage dans la classe " + this.getClass().getName());
			logger.debug("Vue à afficher : " + vue);			
		}
		request.setAttribute("vue", vue);
		request.setAttribute("traitement", this.getClass().getName());
		request.getRequestDispatcher(vue).forward(request, response);
	}		

}
