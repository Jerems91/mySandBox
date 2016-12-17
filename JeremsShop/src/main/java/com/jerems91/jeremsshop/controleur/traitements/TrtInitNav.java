package com.jerems91.jeremsshop.controleur.traitements;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jerems91.jeremsshop.controleur.dispatch.ITraitement;
import com.jerems91.jeremsshop.modele.Catalogue;
import com.jerems91.jeremsshop.modele.Produit;

public class TrtInitNav implements ITraitement {
	
	private static final Logger logger = LogManager.getLogger(TrtInitNav.class);
	
	private static final String CATALOGUE = "catalogue";
	
	public static final String PRODUIT = "produit";

	public void routeRequete(HttpServletRequest request, HttpServletResponse response, String vue) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("Passage dans la classe " + this.getClass().getName());
			logger.debug("Vue à afficher : " + vue);
		}
		
		// Stockage du produit à afficher, ici le premier car il s'agit du premier accès au catalogue
		request.setAttribute(PRODUIT, getProduitFromCatalogue(request,"1"));
		
		// Affichage de la vue
		request.getRequestDispatcher(vue).forward(request, response);
	}
	
	private Produit getProduitFromCatalogue(HttpServletRequest request,String CodeProduit) {
		return ((Catalogue) request.getServletContext().getAttribute(CATALOGUE)).getProduits().get(CodeProduit);
	}

}
