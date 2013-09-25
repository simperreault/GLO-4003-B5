<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" %>
<html>
<head>
</head>
	<h1>'<c:out value="${firstName}"/>' 
		'<c:out value="${lastname}"/>' connected as '<c:out value="${username}"/>'</h1><hr>
	<div>
		<p>note : rien n'est fait pour la session</p>
	</div>
</html>