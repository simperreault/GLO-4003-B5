<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
</head>
	<h1>Ajouter un Billet</h1><hr>
	<div class="error"> 
		<c:forEach var="objectError" items="${error}">
			<c:out value="${objectError.getDefaultMessage()}"></c:out> <br />
		</c:forEach>
	</div>
	<br>
	<form:form method="post" class="form-horizontal" modelAttribute="ticket">
		<table>
			<tr>
				<td>Type :</td>
				<td><form:select path="type" items="${ticketlist}" /></td>
			</tr>
			<tr>
				<td>Section :</td>
				<td><form:input path="section" /></td>
			</tr>
			<tr>
				<td>Siège :</td>
				<td><form:input path="seat" /></td>
			</tr>
			<tr>
				<td>Prix :</td>
				<td><form:input path="price" /></td>
			</tr>
			<tr>
				<td>Combien de billets :</td>
				<td><form:input path="howMany" /></td>
			</tr>
		</table>
		<br>
        <p><button class="btn" type="submit">Ajouter un billet</button></p>
    </form:form>
</html>
