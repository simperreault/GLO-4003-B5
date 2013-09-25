<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Liste des événements à venir</title>
	<!-- <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" /> -->
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />

</head>
<body>

<div class="container">
<h1>
	Liste des événements à venir
</h1>
	<div class="row">
		<table  id="hor-minimalist-b">
			<thead>
				<tr>
					<th>Sport</th>
					<th>Sexe</th>
					<th>Endroit</th>
					<th>Date</th>
					<th colspan="2"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="event" items="${EventList}">
					<c:url var="eventUrl" value="/event/${event.id}" />
					<tr>
						<td>${event.sport}</td>
						<td>${event.gender}</td>
						<td>${event.location},  ${event.stadium}</td>
						<td>${event.date}</td>
						<td>
							<a href="${eventUrl}">Voir les billets disponibles</a>
						</td>
						<td>
							<!-- ADMIN FUNCTION -->
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
