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
				</c:when>
				<c:otherwise>
					<h1>Contenu du panier</h1>
					<table class="panier">
						<tr>
							<th class="panier">Produit</th>
							<th class="panier">Prix (Euros)</th>
							<th class="panier">Nb</th>
							<th class="panier">Montant (Euros)</th>
						</tr>
						<c:forEach items="${panier.achats.values()}" var="achat">
							 <tr>
							 	<td class="nom">${achat.produit.nom}</td>
							 	<td class="prix">${achat.produit.prix}</td>
							 	<td class="quantite">${achat.quantite}</td>
							 	<td class="montant">${achat.montant}</td>
							 </tr>
						</c:forEach>
					</table>
					<br>
					<div class="panier">
						Montant Total : ${panier.montantTotal} Euros
					</div>
				</c:otherwise>
			</c:choose>
			<br>
			<div class="boutons">
				<form class="panier" action="VidePanier" method="post">
					<input name="source" type="hidden" value="${produit.code}">
					<input name="vider" type="submit" value="Vider le panier"/>
				</form>
				<form class="panier" action="RetourPanier" method="post">
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
