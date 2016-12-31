package com.jerems91.jeremsshop.controleur.traitements;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jerems91.jeremsshop.controleur.dispatch.ITraitement;
import com.jerems91.jeremsshop.controleur.utils.CtrlUtils;

public class TrtSupprimeAchat implements ITraitement {
	
	private static final Logger logger = LogManager.getLogger(TrtSupprimeAchat.class);
	
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
			
			// Positionnement du code du produit à afficher en retour depuis le panier
			codeProduit = String.valueOf(codeProduitSource);
			
		}
		
		// Stockage du produit affiché sur la page d'origine
		request.setAttribute(CtrlUtils.PRODUIT, CtrlUtils.getProduitFromCatalogue(request,codeProduit));
		
		// Récupération du code produit de l'achat à supprimer
		int codeAchatSupp = CtrlUtils.getCodeProduitFromRequest(request,CtrlUtils.CODE_ACHAT);
		
		if (codeAchatSupp != 0) {
		// Récupération du code produit sous forme d'entier réussie pour l'achat à supprimer
			
			// Suppression de l'achat dans le panier
			CtrlUtils.supprimerAchat(request, String.valueOf(codeAchatSupp));
			
		}
		
		// Affichage de la vue
		request.getRequestDispatcher(vue).forward(request, response);
	}
	
}
