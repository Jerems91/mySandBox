package com.jerems91.jeremsshop.modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant le catalogue des produits du magasin
 * @author Administrateur
 *
 */
public class Catalogue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2752104110727443483L;
	private Map<String,Produit> produits;

	public Map<String, Produit> getProduits() {
		return produits;
	}
	
	/**
	 * Constructeur permettant d'initialiser le catalogue de produits
	 * @param request
	 */
	public Catalogue() {
		// Initialisation de la Map
		produits = new HashMap<String,Produit>();
	}

	@Override
	public String toString() {
		return "Catalogue [produits=" + produits + "]";
	}
	
}
