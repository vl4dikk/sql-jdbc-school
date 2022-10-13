<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add new student</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/add-student" method = post>
		<br> <label for="GET-name">Add new student:</label><br>
		<br> <input id="GET-name" type="text" name="firstName" placeholder="FirstName"> <br>
		<input id="GET-name" type="text" name="lastName" placeholder="LastName"><br>
		<input
			type="submit" value="Add">
	</form>
</body>
</html>