<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Liste de billets en vente</title>
	<!-- <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" /> -->
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />

	
</head>
<body>

<div class="container">
<h1>
	Liste de billets en vente 
</h1>
	<div class="row">
		<table  id="hor-minimalist-b">
			<thead>
				<tr>
					<th>Sport</th>
					<th>Endroit</th>
					<th>Date</th>
					<th>Prix</th>
					<th colspan="3"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ticket" items="${ticketList}">
					<c:url var="ticketUrl" value="/${ticket.id}" />
					<tr>
						<td>${ticket.event.sport}</td>
						<td>${ticket.event.location}</td>
						<td>${ticket.event.date}</td>
						<td>${ticket.price}$</td>
						<td>
							<a href="${ticketUrl}">Details</a>
						</td>
						<td>
							<a href="">Ajouter au panier</a>
						</td>
						<td>
							<a href="">Retirer</a>
						</td>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="row">
		<a href="<c:url value="/ticket/add" />" class="col-lg-offset=2 btn btn-primary">Ajouter un billet</a>
	</div>
</div>
</body>
</html>
