package com.jerems91.jeremsshop.controleur.traitements;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jerems91.jeremsshop.controleur.dispatch.ITraitement;
import com.jerems91.jeremsshop.controleur.utils.CtrlUtils;

public class TrtPrdPrec implements ITraitement {
	
	private static final Logger logger = LogManager.getLogger(TrtPrdPrec.class);
	
	public void routeRequete(HttpServletRequest request, HttpServletResponse response, String vue) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("Passage dans la classe " + this.getClass().getName());
			logger.debug("Vue à afficher : " + vue);
		}
		
		// Récupération du code du produit affiché sur la page d'origine
		int codeProduitSource = CtrlUtils.getCodeProduitFromRequest(request,CtrlUtils.SOURCE);
		
		// Positionnement sur le premier produit par défaut
		String codeProduit = "1";
		
		if (codeProduitSource != 0) {
		// Récupération du code produit sous forme d'entier réussie
			
			if (codeProduitSource == 1) {
			// Si produit d'origine = premier produit, on se repositionne sur le dernier produit du catalogue
				codeProduit = String.valueOf(CtrlUtils.getCatalogueSize(request));
			} else {
				codeProduit = String.valueOf(codeProduitSource - 1);				
			}
			
		}
		
		// Stockage du produit à afficher dans la request
		request.setAttribute(CtrlUtils.PRODUIT, CtrlUtils.getProduitFromCatalogue(request,codeProduit));
		
		// Affichage de la vue
		request.getRequestDispatcher(vue).forward(request, response);
	}
	
}
