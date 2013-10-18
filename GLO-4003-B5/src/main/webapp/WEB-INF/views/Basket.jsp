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
					<th></th>
					<th></th>
					<th>Ajouter</th>
					<th colspan="3"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ticket" items="${basket}">
					<c:url var="ticketUrl" value="/event/${ticket.event.id}/${ticket.id}" />
					<tr>
						<td>${ticket.event.sport}</td>
						<td>${ticket.event.date}</td>
						<td>${ticket.event.location}, ${ticket.event.stadium}</td>
						<td>${ticket.type}</td>
						<td>${ticket.price}$</td>
						<c:if test="${ticket.type} == GENERAL">
						<td>
							Quantité : <form:input path="" />
						</td>
						</c:if>	
						<td>
							<a href="${ticketUrl}">Details</a>
						</td>
						<td>
							<a href="/ticket/deleteBasket/${ticket.event.id}/${ticket.id}">Retirer du panier</a>
						</td>
						<td><a href="/ticket/copyBasket/${ticket.event.id}/${ticket.id}">Ajouter un Billet Similaire</a></td>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>
