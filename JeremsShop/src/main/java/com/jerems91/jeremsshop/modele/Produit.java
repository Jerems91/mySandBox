package com.jerems91.jeremsshop.modele;

import java.io.Serializable;

/**
 * Classe représentant un produit du magasin
 * Le code correspond au numéro de la page à afficher
 * Sérialisable car stocké dans la session 
 * => Constructeur sans paramètres
 * => Interface Serializable
 * => Serial Version ID
 * @author Administrateur
 *
 */
public class Produit implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4617727649743826192L;
	private String code;
	private String nom;
	private double prix;
	private String titrePage;
	private String cheminImage;
	private String description;
	
	public Produit() {
		
	}	
	
	public Produit(String code, String nom, double prix, String titrePage, String cheminImage, String description) {
		this.code = code;
		this.nom = nom;
		this.prix = prix;
		this.titrePage = titrePage;
		this.cheminImage = cheminImage;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getNom() {
		return nom;
	}

	public double getPrix() {
		return prix;
	}	

	public String getTitrePage() {
		return titrePage;
	}

	public String getCheminImage() {
		return cheminImage;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Produit [code=" + code + ", nom=" + nom + ", prix=" + prix + ", titrePage=" + titrePage
				+ ", cheminImage=" + cheminImage + ", description=" + description + "]";
	}

}
