<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ page import="database.DBInterface"%>
        <%@ page import="javax.servlet.*"%>
        <%@ page import="java.util.*"%>
        
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



<form action="friend-add.jsp" method="post">
Search For New Friend: <input class="form-control" type="text" name= "friend name"></input>
    <input class="btn btn-success"  type="submit" value="Search"/>
</form>
<h2>My Friends:</h2>
 <br/>
<% DBInterface db = new DBInterface();
String userName = (String)session.getAttribute("userName");
ArrayList<String> friends = db.getFriend(userName);
%>
       <% 
for (int i = 0; i < friends.size(); i++){
	String nameToPrint = friends.get(i);
	
	%>

<a href="friend-profile.jsp?submit=<%=nameToPrint%>"><%=nameToPrint%></a>
	<form action="removeFriend" method="post">
	<input class="form-control" type="hidden" value="<%=nameToPrint %>" name = "friend name"/>
    <input class="btn btn-warning" type="submit" value="Remove Friend"/>
</form>
	<br/>
	<%} %>
	<br/>
	<a href="index.jsp">back</a>
	
</body>
</html>