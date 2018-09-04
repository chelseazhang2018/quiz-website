<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ page import="support.Message"%>
        <%@ page import="javax.servlet.*"%>
        <%@ page import="java.util.*"%>
        <%@ page import="database.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>

<body>
<div class="span3 well">


<% DBInterface db = new DBInterface();
String userName = (String)session.getAttribute("userName");
ArrayList<String> friends = db.getFriend(userName);
%>

 <% 
for (int i = 0; i < friends.size(); i++){
	String nameToPrint = friends.get(i);
	
	%>

	<form action="sendNewMessage" method="post">
	Content: <input type="text" name = "content"/> <br/>
	<a href="friend-profile.jsp?submit=<%=nameToPrint%>"><%=nameToPrint%></a>
	<input class="span3"  type="hidden" value="<%=nameToPrint %>" name = "to user"  value ="<%=nameToPrint%>"/>
    <input class="btn btn-warning" type="submit" value="Send Message" />    
    </form>
	<br/>
	<%
	}
       db.DBShutDown(); 
       %>
<a href="index.jsp">Back</a>
</div>
</body>
</html>