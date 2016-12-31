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
				<c:when test="${empty panier || panier.montantTotal == 0}">
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
							<th class="panier"></th>
						</tr>
						<c:forEach items="${panier.achats.values()}" var="achat">
							 <tr>
							 	<td class="nom">${achat.produit.nom}</td>
							 	<td class="prix">${achat.produit.prix}</td>
							 	<td class="quantite">${achat.quantite}</td>
							 	<td class="montant">${achat.montant}</td>
							 	<td class="action">
							 		<form class="panier" action="" method="post">
										<input name="source" type="hidden" value="${produit.code}">
										<input name="codeAchat" type="hidden" value="${achat.produit.code}">
										<input name="supprimer" type="submit" formaction="SupprimeAchat" value="Supprimer"/>
									</form>
							 	</td>
							 </tr>
						</c:forEach>
					</table>
					<br>
					<div class="panier">
						Nombre Total d'articles : ${panier.nombreTotal}<br>
						Montant Total (Euros) : ${panier.montantTotal}
					</div>
				</c:otherwise>
			</c:choose>
			<br>
			<div class="boutons">
				<form class="panier" action="" method="post">
					<input name="source" type="hidden" value="${produit.code}">
					<input name="vider" type="submit" formaction="VidePanier" value="Vider le panier"/>
					<input name="continuer" type="submit" formaction="RetourPanier" value="Continuer mes achats"/>
				</form>
			</div>
		</article>
		<hr>
		<footer>			
		</footer>
	</body>
</html>
