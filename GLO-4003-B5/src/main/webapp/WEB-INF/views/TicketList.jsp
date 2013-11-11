<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="true" %>
<html>
<head>
	<title>Liste de billets en vente</title>
<script src="<c:url value="/resources/js/searchAutoPostback.js" />"></script>
</head>
<body>

<div class="container">
<h1>
	Liste de billets en vente 
</h1>
<hr>
<div id="confirmation" style="display:none; background-color:#316e66;">
<p>Le billet a �t� ajout� � votre panier</p>
</div>
	<div class="row">
		<table  id="hor-minimalist-b">
			<thead>
				<tr>
					<th>Sport</th>
					<th>Date</th>
					<th>Endroit</th>
					<th>Type</th>
					<th>Prix</th>
					<th>Billets similaires</th>
					<th colspan="3"></th>
				</tr>
			</thead>
			<tbody>
				<script type="text/javascript">
					function strRemoveFromEndCharUntilSlash(str)
					{
						str = str.substr(0, str.lastIndexOf("/") + 1 );
						
						return str;
					}
					function updateUrl(itId)
					{
						//alert("id = " + itId);
						var selectedValue =  document.getElementById("nbTicketsList" + itId).value
						var oldHref = document.getElementById("idTicketsAdd" + itId).href; 
						var newUrl = strRemoveFromEndCharUntilSlash(oldHref) + selectedValue;
						document.getElementById("idTicketsAdd" + itId).href = newUrl;
					}
				</script>
				<c:forEach var="ticketSubList" items="${ticketList}">
					<c:set var="counterTicketGroup" value="0" />
					<c:forEach var="ticket" items="${ticketSubList}" varStatus="stat">
						<c:url var="ticketUrl" value="/event/${ticket.event.id}/${ticket.id}" />
						<c:if test="${stat.first}">
        				<c:set var="counterTicketGroup" value="${counterTicketGroup + 1}"/>
						<tr>
							<td>${ticket.event.sport}</td>
							<td><fmt:formatDate value="${ticket.event.date}"
										pattern="dd/MM/yyyy hh:mm" /></td>
							<td>${ticket.event.location}, ${ticket.event.stadium}</td>
							<td>${ticket.type}</td>
							<td>${ticket.price}$</td>
							<td>${ticketSubList.size()}</td>
							<td>
								<a href="${ticketUrl}">Details</a>
							</td>
							<td>
								<!--<c:set var="tmpSize" value="${ticketSubList.size()}" />-->
								<select id="nbTicketsList<c:out value="${counterTicketGroup}"/>" 
										name="nbTicketsList<c:out value="${counterTicketGroup}"/>" 
										onchange="updateUrl(<c:out value="${counterTicketGroup}"/>)">
									<c:forEach var="i" begin="1" end="${ticketSubList.size()}">
										<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>
									</c:forEach>
								</select>
								<a id="idTicketsAdd<c:out value="${counterTicketGroup}"/>" 
									href="/ticket/addBasket/${ticket.event.id}/${ticket.id}/1">Ajouter au panier</a>
							</td>
							<c:if test="${sesacceslevel == 'Admin'}">
							<td>
								<a href="/ticket/delete/${ticket.event.id}/${ticket.id}">Retirer</a>
							</td>
							</c:if>
						</tr>
						</c:if>
					</c:forEach>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<p style="color:red;"> 
	<c:if test="${sesacceslevel == 'Admin'}">
	<div class="row">
		<a href="<c:url value="/ticket/add/${eventID}" />" class="col-lg-offset=2 btn btn-primary">Ajouter un billet</a>
	</div>
	</c:if>
</div>
</body>
</html>
