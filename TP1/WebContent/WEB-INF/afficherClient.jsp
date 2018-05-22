<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8 "/>
<title>Afficher client</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="inc/style.css" />" />
</head>
<body>
	<c:import url="/inc/menu.jsp" />
		<p class="info">
			<c:out value="${ form.resultat }" />
		</p>
		
		<p>
			<b>Client</b>
		</p>
		<p>
			ID:
			<c:out value="${ client.id}" />
		</p>
		<p>
			Nom:
			<c:out value="${ client.nom}" />
		</p>
		<p>
			Prénom:
			<c:out value="${ client.prenom}" />
		</p>
		<p>
			Adresse de livraison:
			<c:out value=" ${ client.adresse}" />
		</p>
		<p>
			Téléphone:
			<c:out value="${ client.telephone}" />
		</p>
		<p>
			Email:
			<c:out value="${ client.email}" />
		</p>
		<p>
</body>
</html>