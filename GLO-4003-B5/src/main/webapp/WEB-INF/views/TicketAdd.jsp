<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
</head>
	<h1>Ajouter un Billet</h1><hr>
	<p style="color:red;"> 
		<c:forEach var="objectError" items="${error}">
			<c:out value="${objectError.getDefaultMessage()}"></c:out> <br />
		</c:forEach>
	</p>
	<form:form method="post" class="form-horizontal" modelAttribute="ticket">
		<table>
			<tr>
				<td>Section :</td>
				<td><form:input path="section" /></td>
			</tr>
			<tr>
				<td>Siège :</td>
				<td><form:input path="seat" /></td>
			</tr>
			<tr>
				<td>Propriétaire :</td>
				<td><form:input path="owner" /></td>
			</tr>
			<tr>
				<td>Prix :</td>
				<td><form:input path="price" /></td>
			</tr>
			<tr>
				<td>Prix de revente :</td>
				<td><form:input path="resellprice" /></td>
			</tr>
		</table>
		<br>
        <p><button class="btn" type="submit">Submit</button></p>
    </form:form>
</html>