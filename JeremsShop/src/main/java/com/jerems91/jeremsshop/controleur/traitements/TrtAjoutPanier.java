package com.jerems91.jeremsshop.controleur.traitements;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jerems91.jeremsshop.controleur.dispatch.ITraitement;
import com.jerems91.jeremsshop.controleur.utils.CtrlUtils;
import com.jerems91.jeremsshop.modele.Panier;

public class TrtAjoutPanier implements ITraitement {
	
	private static final Logger logger = LogManager.getLogger(TrtAjoutPanier.class);
	
	public void routeRequete(HttpServletRequest request, HttpServletResponse response, String vue) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("Passage dans la classe " + this.getClass().getName());
			logger.debug("Vue � afficher : " + vue);
		}
		
		double montantTotal = 0;
		
		// R�cup�ration du code du produit affich� sur la page d'origine
		int codeProduitSource = CtrlUtils.getCodeProduitFromRequest(request,CtrlUtils.SOURCE);
		
		// Positionnement sur le premier produit par d�faut
		String codeProduit = "1";
		
		if (codeProduitSource != 0) {
		// R�cup�ration du code produit sous forme d'entier r�ussie
			
			// Positionnement du code produit choisi pour l'achat
			codeProduit = String.valueOf(codeProduitSource);
			
			// R�cup�ration du panier depuis la session
			Panier monPanier = CtrlUtils.getPanierFromSession(request.getSession(true));			
			
			// Ajout du produit achet� dans le panier
			montantTotal = CtrlUtils.ajouterAchat(request, monPanier, codeProduit);
			
			// Stockage du panier mis � jour dans la session
			CtrlUtils.setPanierToSession(request.getSession(true),monPanier);
			
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Produit choisi : " + CtrlUtils.getProduitFromCatalogue(request,codeProduit));
			logger.debug("Nombre Total d'articles du panier : " + CtrlUtils.getPanierFromSession(request.getSession(true)).getNombreTotal());
			logger.debug("Montant Total du panier : " + montantTotal);
			logger.debug("Mon panier : " + CtrlUtils.getPanierFromSession(request.getSession(true)));
		}
		
		// Stockage du produit � afficher, normalement on reste sur le produit choisi pour l'achat
		request.setAttribute(CtrlUtils.PRODUIT, CtrlUtils.getProduitFromCatalogue(request,codeProduit));
		
		// Stockage du message � afficher sur la page produit suite � l'ajout dans le panier
		request.setAttribute(CtrlUtils.MSG_PRODUIT, CtrlUtils.AJOUT_ARTICLE);
		
		// Affichage de la vue
		request.getRequestDispatcher(vue).forward(request, response);
	}
	
}
