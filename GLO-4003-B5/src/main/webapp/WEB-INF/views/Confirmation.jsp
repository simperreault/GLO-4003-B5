<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<meta name="currentPage" content="Home">
<content tag="currentPage">Home</content>
<head>
<title>Confirmation</title>
</head>
<body>
	<p><c:out value="${message}"></c:out></p>
	<h1>Confirmation</h1>
	<hr>
	<div>
		<p>L'achat a �t� effectu� avec succ�s. Un courriel de confirmation vous a �t� envoy�.</p>
	</div>
</body>
</html>