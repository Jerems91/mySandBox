<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Erreur de routage</title>
	</head>
	<body>
		<article>
			<h1>Erreur de routage dans le Dispatcher</h1>
			<div><%= request.getAttribute("message") %></div>
		</article>			
	</body>
</html>
