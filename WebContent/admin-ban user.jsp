<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="database.DBInterface"%>
        <%@ page import="javax.servlet.*"%>
        <%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ban User Account</title>
</head>
<body>
Ban User Account


<% DBInterface db = new DBInterface();
String userName = (String)session.getAttribute("userName");
ArrayList<String> users = db.getListOfUsers();
%>
       <% 
for (int i = 0; i < users.size(); i++){
	String nameToPrint = users.get(i);
	if (nameToPrint.equals(userName))
		continue;
	%>
    <form action="banUser" method="post">
        <a href="friend-profile.jsp?submit=<%=nameToPrint%>"><%=nameToPrint%></a>
        <input type="hidden" value="<%=nameToPrint %>" name = "userName" />
        <input type="text"  name = "time" /> (min)
    <input type="submit" value="Ban User" />
    </form>
	<br/>
	<%
	}
       db.DBShutDown(); 
       %>
       <a href="administration.jsp">back</a>
	<br/>

</body>
</html>