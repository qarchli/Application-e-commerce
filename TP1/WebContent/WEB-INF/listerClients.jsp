
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8 />
<title>Liste des clients</title>
<link type="text/css" rel="stylesheet" href="<c:url value="inc/style.css" />" />
</head>
<body>
	<c:import url="/inc/menu.jsp" />
	<c:choose>
		<c:when test="${ !empty sessionScope.clients }">
			<table>
				<tr>
					<th>ID</th>
					<th>Nom</th>
					<th>Prénom</th>
					<th>Adresse</th>
					<th>Téléphone</th>
					<th>Email</th>
					<th class="action">Action</th>
				</tr>
				<c:forEach items="${ sessionScope.clients  }" var="client">
					<tr>
						<td><c:out value="${client.value.id}" /></td>
						<td><c:out value="${client.value.nom}" /></td>
						<td><c:out value="${client.value.prenom}" /></td>
						<td><c:out value="${client.value.adresse}" /></td>
						<td><c:out value="${client.value.telephone}" /></td>
						<td><c:out value="${client.value.email}" /></td>
						<td><a href="suppressionclient?id=${client.value.id}"><img alt="Action" src="inc/supprimer-icone.png"></a></td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<p class="erreur">Aucun client enregistré.</p>
		</c:otherwise>
	</c:choose>
</body>
</html>