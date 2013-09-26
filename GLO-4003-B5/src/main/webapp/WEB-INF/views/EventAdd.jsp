<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
</head>
	<h1>Ajouter un Billet</h1><hr>
	<p style="color:red;"> 
		<c:forEach var="objectError" items="${error}">
			<c:out value="${objectError.getDefaultMessage()}"></c:out> <br />
		</c:forEach>
	</p>
	<form:form method="post" class="form-horizontal" modelAttribute="event">
		<table>
			<tr>
				<td>Sexe :</td>
				<td><form:input path="gender" /></td>
			</tr>
			<tr>
				<td>�quipe maison :</td>
				<td><form:input path="homeTeam" /></td>
			</tr>
			<tr>
				<td>�quipe visiteur :</td>
				<td><form:input path="visitorsTeam" /></td>
			</tr>
			<tr>
				<td>Endroit :</td>
				<td><form:input path="location" /></td>
			</tr>
			<tr>
				<td>Stade :</td>
				<td><form:input path="stadium" /></td>
			</tr>
			<tr>
				<td>Date :</td>
				<td><form:input path="date" /></td>
			</tr>
			<tr>
				<td>Time :</td>
				<td><form:input path="time" /></td>
			</tr>
		</table>
		<br>
        <p><button class="btn" type="submit">Submit</button></p>
    </form:form>
</html>