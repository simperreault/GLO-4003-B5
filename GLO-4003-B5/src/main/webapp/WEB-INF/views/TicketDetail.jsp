<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
</head>
	<h1>Details</h1><hr>
	<table id="hor-minimalist-b">
		<tr>
			<td>Date : </td><td>${ticket.event.date}</td>
		</tr>
		<tr>
			<td>Sport : </td><td>${ticket.event.sport} &nbsp; ${ticket.event.gender}</td>
		</tr>
		<tr>
			<td>Équipe domicile : </td><td>${ticket.event.homeTeam}</td>
		</tr>
		<tr>
			<td>Équipe visiteurs : </td><td>${ticket.event.visitorsTeam}</td>
		</tr>
		<tr>
			<td>Endroit : </td><td>${ticket.event.location}, ${ticket.event.stadium}</td>
		</tr>
		<tr>
			<td>Siege : </td><td>${ticket.section}</td>
		</tr>
		<tr>
			<td>Prix : </td><td>${ticket.price} $</td>
		</tr>
	</table>
	<a href="/ticket/buySingleTicket/${ticket.event.id}/${ticket.id}" class="btn">Acheter ce billet</a>
	<br>
</html>