<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />

</head>
	<h1>Accueil</h1><hr>
	<div>
	<p>Sup ! Bienvenue !
	</p>
	</div>
		
	<c:if test='${currentPage == "Home.jsp"}'>
    	<c:out value="TEST"></c:out>
    	<c:set value="someclass" var="cssClass"></c:set>
    </c:if>
</html>