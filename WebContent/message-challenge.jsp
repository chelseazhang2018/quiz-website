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
Send Challenge To Friends



<% DBInterface db = new DBInterface();
String userName = (String)session.getAttribute("userName");
ArrayList<String> friends = db.getFriend(userName);
%>

 <% 
for (int i = 0; i < friends.size(); i++){
	String nameToPrint = friends.get(i);
	
	%>

	<form action="sendNewChallenge" method="post">
	<% String quizTitle = request.getParameter("quizTitle");
String url = "quizSummary.jsp?quizTitle=" + quizTitle;

%>

	<a href="friend-profile.jsp?submit=<%=nameToPrint%>"><%=nameToPrint%></a>
	<input type="hidden" value="<%=nameToPrint %>" name = "friend name"  value ="<%=nameToPrint%>"/>
	<input type="hidden" name = "url" value = "<%=url %>"/>
    <input class="btn btn-primary" type="submit" value="Send Challenge" />    
    </form>
	<br/>
	<%
	}
       db.DBShutDown(); 
       %>
       <a href="index.jsp">Back</a>




</body>
</html>