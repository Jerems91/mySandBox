package com.jerems91.jeremsmvc.controleur.routes;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Administrateur
 *
 */
public interface IRoute {
	void routeRequete(HttpServletRequest request, HttpServletResponse response, String vue) throws IOException, ServletException;
}