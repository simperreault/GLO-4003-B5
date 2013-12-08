<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true" %>
<html>
<head>
</head>
	<h1>Modification d'un compte utilisateur</h1><hr>
	<p style="color:red;"> 
		<c:forEach var="objectError" items="${error}">
			<c:out value="${objectError.getDefaultMessage()}"></c:out> <br />
		</c:forEach>
	</p>

	<form:form method="post" class="form-horizontal" modelAttribute="user">
	<form:input path="username" type="hidden"/>
		<table>
			<tr>
				<td>Mot de passe :</td>
				<td><form:input path="password" type="password"/></td>
			</tr>
			<tr>
				<td>Pr�nom :</td>
				<td><form:input path="firstName" /></td>
			</tr>
			<tr>
				<td>Nom :</td>
				<td><form:input path="lastName" /></td>
			</tr>
			<tr>
				<td>Email :</td>
				<td><form:input path="email" /></td>
			</tr>
			<tr>
				<td>Sport pr�f�r� :</td>
				<td><form:input path="favSport" /></td>
			</tr>
			<tr>
				<td>Genre pr�f�r� :</td>
				<td><form:input path="favGender" /></td>
			</tr>
			<tr>
				<td>Type pr�f�r� :</td>
				<td><form:select path="favType" items="${typeList}" /></td>
			</tr>
			<tr>
				<td>Endroit pr�f�r� :</td>
				<td><form:input path="favLocation" /></td>
			</tr>
		</table>
		<br>

        <p><button class="btn" type="submit">Soumettre</button></p>
    </form:form>
</html>
