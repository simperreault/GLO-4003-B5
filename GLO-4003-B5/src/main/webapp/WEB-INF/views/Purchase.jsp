<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Acheter</title>
</head>
<body>
<h1>Informations d'achat</h1>
		<hr>
		<div>${message}</div>
		<div>${error}</div>
	<form:form method="post" class="form-horizontal" action="Purchase"
		modelAttribute="purchaseInfos">
		<table>
			<tr>
				<td>Prénom :</td>
				<td><form:input path="firstName" /></td>
			</tr>
			<tr>
				<td>Nom :</td>
				<td><form:input path="lastName" /></td>
			</tr>
			<tr>
				<td>Adresse :</td>
				<td><form:input path="adress" /></td>
			</tr>
			<tr>
				<td>Courriel :</td>
				<td><form:input path="email" /></td>
			</tr>
			<tr>
				<td>Type de paiement :</td>
				<td><form:input path="paymentType" /></td>
			</tr>
			<tr>
				<td>Numero de carte de crédit :</td>
				<td><form:input path="cardNumber" /></td>
			</tr>
			<tr>
				<td>Numéro de vérification :</td>
				<td><form:input path="verificationNumber" /></td>
			</tr>
		</table>
		<br>

		<p>
			<button class="btn" type="submit">Acheter</button>
		</p>
	</form:form>
</body>
</html>