<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
<!-- 	<link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
 -->
</head>
<body>
<div id="background">
		<div id="page">
			<div id="header">
				<div id="logo" style="width:56%;float:left;align:bottom"><a href="/Home"><img src="<c:url value="/resources/images/logo.png" />" alt="LOGO" height="112" width="118"></a></div>
					<div id="login" style="float:left;">
						<jsp:include page="Login.jsp" />
					</div>
				<div id="navigation">
					<ul>
						<li class="<c:if test='${currentPage == "Home.jsp"}'>
					    	<c:out value="selected"></c:out>
					    </c:if>">
							<a href="/Home">Accueil</a>
						</li>
						<li class="<c:if test='${currentPage == "EventList.jsp"}'>
					    	<c:out value="selected"></c:out>
					    </c:if>">
							<a href="/event/list">Événements</a>
						</li>
						<li class="">
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
							<br>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="footer">
			<div>
				<ul class="navigation">
					<li class="<c:if test='${currentPage == "Home.jsp"}'>
					    	<c:out value="active"></c:out>
					    </c:if>">
						<a href="/Home">Accueil</a>
					</li>
					<li class="<c:if test='${currentPage == "EventList.jsp"}'>
					    	<c:out value="active"></c:out>
					    </c:if>">
						<a href="/event/list">Événements</a>
					</li>
					<li class="">
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
	</div>

</body>
</html>