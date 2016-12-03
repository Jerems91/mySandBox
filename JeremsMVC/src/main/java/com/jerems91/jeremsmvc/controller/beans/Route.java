package com.jerems91.jeremsmvc.controller.beans;

/**
 * Représente un route configurée dans la config MVC
 * @author Administrateur
 *
 */
public class Route {
	
	private String classe;
	private String vue;	
	
	public Route(String pClasse, String pVue) {
		this.classe = pClasse;
		this.vue = pVue;
	}

	public String getClasse() {
		return classe;
	}
	
	public String getVue() {
		return vue;
	}

	@Override
	public String toString() {
		return "Route [classe=" + classe + ", vue=" + vue + "]";
	}	
	
}
