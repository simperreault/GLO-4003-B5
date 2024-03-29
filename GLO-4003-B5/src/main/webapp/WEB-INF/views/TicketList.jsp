<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="true"%>
<html>
<head>
<title>Liste de billets en vente</title>
<script src="<c:url value="/resources/js/searchAutoPostback.js" />"></script>
</head>
<body>

	<div class="container">
		<h1>Liste de billets en vente</h1>
		<hr>
		<div class="row">
			<table id="hor-minimalist-b">
				<thead>
					<tr>
						<th>Sport</th>
						<th>Date</th>
						<th>Endroit</th>
						<th>Type</th>
						<th>Prix</th>
						<th colspan="2">Billets similaires</th>
						<th colspan="3"></th>
					</tr>
				</thead>
				<tbody>
					<c:set var="counterTicketGroup" value="0" />
					<c:forEach var="ticketSubList" items="${ticketList}">
						<c:forEach var="ticket" items="${ticketSubList}" varStatus="stat">
							<c:url var="ticketUrl"
								value="/event/${ticket.event.id}/${ticket.id}" />
							<c:if test="${stat.first}">
								<c:set var="counterTicketGroup"
									value="${counterTicketGroup + 1}" />
								<tr>
									<td>${ticket.event.sport}</td>
									<td><fmt:formatDate value="${ticket.event.date}"
											pattern="dd/MM/yyyy hh:mm" /></td>
									<td>${ticket.event.location},${ticket.event.stadium}</td>
									<td>${ticket.type}</td>
									<td>${ticket.price}$</td>
									<td>${ticketSubList.size()}</td>
									<td><a href="${ticketUrl}">Details</a></td>
									<td>
										<!--<c:set var="tmpSize" value="${ticketSubList.size()}" />-->
										<select
										id="nbTicketsList<c:out value="${counterTicketGroup}"/>"
										name="nbTicketsList<c:out value="${counterTicketGroup}"/>">
											<c:forEach var="i" begin="1" end="${ticketSubList.size()}">
												<option value="<c:out value="${i}"/>"><c:out
														value="${i}" /></option>
											</c:forEach>
									</select>
									<td>
										<button class="btn"
											onclick="AddTickets('/ticket/addBasket/${ticket.event.id}',
        									'nbTicketsList<c:out value="${counterTicketGroup}"/>',
        									'${ticket.id}')">Ajouter
											au panier</button>
									</td>
									</td>
									<c:if test="${sesacceslevel == 'Admin'}">
										<td><a
											href="/ticket/delete/${ticket.event.id}/${ticket.id}">Retirer</a>
										</td>
									</c:if>
								</tr>
							</c:if>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<p style="color: red;">
			<c:if test="${sesacceslevel == 'Admin'}">
				<div class="row">
					<a href="<c:url value="/ticket/add/${eventID}" />"
						class="col-lg-offset=2 btn btn-primary">Ajouter un billet</a>
				</div>
			</c:if>
	</div>
</body>
</html>
