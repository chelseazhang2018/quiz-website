<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="database.DBInterface"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Website Statistics</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<h1>Statistics</h1>

<% DBInterface db = new DBInterface(); 
   int quizNumber = db.getTotalNumberOfQuizTaken();
   int userNumber = db.getTotalNumberOfUsers();
%>
Number of Users: <%= userNumber %> <br/>
Number of Quizzes Taken: <%= quizNumber  %> <br/>


	<a href="administration.jsp">Back</a>
	
</body>
</html>