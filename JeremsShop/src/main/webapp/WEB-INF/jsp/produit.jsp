<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
import="com.jerems91.jeremsshop.modele.Produit"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>	
	<head>
		<meta charset="ISO-8859-1">
		<title>${produit.titrePage}</title>
		<link rel="stylesheet" type="text/css" href="../css/styles.css">
	</head>
	<body>
	<!-- JSP avec JSTL -->
		<header>
			<img alt="logo" src="../images/logo.png" width="100px">
			<h1>Bienvenue sur JeremsShop</h1>
		</header>
		<hr>
		<article>
			<h1>${produit.nom}</h1>
			<table class="fiche">
				<tr>
					<td class="photo">
						<img class="produit" title="${produit.nom}" src="${produit.cheminImage}">
					</td>
					<td class="infos">
						<p class="prix">Prix : ${produit.prix} Euros</p>
						<p class="description">${produit.description}</p>
					</td>
				</tr>
			</table>
			<br>
			<div class="boutons">
				<form class="produit" action="PrdPrec" method="post">
					<input name="source" type="hidden" value="${produit.code}">
					<input name="precedent" type="submit" value="Précédent"/>
				</form>
				<form class="produit" action="PrdSuiv" method="post">
					<input name="source" type="hidden" value="${produit.code}">
					<input name="suivant" type="submit" value="Suivant"/>
				</form>
				<form class="produit" action="AjoutPanier" method="post">
					<input name="source" type="hidden" value="${produit.code}">
					<input name="ajout" type="submit" value="Ajouter au panier"/>
				</form>
				<form class="produit" action="AffichePanier" method="post">
					<input name="source" type="hidden" value="${produit.code}">
					<input name="panier" type="submit" value="Afficher le panier"/>
				</form>
			</div>
		</article>
		<hr>
		<footer>			
		</footer>
	</body>
</html>
