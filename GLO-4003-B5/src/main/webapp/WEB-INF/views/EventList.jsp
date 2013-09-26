<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
	<title>Liste des événements à venir</title>
</head>
<body>

<div class="container">
<h1>
	Liste des événements à venir
</h1>
<hr>
	<div class="row">
		<table  id="hor-minimalist-b">
			<thead>
				<tr>
					<th>Sport</th>
					<th>Catégorie</th>
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
						<c:if test="${sesacceslevel == 'Admin'}">
						<td>
							<a href="/event/delete/${event.id}">Retirer</a>
						</td>
						</c:if>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<c:if test="${sesacceslevel == 'Admin'}">
	<div class="row">
		<!-- TODO: /event/add -->
		<a href="<c:url value="/event/add" />" class="btn">Ajouter un événement</a>
	</div>
	</c:if>
</div>
</body>
</html>
