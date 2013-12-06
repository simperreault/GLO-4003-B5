<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Acheter</title>
</head>
<body>
	<h1>Informations d'achat</h1>
	<hr>
	<div>${message}</div>
	<div class="error">
		<c:forEach var="objectError" items="${error}">
			<c:out value="${objectError.getDefaultMessage()}"></c:out>
			<br />
		</c:forEach>
	</div>
	<form:form method="post" class="form-horizontal" action="Purchase"
		modelAttribute="purchaseInfos">
		<table style="width: 100%">
			<!-- ; border:solid -->
			<tr>
				<td style="width: 50%">
					<!-- ; border:solid -->
					<table id="Purchaseform">
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
							<td>Type de paiement :</td>
							<td><form:select path="paymentType" items="${paymentType}" /></td>
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
				</td>
				<td style="width: 50%">
					<table id="hor-minimalist-b">
						<thead>
							<tr>
								<th>Sport</th>
								<th>Date</th>
								<th>Endroit</th>
								<th>Quantité</th>
								<th>Prix</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="ticket" items="${basketDisplay}">
								<c:url var="ticketUrl"
									value="/event/${ticket.get(0).event.id}/${ticket.get(0).id}" />
								<input id="ticketId" type="hidden" value="${ticket.get(0).id}" />
								<input id="eventId" type="hidden"
									value="${ticket.get(0).event.id}" />
								<tr>
									<td>${ticket.get(0).event.sport}</td>
									<td><fmt:formatDate value="${ticket.get(0).event.date}"
											pattern="dd/MM/yyyy hh:mm" /></td>
									<td>${ticket.get(0).event.location},
										${ticket.get(0).event.stadium}</td>
									<td>${ticket.size()}</td>
									<td>${ticket.get(0).price}$</td>
									
									<c:set var="totalAmount" value="${totalAmount + ticket.size()*ticket.get(0).price}"/> 
							</c:forEach>
							<c:if test="${basketDisplay.size() == 0}">
								<tr>
									<td colspan="10">Le panier est vide</td>
								</tr>
							</c:if>
						</tbody>
						<tbody>
						<tr><td></td><td></td><td></td><td><b>Total</b></td><td>${totalAmount}$</td></tr>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
		<br>

		<p>
			<button class="btn" type="submit">Acheter</button>
		</p>
	</form:form>
</body>
</html>