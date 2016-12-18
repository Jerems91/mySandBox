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
			<div class="photo">
				<img alt="${produit.nom}" src="${produit.cheminImage}">
			</div>
			<br>
			<div class="prix">
				Prix : ${produit.prix} Euros
			</div>
			<br>
			<div class="prix">
				<p>Description : ${produit.description}</p>
			</div>
			<br>
			<div class="boutons">
				<form action="PrdPrec" method="post">
					<input name="source" type="hidden" value="${produit.code}">
					<input name="precedent" type="submit" value="Précédent"/>
				</form>
				<form action="PrdSuiv" method="post">
					<input name="source" type="hidden" value="${produit.code}">
					<input name="suivant" type="submit" value="Suivant"/>
				</form>
				<form action="AjoutPanier" method="post">
					<input name="source" type="hidden" value="${produit.code}">
					<input name="ajout" type="submit" value="Ajouter au panier"/>
				</form>
				<form action="AffichePanier" method="post">
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
