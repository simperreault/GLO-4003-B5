<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="true"%>
<html>
<head>
<title>Liste des événements à venir</title>
</head>
<body>

	<div class="container">
		<h1>Liste des événements à venir</h1>
		<hr>
		<div class="row">
			<form:form method="post" class="form-horizontal"
				modelAttribute="search">
				<table id="hor-minimalist-b">
					<thead>
						<tr>
							<th>Sport</th>
							<th>Catégorie</th>
							<th>Endroit</th>
							<th>Date</th>
							<th>Équipes</th>
							<th colspan="2"></th>
						</tr>
						<tr>
							<th><form:select path="sport">
									<form:option value="" label="--- Choisir ---" />
									<form:options items="${sportList}" />
								</form:select></th>
								<th></th>
								<th></th>
								<th><form:select path="days">
									<form:option value="0" label="--- Choisir ---" />
									<form:options items="${dayList}" />
								</form:select></th>
								<th><form:select path="team">
									<form:option value="" label="--- Choisir ---" />
									<form:options items="${teamList}" />
								</form:select></th>
								<th colspan="2"><input class="btn" type="submit"
								value="Rechercher" /></th>
						</tr>

					</thead>
					<tbody>
						<c:forEach var="event" items="${EventList}">
							<c:url var="eventUrl" value="/event/${event.id}" />
							<tr>
								<td>${event.sport}</td>
								<td>${event.gender}</td>
								<td>${event.location},${event.stadium}</td>
								<td><fmt:formatDate value="${event.date}"
										pattern="yyyy-MM-dd hh:mm" /></td>
								<td>${event.homeTeam} vs ${event.visitorsTeam}</td>
								<td><a href="${eventUrl}">Voir les billets disponibles</a></td>
								<c:if test="${sesacceslevel == 'Admin'}">
									<td><a href="/event/delete/${event.id}">Retirer</a></td>
								</c:if>
						</c:forEach>
					</tbody>
				</table>
			</form:form>
		</div>
		<c:if test="${sesacceslevel == 'Admin'}">
			<div class="row">
				<!-- TODO: /event/add -->
				<a href="<c:url value="/event/add" />" class="btn">Ajouter un
					événement</a>
			</div>
		</c:if>
	</div>
</body>
</html>
