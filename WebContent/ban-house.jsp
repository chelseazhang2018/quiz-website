<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="database.*"%>
<%@ page import="question.*"%>
<%@ page import="support.*"%>
<%@ page import="javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ban House</title>
</head>
<body>
<% String name = (String)session.getAttribute("userName"); 
	DBInterface db = new DBInterface();
   	String bannedUntil = db.getBannedTime(name);
   	db.DBShutDown();
%>
<h1>Sorry <%= name %>! </h1>
You have been banned for inappropriate behaviors and will regain freedom until <%= bannedUntil %>!
<form action="logoutServlet" method="post">
    <input type="submit" value="logout"/>
</form>
</body>
</html>