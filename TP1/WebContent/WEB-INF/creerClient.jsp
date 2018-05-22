
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8 "/>
<title>Nouveau client</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="inc/style.css" />" />
</head>
<body>
	<c:import url="/inc/menu.jsp" />

	<form action="client" method="post">
		<fieldset>
			<legend>Informations client</legend>
			<c:import url="/inc/inc_client_form.jsp" /><br>
			<p class="info"><c:out value="${ form.resultat }"/></p>
		</fieldset>
		<input class="boutons" type="submit" name="valider" value="Valider">
		<input class="boutons" type="button" name="reinit"
			value="Remettre à zéro">
	</form>
</body>
</html>