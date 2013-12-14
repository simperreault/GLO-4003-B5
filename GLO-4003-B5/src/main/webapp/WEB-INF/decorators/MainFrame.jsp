<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title><sitemesh:write property='title' /></title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />
<link type="text/css" rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<c:if test="${errorMsg.length() > 0}">
	<script>
		$(document).ready(function() {
			$("#errorMsg").slideToggle("slow");
		});
	</script>
</c:if>
<c:if test="${confirmationMsg.length() > 0}">
	<script>
		$(document).ready(function() {
			$("#confirmationMsg").slideToggle("slow");
		});
	</script>
</c:if>
<sitemesh:write property='head' />
</head>
<body>
	<div id="background">
		<div id="page">
			<div id="header">
				<div id="logo">
					<a href="/Home"> <img
						src="<c:url value="/resources/images/logo.png" />" alt="LOGO"
						height="112" width="118">
					</a>
				</div>
				<div id="login">
					<jsp:include page="Login.jsp" />
				</div>
				<div id="navigation">
					<ul>
						<li><a href="/Home">Accueil</a></li>
						<li><a href="/event/list">Évènements</a></li>
						<li><a href="/about">À propos</a></li>
						<li><a href="/contact">Contact</a></li>
						<li><a href="/purchase/Basket">Panier</a></li>
					</ul>
				</div>
			</div>
			<div id="contents">
				<div class="box">
					<div>
						<div class="body">
							<div id="confirmationMsg" class="trigger-confirmationMsg">${confirmationMsg}</div>
							<div id="errorMsg" class="trigger-errorMsg">${errorMsg}</div>
							<p>${page.properties["meta.foo"]}</p>
							<sitemesh:write property='body' />
							<br>
						</div>
					</div>
				</div>
			</div>
			<div id="footer">
				<div>
					<ul class="navigation">
						<li><a href="/Home">Accueil</a></li>
						<li><a href="/event/list">Évènements</a></li>
						<li><a href="/about">À propos</a></li>
						<li><a href="/contact">Contact</a></li>
						<li><a href="/purchase/Basket">Panier</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

</body>
</html>