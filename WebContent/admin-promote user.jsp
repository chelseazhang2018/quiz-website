<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ page import="database.DBInterface"%>
        <%@ page import="javax.servlet.*"%>
        <%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Promote User</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
Promote User


<% DBInterface db = new DBInterface();
String userName = (String)session.getAttribute("userName");
ArrayList<String> users = db.getListOfUsers();
%>
       <% 
for (int i = 0; i < users.size(); i++){
	String nameToPrint = users.get(i);
	if (nameToPrint.equals(userName) || db.isAdministrator(nameToPrint))
		continue;
	%>

	<form action="promoteUser" method="post">
	<a href="friend-profile.jsp?submit=<%=nameToPrint%>"><%=nameToPrint%></a>
	<input class="form-control" type="hidden" value="<%=nameToPrint %>" name = "user name" style = "display: inline;"/>
    <input class="btn btn-warning" type="submit" value="Promote" style = "display: inline;"/>    
    </form>
	<br/>
	<%
	}
       db.DBShutDown(); 
       %>
	<br/>
	<a href="administration.jsp">back</a>

</body>
</html>