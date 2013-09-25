<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />

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
				<td>Seat :</td>
				<td><form:input path="seat" /></td>
			</tr>
			<tr>
				<td>Owner :</td>
				<td><form:input path="owner" /></td>
			</tr>
			<tr>
				<td>Price :</td>
				<td><form:input path="price" /></td>
			</tr>
			<tr>
				<td>Resell Price :</td>
				<td><form:input path="resellprice" /></td>
			</tr>
		</table>

        <p><button type="submit">Submit</button></p>
    </form:form>
</html>