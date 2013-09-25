<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />

</head>
	<h1>Ajouter un Billet</h1><hr>
	<form:form method="post" class="form-horizontal" modelAttribute="ticket">
		<table>
			<tr>
				<td>Price :</td>
				<td><form:input path="price" /></td>
			</tr>
		</table>

        <p><button type="submit">Submit</button></p>
    </form:form>
</html>