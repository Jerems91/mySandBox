package com.jerems91.jeremsshop.controleur.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jerems91.jeremsshop.modele.Achat;
import com.jerems91.jeremsshop.modele.Catalogue;
import com.jerems91.jeremsshop.modele.Panier;
import com.jerems91.jeremsshop.modele.Produit;

public class CtrlUtils {

	private static final Logger logger = LogManager.getLogger(CtrlUtils.class);
	
	public static final String MVC_CONFIG = "mvcConfig";
	
	public static final String PAGE_ACCUEIL = "pageAccueil";
	public static final String PAGE_PROBLEME = "pageProbleme";
	
	public static final String CATALOGUE = "catalogue";	
	public static final String PRODUIT = "produit";
	public static final String PANIER = "panier";
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
	
	public static double ajouterAchat(HttpServletRequest request,String codeProduit) {
		
		// Récupération du panier depuis la session
		Panier monPanier = getPanierFromSession(request.getSession(true));
		
		// Recherche du produit dans le panier
		Achat monAchat = monPanier.getAchats().get(codeProduit);
		
		if (monAchat == null) {
		// Pas d'achat concernant ce produit dans le panier
			
			// On crée un nouvel achat pour ce produit
			monAchat = new Achat(getProduitFromCatalogue(request,codeProduit),0,0);
			
			// Ajout de l'achat dans le panier
			monPanier.getAchats().put(codeProduit, monAchat);
		}
		
		// On incrémente la quantité
		monAchat.setQuantite(monAchat.getQuantite() + 1);
		
		// On incrémente le montant de l'achat avec le prix du produit
		monAchat.setMontant(monAchat.getMontant() + monAchat.getProduit().getPrix());
		
		// On incrémente le montant total du panier
		monPanier.setMontantTotal(monPanier.getMontantTotal() + monAchat.getProduit().getPrix());
		
		// Stockage du panier dans la session
		request.getSession(true).setAttribute(PANIER, monPanier);
		
		return monPanier.getMontantTotal();
	}
	
	public static Panier getPanierFromSession(HttpSession session) {
		
		Panier monPanier = (Panier) session.getAttribute(PANIER);
		
		// Si aucun panier existant, on le crée
		if (monPanier == null) {
			monPanier = new Panier();
		}
		
		return monPanier;
	}
	
}
