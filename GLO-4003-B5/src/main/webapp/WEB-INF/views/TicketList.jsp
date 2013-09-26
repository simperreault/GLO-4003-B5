<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
	<title>Liste de billets en vente</title>
</head>
<body>

<div class="container">
<h1>
	Liste de billets en vente 
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
					<th colspan="3"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ticket" items="${ticketList}">
					<c:url var="ticketUrl" value="/event/${ticket.event.id}/${ticket.id}" />
					<tr>
						<td>${ticket.event.sport}</td>
						<td>${ticket.event.date}</td>
						<td>${ticket.event.location}, ${ticket.event.stadium}</td>
						<td>${ticket.type}</td>
						<td>${ticket.price}$</td>
						<td>
							<a href="${ticketUrl}">Details</a>
						</td>
						<td>
							<a href="">Ajouter au panier</a>
						</td>
						<td>
							<!-- ADMIN FUNCTION -->
							<a href="">Retirer</a>
						</td>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<c:if test="${sesacceslevel == 'admin'}">
	<div class="row">
		<a href="<c:url value="/ticket/add/${eventID}" />" class="col-lg-offset=2 btn btn-primary">Ajouter un billet</a>
	</div>
	</c:if>
</div>
</body>
</html>
