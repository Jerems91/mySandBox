package com.jerems91.jeremsshop.controleur.traitements;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jerems91.jeremsshop.controleur.dispatch.ITraitement;
import com.jerems91.jeremsshop.controleur.utils.CtrlUtils;

public class TrtAjoutPanier implements ITraitement {
	
	private static final Logger logger = LogManager.getLogger(TrtAjoutPanier.class);
	
	public void routeRequete(HttpServletRequest request, HttpServletResponse response, String vue) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("Passage dans la classe " + this.getClass().getName());
			logger.debug("Vue à afficher : " + vue);
		}
		
		double montantTotal = 0;
		
		// Récupération du code du produit affiché sur la page d'origine
		int codeProduitSource = CtrlUtils.getCodeProduitSource(request);
		
		// Positionnement sur le premier produit par défaut
		String codeProduit = "1";
		
		if (codeProduitSource != 0) {
		// Récupération du code produit sous forme d'entier réussie
			
			// Positionnement du code produit choisi pour l'achat
			codeProduit = String.valueOf(codeProduitSource);
			
			// Ajout du produit acheté dans le panier
			montantTotal = CtrlUtils.ajouterAchat(request, codeProduit);
			
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Produit choisi : " + CtrlUtils.getProduitFromCatalogue(request,codeProduit));
			logger.debug("Montant Total du panier : " + montantTotal);
			logger.debug("Mon panier : " + CtrlUtils.getPanierFromSession(request.getSession(true)));
		}
		
		// Stockage du produit à afficher, normalement on reste sur le produit choisi pour l'achat
		request.setAttribute(CtrlUtils.PRODUIT, CtrlUtils.getProduitFromCatalogue(request,codeProduit));
		
		// Affichage de la vue
		request.getRequestDispatcher(vue).forward(request, response);
	}
	
}
