<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add new student</title>
</head>
<body>
	<form action="/add-student">
		<br> <label for="GET-name">Add new student:</label><br>
		<br> <input id="GET-name" type="text" name="name"> <input
			type="submit" value="Add">
	</form>
	<br>
	<form action="/sql-jdbc-school/index.jsp">
		<input type="submit" value="Back to main page" />
	</form>
</body>
</html>