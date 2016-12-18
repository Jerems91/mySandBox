<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
import="com.jerems91.jeremsshop.modele.Panier,com.jerems91.jeremsshop.modele.Achat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>	
	<head>
		<meta charset="ISO-8859-1">
		<title>Panier</title>
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
			<c:choose>
				<c:when test="${empty panier}">
					<h1>Votre panier est vide !</h1>
					<hr>
				</c:when>
				<c:otherwise>
					<h1>Contenu du panier</h1>
					<hr>
					<div class="panier">
						<ol>
							<c:forEach items="${panier.achats.values()}" var="achat">
								<li>Produit : ${achat.produit.nom}, Prix : ${achat.produit.prix} Euros, Quantité : ${achat.quantite}, Montant : ${achat.montant} Euros</li>
							</c:forEach>
						</ol>
						<br>
						Montant Total du Panier : ${panier.montantTotal} Euros
					</div>
				</c:otherwise>
			</c:choose>
			<hr>
			<br>
			<div class="boutons">
				<form action="RetourPanier" method="post">
					<input name="source" type="hidden" value="${produit.code}">
					<input name="continuer" type="submit" value="Continuer mes achats"/>
				</form>
			</div>
		</article>
		<hr>
		<footer>			
		</footer>
	</body>
</html>
