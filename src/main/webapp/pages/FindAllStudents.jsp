<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Find all students related to course with given name</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/find-all-students" method = post>
		<br> <label for="GET-name">Find all students related to
			course with given name:</label><br>
		<br> <input id="GET-name" type="text" name="name"> <input
			type="submit" value="Search">
	</form>
</body>
</html>