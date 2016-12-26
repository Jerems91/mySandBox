package com.jerems91.jeremsshop.modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant le panier du client
 * @author Administrateur
 *
 */
public class Panier implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3641049570017386943L;
	private Map<String,Achat> achats;
	private double montantTotal;
	private int nombreTotal;
	
	public Panier() {
		achats = new HashMap<String,Achat>();
	}

	public double getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(double montantTotal) {
		this.montantTotal = montantTotal;
	}

	public int getNombreTotal() {
		return nombreTotal;
	}

	public void setNombreTotal(int nombreTotal) {
		this.nombreTotal = nombreTotal;
	}

	public Map<String, Achat> getAchats() {
		return achats;
	}

	@Override
	public String toString() {
		return "Panier [achats=" + achats + ", montantTotal=" + montantTotal + "]";
	}
	
}
