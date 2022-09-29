<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang = "en">
<head>
<meta charset="ISO-8859-1">
<title>Main page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
</head>
<body>
	<div>
  <h3>Choose option:</h3>
<input type="checkbox" class="slectOne" data-id="Find all groups" onchange="window.location.href='pages/FindAllGroups.jsp'"/>Find all groups with less or equals student count<br>
<input type="checkbox" class="slectOne" data-id="Find all students" onchange="window.location.href='pages/FindAllStudents.jsp'"/>Find all students related to course with given name<br>
<input type="checkbox" class="slectOne" data-id="Add new student" onchange="window.location.href='pages/AddNewStudent.jsp'"/>Add new student<br>
<input type="checkbox" class="slectOne" data-id="Delete student" onchange="window.location.href='pages/DeleteStudent.jsp'"/>Delete student by STUDENT_ID<br>
<input type="checkbox" class="slectOne" data-id="Add a student to course" onchange="window.location.href='pages/AddStudentToCourse.jsp'"/>Add a student to the course (from a list)<br>
<input type="checkbox" class="slectOne" data-id="Remove the student from courses" onchange="window.location.href='pages/RemoveStudentFromCourse.jsp'"/>Remove the student from one of his or her courses<br>
</div>
<button type="submit" id="1">Click Me!</button>
<script type="text/javascript">
$(document).ready(function(){
	$('.slectOne').on('change', function() {
	   $('.slectOne').not(this).prop('checked', false);
	   $('#result').html($(this).data( "id" ));
	   if($(this).is(":checked"))
	   	$('#result').html($(this).data( "id" ));
	   else
	   	$('#result').html('Empty...!');
	});
	});
</script>
</body>
</html>