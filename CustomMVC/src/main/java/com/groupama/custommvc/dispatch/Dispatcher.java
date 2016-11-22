package com.groupama.custommvc.dispatch;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.groupama.custommvc.beans.Route;

/**
 * Servlet implementation class Dispatcher
 */
public class Dispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String,Route> configRoutes;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		// Chargement de la config MVC		
		chargeConfigMVCXml();
		
		// Affichage des routes configurées à partir de la Map
		System.out.println(configRoutes);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String chemin = request.getPathInfo();
		
		System.out.println(chemin);
		
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
		
		System.out.println(nomClasse);
		
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
			} catch (ClassNotFoundException e) {
			    request.setAttribute("message", "Classe " + nomClasse + " non trouvée !");
			    RequestDispatcher reqDisp = request.getRequestDispatcher("/WEB-INF/jsp/probleme.jsp");
			    reqDisp.forward(request, response);
			} catch (InstantiationException ie) {
			// impossible d'instancier cette classe !			
			    throw new ServletException(ie);
			} catch (IllegalAccessException iae) {
			// le constructeur n'est pas visible !
			    throw new ServletException(iae);
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void chargeConfigMVCXml() throws ServletException {
		
		String fichierConfig = getServletContext().getInitParameter("mvc-config-xml");
		
		System.out.println("Chargement du fichier de configuration : " + fichierConfig);
		
		// Instanciation d'un builder de document
		SAXBuilder sbx = new SAXBuilder();
		
		// Parsing du fichier de configuration XML et instantiation d'un Document
		Document doc;		
		try {
			 doc = sbx.build(getServletContext().getResourceAsStream(fichierConfig));
		} catch (IOException ioe) {
			System.err.println("\nProblème de chargement du fichier de configuration : " + fichierConfig);
			throw new ServletException("Erreur dans la configuration de l'application",ioe);
		} catch (JDOMException jdmoe) {
			System.err.println("\nProblème de parsing du fichier de configuration : " + fichierConfig);
			throw new ServletException("Erreur dans la configuration de l'application",jdmoe);
		}
		
		// Récupération de l'élément racine du document
		Element racine = doc.getRootElement();
		
		if (! "config-mvc".equals(racine.getName())) {
		// Racine incorrecte
			
			System.err.println("\nLa racine du document est incorrecte : " + racine.getName());
			throw new ServletException("Erreur dans la configuration de l'application");			
		}
		
		// Initialisation de la Map de configuration des routes
		configRoutes = new HashMap<String,Route>();
		
		// Récupération de la liste des éléments de type route du document
		List<Element> listeRoutes = racine.getChildren("route");
		
		if (listeRoutes.size() != 0) {
		// Liste non vide
			
			System.out.println("------------------------------------------------------");
			// Parcours des éléments de type route
			for (Element el : listeRoutes) {
				System.out.println("Element : " + el.getAttributeValue("name"));
				System.out.println("Nom :  " + el.getChildText("nom"));
				System.out.println("Classe :  " + el.getChildText("classe"));
				System.out.println("Vue :  " + el.getChildText("vue"));
				System.out.println("------------------------------------------------------");
				
				//Ajout de la route dans la Map
				configRoutes.put(el.getChildText("nom"), new Route(el.getChildText("classe"),el.getChildText("vue")));
				
			}
			
		} else {
			System.out.println("ATTENTION : la configuration est vide, aucune route chargée dans l'application !");
		}		
		
		System.out.println("Fichier chargé avec succès");
		
	}

}
