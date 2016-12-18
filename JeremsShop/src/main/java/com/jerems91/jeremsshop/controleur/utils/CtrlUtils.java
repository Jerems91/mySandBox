package com.jerems91.jeremsshop.controleur.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jerems91.jeremsshop.modele.Catalogue;
import com.jerems91.jeremsshop.modele.Produit;

public class CtrlUtils {

	private static final Logger logger = LogManager.getLogger(CtrlUtils.class);
	
	public static final String MVC_CONFIG = "mvcConfig";
	
	public static final String PAGE_ACCUEIL = "pageAccueil";
	public static final String PAGE_PROBLEME = "pageProbleme";
	
	public static final String CATALOGUE = "catalogue";	
	public static final String PRODUIT = "produit";
	public static final String SOURCE = "source";

	public static Produit getProduitFromCatalogue(HttpServletRequest request,String CodeProduit) {
		return ((Catalogue) request.getServletContext().getAttribute(CATALOGUE)).getProduits().get(CodeProduit);
	}
	
	public static int getCatalogueSize(HttpServletRequest request) {
		return ((Catalogue) request.getServletContext().getAttribute(CATALOGUE)).getProduits().size();
	}
	
	public static int getCodeProduitSource(HttpServletRequest request) {
		
		// Positionnement à 0 par défaut
		int codeProduitSource = 0;
		
		if (request.getParameter(CtrlUtils.SOURCE) != null) {
		// Paramètre source renseigné
			
			try {
				// Récupération du code du produit affiché sur la page d'origine
				codeProduitSource = Integer.parseInt(request.getParameter(CtrlUtils.SOURCE));
			} catch (Exception e) {
				logger.error("Problème lors de la conversion du code produit en entier",e);
			}
		}
		
		return codeProduitSource;

	}

}
