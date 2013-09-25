<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" %>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />

</head>
	<h1>'<c:out value="${firstName}"/>' 
		'<c:out value="${lastname}"/>' connected as '<c:out value="${username}"/>'</h1><hr>
	<div>
		<p>note : rien n'est fait pour la session</p>
	</div>
</html>