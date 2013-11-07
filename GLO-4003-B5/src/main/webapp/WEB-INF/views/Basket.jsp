<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
	<title>Panier d'achat</title>
</head>
<body>

<div class="container">
<h1>
	Panier d'achat
</h1>
<hr>
	<div class="row">
		<table  id="hor-minimalist-b">
			<thead>
				<tr>
					<th>Sport</th>
					<th>Date</th>
					<th>Endroit</th>
					<th>Type</th>
					<th>Prix</th>
					<th>Quantité</th>
					<th></th>
					<th></th>
					<th>Ajouter</th>
					<th colspan="3"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ticket" items="${basketD}">
					<c:url var="ticketUrl" value="/event/${ticket.get(0).event.id}/${ticket.get(0).id}" />
					<tr>
						<td>${ticket.get(0).event.sport}</td>
						<td>${ticket.get(0).event.date}</td>
						<td>${ticket.get(0).event.location}, ${ticket.get(0).event.stadium}</td>
						<td>${ticket.get(0).type}</td>
						<td>${ticket.get(0).price}$</td>
						<td>${ticket.size()}</td>
						<td><a href="${ticketUrl}">Details</a></td>
						<td>
							<a href="/ticket/deleteBasket/${ticket.get(0).event.id}/${ticket.get(0).id}">Retirer du panier</a>
						</td>
						<td><a href="/ticket/copyBasket/${ticket.get(0).event.id}/${ticket.get(0).id}">Ajouter un Billet Similaire</a></td>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<a href="<c:url value="/Purchase" />" class="btn">Passer au paiement</a>
</div>
</body>
</html>
