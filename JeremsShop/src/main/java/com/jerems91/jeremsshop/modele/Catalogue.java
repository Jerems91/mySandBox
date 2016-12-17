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
	private static final long serialVersionUID = 436964872104112661L;
	private Map<String,Produit> produits;

	public Map<String, Produit> getProduits() {
		return produits;
	}
	
	/**
	 * Constructeur permettant d'initialiser le catalogue de produits	 * 
	 */
	public Catalogue() {
		// Initialisation de la Map
		produits = new HashMap<String,Produit>();
		
		// Stockage des produits
		produits.put("1",new Produit("1","Bouilloire",99.99,"Bouilloire","../images/bouilloire.png","Quelle superbe bouilloire !!!"));
		produits.put("2",new Produit("2","Cuisinière",499.95,"Cuisinière","../images/cuisiniere.png","Quelle superbe cuisinière !!!"));
		produits.put("3",new Produit("3","Machine à café",129.49,"Machine à café","../images/machine-cafe.png","Quelle superbe machine à café !!!"));
		produits.put("4",new Produit("4","Machine à pain",99.99,"Machine à pain","../images/machine-pain.png","Quelle superbe machine à pain !!!"));
		produits.put("5",new Produit("5","Micro-ondes",259,"Micro-ondes","../images/micro-ondes.png","Quelle superbe micro-ondes !!!"));
		produits.put("6",new Produit("6","Shaker",119.99,"Shaker","../images/shaker.png","Quelle superbe shaker !!!"));
		produits.put("7",new Produit("7","Toaster",129.99,"Toaster","../images/toaster.png","Quelle superbe toaster !!!"));
		
	}

	@Override
	public String toString() {
		return "Catalogue [produits=" + produits + "]";
	}	
	
}
