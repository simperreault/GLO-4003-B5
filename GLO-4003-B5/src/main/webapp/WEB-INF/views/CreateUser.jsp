<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" %>
<html>
<head>
</head>
	<h1>Créer un compte utilisateur</h1><hr>
	<div>
		<form name="createUserForm" method="post" action="AddUser">
			<table>
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
			</table>
			 <br>
			<input class="btn" type="submit" value="S'enregistrer"/>
		</form>
	</div>
</html>