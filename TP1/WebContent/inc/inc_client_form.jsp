



<label for="nomClient">Nom <span class="requis">*</span>
</label>
<input id="nomClient" name="nomClient" type="text" value="<c:out value="${ client.nom }"/>"><span class="erreur"><c:out value="${ form.erreurs.nomClient }"/></span>
</br>
<label for="prenomClient">Prénom </label>
<input id="prenomClient" name="prenomClient" type="text" value="<c:out value="${ client.prenom }"/>"><span class="erreur"><c:out value="${ form.erreurs.prenomClient }"/></span>
</br>
<label for="adresseClient">Adresse de livraison <span
	class="requis">*</span></label>
<input id="adresseClient" name="adresseClient" type="text" value="<c:out value="${ client.adresse }"/>"><span class="erreur"><c:out value="${ form.erreurs.adresseClient }"/></span>
</br>
<label for="telephoneClient">Numéro de télephone <span class="requis">*</span>
</label>
<input id="telephoneClient" name="telephoneClient" type="text" value="<c:out value="${ client.telephone }"/>"><span class="erreur"><c:out value="${ form.erreurs.telephoneClient }"/></span>
</br>
<label for="emailClient">Adresse email </label>
<input id="emailClient" name="emailClient" type="text" value="<c:out value="${ client.email }"/>"><span class="erreur"><c:out value="${ form.erreurs.emailClient }"/></span>
</br>
</br>


