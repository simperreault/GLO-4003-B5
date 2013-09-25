<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" %>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />

</head>
	<h1>Login</h1><hr>
	<div>
		<form name="loginForm" method="post" action="Connect">
			<table>
				<thead>Entrez vos info D: !2</thead>
				<tr>
					<td>
						<label>Username :</label>
					</td>
					<td>
						<input type="text" name="username"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>Password :</label>
					</td>
					<td>
						<input type="password" name="password"/>
					</td>
				</tr>
				<tr>
			        <td colspan="2">
			            <input type="submit" value="Connect"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</html>