<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
<script src="<c:url value="/resources/js/jquery-ui-timepicker-addon.js" />"></script>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/jquery-ui-timepicker-addon.css" />" />
</head>
<c:if test="${sesacceslevel == 'Admin'}">
	<script>
		$(function() {
			$( "#datepicker" ).datetimepicker({
				changeMonth: true,
				showOtherMonths: true,
				selectOtherMonths: true
			});
		});
	</script>

	<h1>Ajouter un �v�nement</h1><hr>
	<p style="color:red;"> 
		<c:forEach var="objectError" items="${error}">
			<c:out value="${objectError.getDefaultMessage()}"></c:out> <br />
		</c:forEach>
	</p>
	<form:form method="post" class="form-horizontal" modelAttribute="event">
		<table>
			<tr>
				<td>Cat�gorie :</td>
				<td><form:select path="gender">
    					<form:option value="M" label="Masculin" />
    					<form:option value="F" label="F�minin" />
    				</form:select></td>
			</tr>
			<tr>
				<td>Sport :</td>
				<td><form:select path="sport" items="${sportList}" /></td>
			</tr>
			<tr>
				<td>�quipe maison :</td>
				<td><form:input path="homeTeam" /></td>
			</tr>
			<tr>
				<td>�quipe visiteur :</td>
				<td><form:input path="visitorsTeam" /></td>
			</tr>
			<tr>
				<td>Endroit :</td>
				<td><form:input path="location" /></td>
			</tr>
			<tr>
				<td>Stade :</td>
				<td><form:input path="stadium" /></td>
			</tr>
			<tr>
				<td>Date :</td>
				<td><form:input path="date" id="datepicker"/></td>
			</tr>
		</table>
		<br>
        <p><button class="btn" type="submit">Cr�er l'�v�nement</button></p>
    </form:form>
    </c:if>
       <c:if test="${sesacceslevel != 'Admin'}">
    	get out hacker!
   	 </c:if>
</html>