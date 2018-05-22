<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<c:url value="inc/style.css" />" />
<title>Nouvelle commande</title>
</head>
<body>
	<c:import url="/inc/menu.jsp" />
	<form action="commande" method="post">
		<c:set var="client" value="${ commande.client }" scope="request" />
		<fieldset>
			<legend>Informations client</legend>
			<label for="radio">Nouveau client? <span class="requis">*</span></label>
			<div id="radio">
				<input type="radio" id="oui" name="choixNouveauClient" value="oui" checked><label class="skipLabels" for="oui">Oui</label> <input type="radio" id="non"
					name="choixNouveauClient" value="non"
				><label class="skipLabels" for="non">Non</label>
			</div>
			<br>
			<div id="clientForm" name="clientForm">
				<c:import url="/inc/inc_client_form.jsp" />
			</div>
			<div id="clientSelect" style="display: none">
				<select id="listeClients" name="listeClients">
					<option value="" selected>Choisissez un client existant</option>
					<c:if test="${ !empty sessionScope.clients }">
						<c:forEach items="${ sessionScope.clients }" var="client">
							<option><c:out value="${ client.key } " /></option>
						</c:forEach>
					</c:if>
				</select><span class="erreur"><c:out value="${ form.erreurs.listeClients }" /></span>
			</div>
		</fieldset>
		<fieldset>
			<legend>Informations commande</legend>
			<label for="dateCommande">Date<span class="requis">*</span>
			</label><input id="dateCommande" name="dateCommande" type="text" disabled value="<c:out value="${ commande.date }"/>"></br> <label for="montantCommande">Montant <span
				class="requis"
			>*</span></label><input id="montantCommande" name="montantCommande" type="text" value="<c:out value="${ commande.montant==0.0? '' : commande.montant }"/>"><span class="erreur"><c:out
					value="${ form.erreurs.montantCommande }"
				/></span> </br> <label for="modepaiementCommande">Mode de paiement<span class="requis">*</span></label><input id="modepaiementCommande" name="modePaiementCommande" type="text"
				value="<c:out value="${ commande.modePaiement }"/>"
			><span class="erreur"><c:out value="${ form.erreurs.modePaiementCommande }" /></span> </br> <label for="statutPaiementCommande">Statut de paiement </label><input
				id="statutPaiementCommande" name="statutPaiementCommande" type="text" value="<c:out value="${ commande.statutPaiement }"/>"
			><span class="erreur"><c:out value="${ form.erreurs.statutPaiementCommande }" /></span> </br> <label for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label><input
				id="modeLivraisonCommande" name="modeLivraisonCommande" type="text" value="<c:out value="${ commande.modeLivraison }"/>"
			><span class="erreur"><c:out value="${ form.erreurs.modeLivraisonCommande }" /></span> </br> <label for="statutLivraisonCommande">Statut de livraison </label><input
				id="statutLivraisonCommande" name="statutLivraisonCommande" type="text" value="<c:out value="${ commande.statutLivraison }"/>"
			><span class="erreur"><c:out value="${ form.erreurs.statutLivraisonCommande }" /></span> </br>
			<p class="info">${ form.resultat }</p>
		</fieldset>
		<input class="boutons" type="submit" name="valider" value="Valider"> <input class="boutons" type="button" name="reinit" value="Remettre à zéro">
	</form>
	<script src="inc/client_form.js"></script>
</body>
</html>