<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<!-- 
	 <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/styleSheet.css" />" />
 -->
</head>
<body>
<div id="background">
		<div id="page">
			<div id="header">
				<div id="logo">
					<a href="/Home"><img src="<c:url value="/resources/images/logo.png" />" alt="LOGO" height="112" width="118"></a>
				</div>
				<div id="navigation">
					<ul>
						<li>
							<a href="/Home">Accueil</a>
						</li>
						<li>
							<a href="/event/list">Événements</a>
						</li>
						<li class="selected">
							<a href="">TODO</a>
						</li>
						<li>
							<a href="">TODO</a>
						</li>
						<li>
							<a href="">À propos</a>
						</li>
						<li>
							<a href="">Contact</a>
						</li>
						<li>
							<a href="">Panier</a>
						</li>
					</ul>
				</div>
			</div>
			<div id="contents">
				<div class="box">
					<div>
						<div class="body">
							<jsp:include page="${currentPage}" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="footer">
			<div>
				<ul class="navigation">
					<li>
						<a href="/Home">Accueil</a>
					</li>
					<li>
						<a href="/event/list">Événements</a>
					</li>
					<li class="active">
						<a href="">TODO</a>
					</li>
					<li>
						<a href="">TODO</a>
					</li>
					<li>
						<a href="">À propos</a>
					</li>
					<li>
						<a href="">Contact</a>
					</li>
					<li>
						<a href="">Panier</a>
					</li>
					<li>
						<a href="/CreateUser">S'enregistrer</a><!-- Possibly put it as include somewhere in the futur -->
					</li>
					<li>
						<a href="/Login">Login</a><!-- Possibly put it as include somewhere in the futur -->
					</li>
				</ul>
			</div>
		</div>
	</div>

</body>
</html>