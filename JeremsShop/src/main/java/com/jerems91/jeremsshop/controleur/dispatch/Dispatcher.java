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
import com.jerems91.jeremsshop.modele.Catalogue;

/**
 * Servlet implementation class Dispatcher
 */
public class Dispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(Dispatcher.class);
	
	private static final String MVC_CONFIG = "mvcConfig";
	
	private static final String PAGE_ACCUEIL = "pageAccueil";
	private static final String PAGE_PROBLEME = "pageProbleme";
	
	private static final String CATALOGUE = "catalogue";
	
	private String pageAccueil;
	private String pageProbleme;
	private Map<String,Route> configRoutes;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// R�cup�ration du chemin et du nom de la page d'accueil depuis le contexte de l'application
		pageAccueil = getServletContext().getInitParameter(PAGE_ACCUEIL);
		
		// R�cup�ration du chemin et du nom de la page d'erreur depuis le contexte de l'application
		pageProbleme = getServletContext().getInitParameter(PAGE_PROBLEME);
		
		// Chargement de la config MVC
		chargeConfigMVC();
		
		// Affichage des routes configur�es � partir de la Map
		if (logger.isDebugEnabled()) {
			logger.debug(configRoutes);			
		}
		
		//Instanciation du catalogue et stockage dans le contexte de l'application
		Catalogue monCatalogue = new Catalogue();
		this.getServletContext().setAttribute(CATALOGUE, monCatalogue);
		
		// Affichage du contenu du catalogue
		if (logger.isDebugEnabled()) {
			logger.debug(monCatalogue);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// R�cup�ration du chemin de traitement demand�
		String chemin = request.getPathInfo();
		
		if (logger.isDebugEnabled()) {
			logger.debug(chemin);			
		}
		
		// Si on ne demande rien, retour � index.html
		if ((chemin == null) || "/".equals(chemin)) {
		    request.getRequestDispatcher(pageAccueil).forward(request, response);
		    return;
		}
		
		// R�cup�ration de la commande de traitement
		String commande = chemin.substring(1, chemin.length());
		
		// R�cup�ration du nom de la classe
		String nomClasse = configRoutes.get(commande).getClasse();
		
		if (logger.isDebugEnabled()) {
			logger.debug(nomClasse);			
		}
		
		if (nomClasse.trim().isEmpty()) {
		// Chaine vide
			request.setAttribute("message", "Nom de classe non renseign� pour la commande " + commande);
			request.getRequestDispatcher(pageProbleme).forward(request, response);
		} else {
			try {
			    // R�cup�ration de la classe de traitement
				Class<?> c = Class.forName(nomClasse);
				// Instanciation de la classe de traitement
			    ITraitement ir = (ITraitement) c.newInstance();
			    // Appel de la m�thode de traitement dans la classe
			    ir.routeRequete(request, response,configRoutes.get(commande).getVue());
			} catch (ClassNotFoundException cne) {
				logger.error("Classe introuvable : " + nomClasse,cne);
			    request.setAttribute("message", "Classe " + nomClasse + " non trouv�e !");
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
		
		// R�cup�ration du chemin et du nom du fichier de configuration depuis le contexte de l'application
		String fichierConfig = getServletContext().getInitParameter(MVC_CONFIG);
		
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
			logger.error("Probl�me de chargement du fichier de configuration : " + fichierConfig,ioe);
			throw new ServletException("Erreur dans la configuration de l'application",ioe);
		} catch (JDOMException jdmoe) {
			logger.error("Probl�me de parsing du fichier de configuration : " + fichierConfig,jdmoe);
			throw new ServletException("Erreur dans la configuration de l'application",jdmoe);
		}
		
		// R�cup�ration de l'�l�ment racine du document
		Element racine = doc.getRootElement();
		
		if (! "config-mvc".equals(racine.getName())) {
		// Racine incorrecte
			
			logger.error("La racine du document est incorrecte : " + racine.getName());
			throw new ServletException("Erreur dans la configuration de l'application");			
		}
		
		// Initialisation de la Map de configuration des routes
		configRoutes = new HashMap<String,Route>();
		
		// R�cup�ration de la liste des �l�ments de type route du document
		List<Element> listeRoutes = racine.getChildren("route");
		
		if (listeRoutes.size() != 0) {
		// Liste non vide
			
			if (logger.isDebugEnabled()) {
				logger.debug("------------------------------------------------------");				
			}
			// Parcours des �l�ments de type route
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
				logger.info("ATTENTION : la configuration est vide, aucune route charg�e dans l'application !");				
			}
		}		
		
		if (logger.isInfoEnabled()) {
			logger.info("Fichier charg� avec succ�s");			
		}
		
	}

}
