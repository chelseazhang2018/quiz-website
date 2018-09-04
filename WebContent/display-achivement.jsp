<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*"%>
<%@ page import="database.*"%>
<%@ page import="question.*"%>
<%@ page import="support.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Display Achievement</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
Congratulations! You've got the following new achievements.
<br/>

<%DBInterface db = new DBInterface(); 
String userName = (String)session.getAttribute("userName");

%>

<div class = "achivement">
	Achievement:
	<%
	//(DBInterface)session.getAttribute("connection")
		//DBInterface db = new DBInterface();
	ArrayList<String> achivements = (ArrayList<String>)session.getAttribute("achievements");
	%>
	<ul class="list-group">
	<%
	for(int i = 0;i<achivements.size();i++){
	%>
	<li class="list-group-item"> <%=achivements.get(i) %> </li>
	<%
	}
	%>
	</ul>
</div>
<%db.DBShutDown(); %>
<a href = "index.jsp">back</a>
</body>
</html>