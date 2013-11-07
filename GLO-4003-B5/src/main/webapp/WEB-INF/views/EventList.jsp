<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="true"%>
<html>
<meta name="foo" content="test">
<head>
<script src="<c:url value="/resources/js/searchAutoPostback.js" />"></script>
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
							<th>Billets</th>
							<th colspan="2"></th>
						</tr>
						<tr>
							<th><form:select path="sport" id="sportList"
									onChange="post_to_url('#');">
									<form:option value="" label="--- Choisir ---" />
									<form:options items="${sportList}" />
								</form:select></th>
							<th></th>
							<th></th>
							<th><form:select path="days" id="dateList"
									onChange="post_to_url('#');">>
									<form:option value="0" label="--- Choisir ---" />
									<form:options items="${dayList}" />
								</form:select></th>
							<th><form:select path="team" id="teamList"
									onChange="post_to_url('#');">
									<form:option value="" label="--- Choisir ---" />
									<form:options items="${teamList}" />
								</form:select></th>
							<th colspan="2">
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
										pattern="dd/MM/yyyy hh:mm" /></td>
								<td>${event.homeTeam}vs${event.visitorsTeam}</td>
								<td>${event.ticketList.size()}</td>
								<td><a href="${eventUrl}">Billets disponibles</a></td>
								<c:if test="${sesacceslevel == 'Admin'}">
									<td><a href="/event/delete/${event.id}">Retirer</a></td>
								</c:if>
						</c:forEach>
						<c:if test="${message.length() > 0}">
							<tr><td>${message}</td><td></td><td></td><td></td><td></td><td></td></tr>
						</c:if>
					</tbody>
				</table>
			</form:form>
		</div>
		<c:if test="${sesacceslevel == 'Admin'}">
			<div class="row">
				<a href="<c:url value="/event/add" />" class="btn">Ajouter un
					événement</a>
			</div>
		</c:if>
	</div>
</body>
</html>


