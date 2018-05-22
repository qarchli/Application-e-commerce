<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8 "/>
<title>Afficher commande</title>
<link type="text/css" rel="stylesheet" href="<c:url value="inc/style.css" />" />
</head>
<body>
	<c:import url="/inc/menu.jsp" />
	
		<p class="info">${ form.resultat }</p>
		<p>
			<b>Client</b>
		</p>
		<p>
			Nom:
			<c:out value="${ commande.client.nom}" />
		</p>
		<p>
			Prénom:
			<c:out value="${ commande.client.prenom}" />
		</p>
		<p>
			Adresse de livraison:
			<c:out value=" ${ commande.client.adresse}" />
		</p>
		<p>
			Téléphone:
			<c:out value="${ commande.client.telephone}" />
		</p>
		<p>
			Email:
			<c:out value="${ commande.client.email}" />
		</p>

		<p>
			<b>Commande</b>
		</p>
		<p>
			Date:
			<c:out value="${commande.date }" />
		</p>
		<p>
			Montant:
			<c:out value=" ${commande.montant }" />
		</p>
		<p>
			Mode de paiement:
			<c:out value=" ${commande.modePaiement }" />
		</p>
		<p>
			Statut de paiement:
			<c:out value="${commande.statutPaiement }" />
		</p>
		<p>
			Mode de livraison:
			<c:out value="${commande.modeLivraison }" />
		</p>
		<p>
			Statut de livraison:
			<c:out value="${commande.statutLivraison }" />
		</p>

	

</body>
</html>