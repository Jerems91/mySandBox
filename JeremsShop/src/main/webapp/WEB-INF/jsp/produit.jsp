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
			<div class="nbpanier">Nb Articles : 
				<c:choose>
					<c:when test="${empty panier}">0</c:when>
					<c:otherwise>${panier.nombreTotal}</c:otherwise>
				</c:choose>
			</div>
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
				<form class="produit" action="" method="post">
					<input name="source" type="hidden" value="${produit.code}">
					<input name="precedent" type="submit" formaction="PrdPrec" value="Précédent"/>
					<input name="suivant" type="submit" formaction="PrdSuiv" value="Suivant"/>
					<input name="ajout" type="submit" formaction="AjoutPanier" value="Ajouter au panier"/>
					<input name="panier" type="submit" formaction="AffichePanier" value="Afficher le panier"/>
				</form>
			</div>
		</article>
		<hr>
		<div class="produit">${msgProduit}</div>
		<footer>			
		</footer>
	</body>
</html>
