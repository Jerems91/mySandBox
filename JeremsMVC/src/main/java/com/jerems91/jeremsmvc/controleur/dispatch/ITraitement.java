package com.jerems91.jeremsmvc.controleur.dispatch;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Administrateur
 *
 */
public interface ITraitement {
	void routeRequete(HttpServletRequest request, HttpServletResponse response, String vue) throws IOException, ServletException;
}
