package com.jerems91.jeremsmvc.controller.dispatch;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
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

import com.jerems91.jeremsmvc.controller.beans.Route;

/**
 * Servlet implementation class Dispatcher
 */
public class Dispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(Dispatcher.class);
	
	private Map<String,Route> configRoutes;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// Chargement de la config MVC		
		chargeConfigMVC();
		
		// Affichage des routes configurées à partir de la Map
		logger.debug(configRoutes);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String chemin = request.getPathInfo();
		
		logger.debug(chemin);
		
		// Si on ne demande rien, retour à index.html
		if ((chemin == null) || "/".equals(chemin)) {
		    RequestDispatcher reqDisp = request.getRequestDispatcher("/index.html");
		    reqDisp.forward(request, response);
		    return;
		}
		
		// Récupération de la commande de traitement
		String commande = chemin.substring(1, chemin.length());
		
		// Récupération du nom de la classe
		String nomClasse = configRoutes.get(commande).getClasse();
		
		logger.debug(nomClasse);
		
		if (nomClasse.trim().isEmpty()) {
		// Chaine vide
			RequestDispatcher reqDisp = request.getRequestDispatcher("/WEB-INF/jsp/probleme.jsp");
			request.setAttribute("message", "Nom de classe non renseigné pour la commande " + commande);
		    reqDisp.forward(request, response);
		} else {
			try {
			    // Récupération de la classe de traitement
				Class<?> c = Class.forName(nomClasse);
				// Instanciation de la classe de traitement
			    IRoute ir = (IRoute) c.newInstance();
			    // Appel de la méthode de traitement dans la classe
			    ir.routeRequete(request, response,configRoutes.get(commande).getVue());
			} catch (ClassNotFoundException cne) {
				logger.error("Classe introuvable : " + nomClasse,cne);
			    request.setAttribute("message", "Classe " + nomClasse + " non trouvée !");
			    RequestDispatcher reqDisp = request.getRequestDispatcher("/WEB-INF/jsp/probleme.jsp");
			    reqDisp.forward(request, response);
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
		
		String fichierConfig = getServletContext().getInitParameter("mvc-config");
		
		logger.info("Chargement du fichier de configuration MVC : " + fichierConfig);
		
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
			
			logger.debug("------------------------------------------------------");
			// Parcours des éléments de type route
			for (Element el : listeRoutes) {
				logger.debug("Element : " + el.getAttributeValue("name"));
				logger.debug("Nom :  " + el.getChildText("nom"));
				logger.debug("Classe :  " + el.getChildText("classe"));
				logger.debug("Vue :  " + el.getChildText("vue"));
				logger.debug("------------------------------------------------------");
				
				//Ajout de la route dans la Map
				configRoutes.put(el.getChildText("nom"), new Route(el.getChildText("classe"),el.getChildText("vue")));
				
			}
			
		} else {
			logger.info("ATTENTION : la configuration est vide, aucune route chargée dans l'application !");
		}		
		
		logger.info("Fichier chargé avec succès");
		
	}

}
