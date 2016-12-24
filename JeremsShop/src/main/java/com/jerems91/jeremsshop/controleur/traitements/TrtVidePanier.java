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

public class TrtVidePanier implements ITraitement {
	
	private static final Logger logger = LogManager.getLogger(TrtVidePanier.class);
	
	public void routeRequete(HttpServletRequest request, HttpServletResponse response, String vue) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("Passage dans la classe " + this.getClass().getName());
			logger.debug("Vue � afficher : " + vue);
		}
		
		// R�cup�ration du code du produit affich� sur la page d'origine
		int codeProduitSource = CtrlUtils.getCodeProduitSource(request);
		
		// Positionnement sur le premier produit par d�faut
		String codeProduit = "1";
		
		if (codeProduitSource != 0) {
		// R�cup�ration du code produit sous forme d'entier r�ussie
			
			// Positionnement du code du produit � afficher en retour depuis le panier
			codeProduit = String.valueOf(codeProduitSource);
			
		}
		
		// Stockage du produit affich� sur la page d'origine
		request.setAttribute(CtrlUtils.PRODUIT, CtrlUtils.getProduitFromCatalogue(request,codeProduit));
		
		// R�cup�ration du panier depuis la session
		Panier monPanier = CtrlUtils.getPanierFromSession(request.getSession(true));
		
		// Vidage du panier et remise � 0 du montant total
		monPanier.getAchats().clear();		
		monPanier.setMontantTotal(0);
		
		// Stockage du panier vide dans la session
		CtrlUtils.setPanierToSession(request.getSession(true), monPanier);
		
		// Affichage de la vue
		request.getRequestDispatcher(vue).forward(request, response);
	}
	
}
