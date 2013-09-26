<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true" isELIgnored="false"%>
<html>
<c:choose>
	<c:when test="${ empty sesusername }">
		<form name="loginForm" method="post" action="connect">

			<table>
				<tr>
					<td>Pseudo :</td>
					<td align="right"><input type="text" name="username" /></td>
				</tr>
				<tr>
					<td>Mot de passe :</td>
					<td align="right"><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input class="btn"
						style="width: 155px" type="submit" value="Se connecter" /></td>
				</tr>
				<tr>
					<td></td>
					<td><a href="/CreateUser"
						style="text-align: right; font-size: 11px;">Pas de compte ?</a></td>
				</tr>
			</table>

		</form>
	</c:when>
	<c:when test = "${not empty sesusername }">
		Bonjour, <%= session.getAttribute( "sesusername" ) %>
	</c:when>
</c:choose>
</html>