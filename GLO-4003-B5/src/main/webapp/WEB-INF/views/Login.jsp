<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true" isELIgnored="false"%>
<html>
<c:choose>
	<c:when test="${ empty sesusername }">
		<form name="loginForm" method="post" action="connect">

			<table>
				<tr>
					<td>
						Pseudo :
					</td>
					<td >
						<input type="text" name="username"/>
					</td>
					<td>
						Mot de passe :
					</td>
					<td>
						<input type="password" name="password"/>
					</td>
					<td id="errorMsg">
						${errorMsg}
					</td>
			        <td>
			            <input class="btn" type="submit" value="Se connecter"/>
					</td>
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
