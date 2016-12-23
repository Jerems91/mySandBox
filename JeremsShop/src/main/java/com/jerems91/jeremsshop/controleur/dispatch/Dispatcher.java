package com.jerems91.jeremsshop.controleur.dispatch;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.jerems91.jeremsshop.controleur.beans.Route;
import com.jerems91.jeremsshop.controleur.utils.CtrlUtils;
import com.jerems91.jeremsshop.modele.Catalogue;
import com.jerems91.jeremsshop.modele.Produit;

/**
 * Servlet implementation class Dispatcher
 */
public class Dispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(Dispatcher.class);
	
	private String pageAccueil;
	private String pageProbleme;
	private Map<String,Route> configRoutes;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// Récupération du chemin et du nom de la page d'accueil depuis le contexte de l'application
		pageAccueil = getServletContext().getInitParameter(CtrlUtils.PAGE_ACCUEIL);
		
		// Récupération du chemin et du nom de la page d'erreur depuis le contexte de l'application
		pageProbleme = getServletContext().getInitParameter(CtrlUtils.PAGE_PROBLEME);
		
		// Chargement de la config MVC
		chargeConfigMVC();
		
		// Affichage des routes configurées à partir de la Map
		if (logger.isDebugEnabled()) {
			logger.debug(configRoutes);			
		}
		
		// Instanciation du catalogue
		Catalogue monCatalogue = new Catalogue();
		
		// Chargement du catalogue
		chargeCatalogue(monCatalogue);
		
		// Stockage dans le contexte de l'application
		getServletContext().setAttribute(CtrlUtils.CATALOGUE, monCatalogue);
		
		// Affichage du contenu du catalogue
		if (logger.isDebugEnabled()) {
			logger.debug(monCatalogue);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Récupération du chemin de traitement demandé
		String chemin = request.getPathInfo();
		
		if (logger.isDebugEnabled()) {
			logger.debug(chemin);			
		}
		
		// Si on ne demande rien, retour à index.html
		if ((chemin == null) || "/".equals(chemin)) {
		    request.getRequestDispatcher(pageAccueil).forward(request, response);
		    return;
		}
		
		// Récupération de la commande de traitement
		String commande = chemin.substring(1, chemin.length());
		
		// Récupération du nom de la classe
		String nomClasse = configRoutes.get(commande).getClasse();
		
		if (logger.isDebugEnabled()) {
			logger.debug(nomClasse);			
		}
		
		if (nomClasse.trim().isEmpty()) {
		// Chaine vide
			request.setAttribute("message", "Nom de classe non renseigné pour la commande " + commande);
			request.getRequestDispatcher(pageProbleme).forward(request, response);
		} else {
			try {
			    // Récupération de la classe de traitement
				Class<?> c = Class.forName(nomClasse);
				// Instanciation de la classe de traitement
			    ITraitement ir = (ITraitement) c.newInstance();
			    // Appel de la méthode de traitement dans la classe
			    ir.routeRequete(request, response,configRoutes.get(commande).getVue());
			} catch (ClassNotFoundException cne) {
				logger.error("Classe introuvable : " + nomClasse,cne);
			    request.setAttribute("message", "Classe " + nomClasse + " non trouvée !");
			    request.getRequestDispatcher(pageProbleme).forward(request, response);
			} catch (InstantiationException ie) {
			// impossible d'instancier cette classe !
				logger.error("Impossible d'instancier la classe " + nomClasse,ie);
			    throw new ServletException(ie);
			} catch (IllegalAccessException iae) {
			// le constructeur n'est pas visible !
				logger.error("Le constructeur n'est pas visible pour la classe " + nomClasse,iae);
			    throw new ServletException(iae);
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void chargeConfigMVC() throws ServletException {
		
		// Récupération du chemin et du nom du fichier de configuration depuis le contexte de l'application
		String fichierConfig = getServletContext().getInitParameter(CtrlUtils.MVC_CONFIG);
		
		if (logger.isInfoEnabled()) {
			logger.info("Chargement du fichier de configuration MVC : " + fichierConfig);			
		}
		
		// Instanciation d'un builder de document
		SAXBuilder sbx = new SAXBuilder();
		
		// Parsing du fichier de configuration XML et instantiation d'un Document
		Document doc;		
		try {
			 doc = sbx.build(getServletContext().getResourceAsStream(fichierConfig));
		} catch (IOException ioe) {
			logger.error("Problème de chargement du fichier de configuration : " + fichierConfig,ioe);
			throw new ServletException("Erreur dans la configuration de l'application",ioe);
		} catch (JDOMException jdmoe) {
			logger.error("Problème de parsing du fichier de configuration : " + fichierConfig,jdmoe);
			throw new ServletException("Erreur dans la configuration de l'application",jdmoe);
		}
		
		// Récupération de l'élément racine du document
		Element racine = doc.getRootElement();
		
		if (! "config-mvc".equals(racine.getName())) {
		// Racine incorrecte
			
			logger.error("La racine du document est incorrecte : " + racine.getName());
			throw new ServletException("Erreur dans la configuration de l'application");			
		}
		
		// Initialisation de la Map de configuration des routes
		configRoutes = new HashMap<String,Route>();
		
		// Récupération de la liste des éléments de type route du document
		List<Element> listeRoutes = racine.getChildren("route");
		
		if (listeRoutes.size() != 0) {
		// Liste non vide
			
			if (logger.isDebugEnabled()) {
				logger.debug("------------------------------------------------------");				
			}
			// Parcours des éléments de type route
			for (Element el : listeRoutes) {
				if (logger.isDebugEnabled()) {
					logger.debug("Element : " + el.getAttributeValue("name"));
					logger.debug("Nom :  " + el.getChildText("nom"));
					logger.debug("Classe :  " + el.getChildText("classe"));
					logger.debug("Vue :  " + el.getChildText("vue"));
					logger.debug("------------------------------------------------------");					
				}
				
				//Ajout de la route dans la Map
				configRoutes.put(el.getChildText("nom"), new Route(el.getChildText("classe"),el.getChildText("vue")));
				
			}
			
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("ATTENTION : la configuration est vide, aucune route chargée dans l'application !");				
			}
		}		
		
		if (logger.isInfoEnabled()) {
			logger.info("Fichier chargé avec succès");			
		}
		
	}

	private void chargeCatalogue(Catalogue monCatalogue) throws ServletException {
		
		// Récupération du chemin et du nom du fichier du catalogue de produits depuis le contexte de l'application
		String fichierCatalogue = getServletContext().getInitParameter(CtrlUtils.FICHIER_CATALOGUE);
		
		if (logger.isInfoEnabled()) {
			logger.info("Chargement du catalogue de produits : " + fichierCatalogue);			
		}
		
		// Instanciation d'un builder de document
		SAXBuilder sbx = new SAXBuilder();
		
		// Parsing du fichier du catalogue XML et instantiation d'un Document
		Document doc;		
		try {
			 doc = sbx.build(getServletContext().getResourceAsStream(fichierCatalogue));
		} catch (IOException ioe) {
			logger.error("Problème de chargement du fichier catalogue : " + fichierCatalogue,ioe);
			throw new ServletException("Erreur dans la configuration de l'application",ioe);
		} catch (JDOMException jdmoe) {
			logger.error("Problème de parsing du fichier catalogue : " + fichierCatalogue,jdmoe);
			throw new ServletException("Erreur dans la configuration de l'application",jdmoe);
		}
		
		// Récupération de l'élément racine du document
		Element racine = doc.getRootElement();
		
		if (! "catalogue".equals(racine.getName())) {
		// Racine incorrecte
			
			logger.error("La racine du document est incorrecte : " + racine.getName());
			throw new ServletException("Erreur dans la configuration de l'application");			
		}
		
		// Récupération de la liste des éléments de type produit du document
		List<Element> listeProduits = racine.getChildren("produit");
		
		if (listeProduits.size() != 0) {
		// Liste non vide
			
			if (logger.isDebugEnabled()) {
				logger.debug("------------------------------------------------------");				
			}
			// Parcours des éléments de type produit
			for (Element el : listeProduits) {
				if (logger.isDebugEnabled()) {
					logger.debug("Element : " + el.getAttributeValue("name"));
					logger.debug("Code :  " + el.getChildText("code"));
					logger.debug("Nom :  " + el.getChildText("nom"));
					logger.debug("Prix :  " + el.getChildText("prix"));
					logger.debug("Titre Page :  " + el.getChildText("titrePage"));
					logger.debug("Chemin Image :  " + el.getChildText("cheminImage"));
					logger.debug("Description :  " + el.getChildText("description"));
					logger.debug("------------------------------------------------------");					
				}
				
				// Ajout du produit dans le catalogue
				try {
					monCatalogue.getProduits().put(el.getChildText("code"),new Produit(el.getChildText("code"),
							   el.getChildText("nom"),
							   Double.parseDouble(el.getChildText("prix")),
							   el.getChildText("titrePage"),
							   el.getChildText("cheminImage"),
							   el.getChildText("description")));					
				} catch (NumberFormatException nfe) {
					logger.error("Problème de conversion de String en nombre : " + el.getChildText("prix"),nfe);
					throw new ServletException("Erreur dans la configuration de l'application",nfe);
				}
				
			}
			
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("ATTENTION : le catalogue est vide, aucun produit chargé dans l'application !");				
			}
		}		
		
		if (logger.isInfoEnabled()) {
			logger.info("Fichier chargé avec succès");
		}
		
	}
	
}
