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
	
	public static final String FICHIER_CATALOGUE = "fichierCatalogue";
	
	public static final String PAGE_ACCUEIL = "pageAccueil";
	public static final String PAGE_PROBLEME = "pageProbleme";
	
	public static final String CATALOGUE = "catalogue";	
	public static final String PRODUIT = "produit";
	public static final String PANIER = "panier";
	public static final String SOURCE = "source";
	public static final String CODE_ACHAT = "codeAchat";
	public static final String MSG_PRODUIT = "msgProduit";
	public static final String AJOUT_ARTICLE = "Cet article a bien été ajouté au panier !";

	public static Produit getProduitFromCatalogue(HttpServletRequest request,String CodeProduit) {
		return ((Catalogue) request.getServletContext().getAttribute(CATALOGUE)).getProduits().get(CodeProduit);
	}
	
	public static int getCatalogueSize(HttpServletRequest request) {
		return ((Catalogue) request.getServletContext().getAttribute(CATALOGUE)).getProduits().size();
	}
	
	public static int getCodeProduitFromRequest(HttpServletRequest request, String paramName) {
		
		// Positionnement à 0 par défaut
		int codeProduit = 0;
		
		if (request.getParameter(paramName) != null) {
		// Paramètre source renseigné
			
			try {
				// Récupération du code du produit affiché sur la page d'origine
				codeProduit = Integer.parseInt(request.getParameter(paramName));
			} catch (Exception e) {
				logger.error("Problème lors de la conversion du code produit en entier",e);
			}
		}
		
		return codeProduit;

	}
	
	public static double ajouterAchat(HttpServletRequest request, Panier monPanier, String codeProduit) {
		
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
		
		// On incrémente le nombre total d'articles du panier
		monPanier.setNombreTotal(monPanier.getNombreTotal() + 1);
		
		// On incrémente le montant total du panier
		monPanier.setMontantTotal(monPanier.getMontantTotal() + monAchat.getProduit().getPrix());
		
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
	
	public static void setPanierToSession(HttpSession session, Panier monPanier) {
		
		// Stockage du panier dans la session
		session.setAttribute(PANIER,monPanier);		
		
	}
	
	public static void viderPanier(Panier monPanier) {
		
		// Vidage du panier, remise à 0 du montant total et du nombre total d'articles
		monPanier.getAchats().clear();
		monPanier.setNombreTotal(0);
		monPanier.setMontantTotal(0);
		
	}
	
	public static void supprimerAchat(Panier monPanier, String codeAchat) {
		
		// Diminution du nombre total d'articles et du montant total, suppression de l'achat dans le panier
		monPanier.setNombreTotal(monPanier.getNombreTotal() - monPanier.getAchats().get(codeAchat).getQuantite());
		monPanier.setMontantTotal(monPanier.getMontantTotal() - monPanier.getAchats().get(codeAchat).getMontant());
		monPanier.getAchats().remove(codeAchat);
		
		// Si le panier ne contient plus d'achat, on réinitialise le panier par sécurité
		if (monPanier.getAchats().isEmpty()) {
			viderPanier(monPanier);
		}
		
	}
	
	public static void quantiteMoins(Panier monPanier, String codeAchat) {
		
		if (monPanier.getAchats().get(codeAchat).getQuantite() == 1) {
		// Si un seul exemplaire restant pour l'achat, on le supprime
			supprimerAchat(monPanier, codeAchat);
		} else {
			
			// Diminution de la quantité et du montant de l'achat
			monPanier.getAchats().get(codeAchat).setQuantite(monPanier.getAchats().get(codeAchat).getQuantite() - 1);
			monPanier.getAchats().get(codeAchat).setMontant(monPanier.getAchats().get(codeAchat).getMontant() - monPanier.getAchats().get(codeAchat).getProduit().getPrix());
			
			// Diminution du nombre total d'articles et du montant total
			monPanier.setNombreTotal(monPanier.getNombreTotal() - 1);
			monPanier.setMontantTotal(monPanier.getMontantTotal() - monPanier.getAchats().get(codeAchat).getProduit().getPrix());
			
		}		
		
	}
	
	public static void quantitePlus(Panier monPanier, String codeAchat) {
		
		// Augmentation de la quantité et du montant de l'achat
		monPanier.getAchats().get(codeAchat).setQuantite(monPanier.getAchats().get(codeAchat).getQuantite() + 1);
		monPanier.getAchats().get(codeAchat).setMontant(monPanier.getAchats().get(codeAchat).getMontant() + monPanier.getAchats().get(codeAchat).getProduit().getPrix());
		
		// Augmentation du nombre total d'articles et du montant total
		monPanier.setNombreTotal(monPanier.getNombreTotal() + 1);
		monPanier.setMontantTotal(monPanier.getMontantTotal() + monPanier.getAchats().get(codeAchat).getProduit().getPrix());
		
	}		
	
}
