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
<title>Insert title here</title>
</head>
<body>
<%
if((boolean)session.getAttribute("practiceMode")){
%>
<%
DBInterface db = new DBInterface();
ArrayList<String> achievements = db.addNewActivity((String)session.getAttribute("userName"), "", 0, 0, true);
System.out.print("Multi-page achievements number is " + achievements.size());
if(!achievements.isEmpty()){
	session.setAttribute("achievements", achievements);
	db.DBShutDown();
	%>
	
	<a href ="display-achivement.jsp">exit practice mode</a>
	<%
}
else {
%>
<a href ="quizSummary.jsp?quizTitle=<%=(String)session.getAttribute("quizTitle")%>">exit practice mode</a >

<%}
}
%>
</body>
</html>