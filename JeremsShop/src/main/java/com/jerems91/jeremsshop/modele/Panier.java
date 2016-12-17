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
	private static final long serialVersionUID = -5063980757492884838L;
	private Map<String,Achat> achats;
	private double montantTotal;
	
	public Panier() {
		achats = new HashMap<String,Achat>();
	}

	public double getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(double montantTotal) {
		this.montantTotal = montantTotal;
	}

	public Map<String, Achat> getAchats() {
		return achats;
	}

	@Override
	public String toString() {
		return "Panier [achats=" + achats + ", montantTotal=" + montantTotal + "]";
	}
	
}
