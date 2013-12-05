<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<meta name="currentPage" content="Home">
<content tag="currentPage">Home</content>
<head>
<title>À propos</title>
</head>
<body>
	<div id="message" class="error">${errorMsg}</div>
	<h1>À propos</h1>
	<hr>
	<div>
		<h3>Site réalisé par :</h3>
		Alexis Légaré-Julien<br>
		Benjamin Pelletier<br>
		Charl-Philip Boutet<br>
		Ghita Ouliz<br>
		Mathieu Bolduc<br>
		Alexis Légaré-Julien<br>
		dans le cadre du cours <a href="http://www2.ulaval.ca/les-etudes/cours/repertoire/detailsCours/glo-4003-architecture-logicielle.html">GLO-4003</a>
	</div>
</body>
</html>