<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<table>
			<tr>
				<td>Nom d'utilisateur :</td>
				<td>${user.username}</td>
			</tr>
			<tr>
				<td>Prénom :</td>
				<td>${user.firstName}</td>
			</tr>
			<tr>
				<td>Nom :</td>
				<td>${user.lastName}</td>
			</tr>
			<tr>
				<td>Email :</td>
				<td>${user.email}</td>
			</tr>
			<tr>
				<td>Sport préféré :</td>
				<td>${user.favSport}</td>
			</tr>
			<tr>
				<td>Genre préféré :</td>
				<td>${user.favGender}</td>
			</tr>
			<tr>
				<td>Type préféré :</td>
				<td>${user.favType.toString()}</td>
			</tr>
			<tr>
				<td>Endroit préféré :</td>
				<td>${user.favLocation}</td>
			</tr>
		</table>
		<br />
		<a href="/user/edit" class="btn">Edition</a><br />
</body>
</html>