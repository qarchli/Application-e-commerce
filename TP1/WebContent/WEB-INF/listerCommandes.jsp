
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
		<c:when test="${ !empty sessionScope.commandes }">
			<table>
				<tr>
					<th>Id Commande</th>
					<th>Id Client</th>
					<th>Date</th>
					<th>Montant</th>
					<th>Mode de paiement</th>
					<th>Statut de paiement</th>
					<th>Mode de livraison</th>
					<th>Statut de livraison</th>
					<th class="action">Action</th>
				</tr>
				<c:forEach items="${ sessionScope.commandes }" var="commande">
					<tr>
						<td><c:out value="${commande.value.id}" /></td>
						<td><c:out value="${commande.value.client.value.id}" /></td>
						<td><c:out value="${commande.value.date}" /></td>
						<td><c:out value="${commande.value.montant}" /></td>
						<td><c:out value="${commande.value.modePaiement}" /></td>
						<td><c:out value="${commande.value.statutPaiement}" /></td>
						<td><c:out value="${commande.value.modeLivraison}" /></td>
						<td><c:out value="${commande.value.statutLivraison}" /></td>
						<td><a href="suppressioncommande?id=${ commande.value.id }"><img alt="Action" src="inc/supprimer-icone.png"></a></td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<p class="erreur">Aucune commande enregistrée.</p>
		</c:otherwise>
	</c:choose>
</body>
</html>