<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete student by STUDENT_ID</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/delete-student" method = post>
		<br> <label for="GET-name">Delete student by STUDENT_ID:</label><br>
		<br> <input id="GET-name" type="text" name="STUDENT_ID"> <input
			type="submit" value="Delete">
	</form>
</body>
</html>