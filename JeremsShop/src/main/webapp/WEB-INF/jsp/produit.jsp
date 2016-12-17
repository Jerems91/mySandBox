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
				<form action="Controleur?pg=${produit.code}" method="post">
					<input name="achat" type="submit" value="Acheter"/>
					<input name="suivant" type="submit" value="Suivant"/>
					<input name="panier" type="submit" value="Panier"/>
				</form>
			</div>
		</article>
		<hr>
		<footer>			
		</footer>
	</body>
</html>
