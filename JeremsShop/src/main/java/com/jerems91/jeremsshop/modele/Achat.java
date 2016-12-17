package com.jerems91.jeremsshop.modele;

import java.io.Serializable;

/**
 * Représente l'achat effectué par le client
 * @author Administrateur
 *
 */
public class Achat implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 789699509723824293L;
	private Produit produit;
	private int quantite;
	private double montant;
	
	public Achat() {
		
	}

	public Achat(Produit produit, int quantite, double montant) {
		super();
		this.produit = produit;
		this.quantite = quantite;
		this.montant = montant;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public Produit getProduit() {
		return produit;
	}

	public int getQuantite() {
		return quantite;
	}	

	public void setQuantite(int quantité) {
		this.quantite = quantité;
	}

	@Override
	public String toString() {
		return "Achat [produit=" + produit + ", quantité=" + quantite + ", montant=" + montant + "]";
	}	
	
}
