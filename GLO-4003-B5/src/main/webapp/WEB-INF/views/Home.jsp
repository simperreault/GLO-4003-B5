<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<meta name="currentPage" content="Home">
<content tag="currentPage">Home</content>
<head>
<title>Accueil</title>
</head>
<body>
	<p><c:out value="${message}"></c:out></p>
	<h1>Accueil</h1>
	<hr>
	<div>
		<p>Sup ! Bienvenue !</p>
	</div>
</body>
</html>