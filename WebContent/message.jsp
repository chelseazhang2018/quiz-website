<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ page import="database.DBInterface"%>
        <%@ page import="support.Message"%>
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
<jsp:include page="navbar.jsp" flush="true"/>


		<form action="message-send new message.jsp" method="post">
    <input class="btn btn-warning" type="submit" value="Send New Message"/> 
    <br/><br/>
</form>


<%--start new message --%>
New Message<br/>
<% DBInterface db = new DBInterface();
String userName = (String)session.getAttribute("userName");

ArrayList<Message> messages = db.getAllMessages(userName);
%>
       <% 
       CharSequence challenge = "***";
for (int i = 0; i < messages.size(); i++){
	Message message = messages.get(i);
	String content = message.getContent();
	
	if (content.equals("&&&") && message.isUnread()){
		%>
		Friend Request: 
		<%= message.getSender() %> sends you a friend request at <%= message.getTime()%>
		
		<form action="friendRequest" method="post">
		<input  type = "hidden" value = "<%=message.getSender()%>" name = "sender name" />
		<input type = "hidden" value = "<%=i + ""%>" name = "message position" />
    <input class="btn btn-warning" type="submit" value="Accept" name ="action"/> 
    <input class="btn btn-warning" type="submit" value="Decline" name = "action"/>
    <br/>
</form>

		<%
	}
	%>
	
	<%
	
	if (content.contains(challenge) && message.isUnread()){ %>
	<%String url = content.substring(3); %>
	Challenge:
	<%= message.getSender() %> sends you a challenge at <%= message.getTime()%>

	<a href="<%= url %>"> Take the Challenge</a>
	<form action="markMessageRead" method="post">
		<input type = "hidden" value = "<%=i %>" name = "message position" />
		
    <input class="form-control" type="submit" value="Mark Read"/>
    <br/>
</form>
	<br/>
	
	<%} %>
	
	
	
	
	<%
	 if (!content.equals("&&&") && message.isUnread() && !content.contains(challenge)){
		%>
		<%= message.getSender() %> Sends You a Message: 
		<%= message.getContent() %> at <%= message.getTime()%> <br/> 
		<form action="markMessageRead" method="post">
		<input type = "hidden" value = "<%=i %>" name = "message position" />
		
    <input class="form-control" type="submit" value="Mark Read"/>
    <br/>
</form>
		
		<%
	}
	%>
	<br/>
	<%} %>
	<br/><br/>
<%--end new message --%>
	
	
<%--start old message --%>
<h2>Read Message</h2><br/>
<ul class="list-group">
       <% 
for (int i = 0; i < messages.size(); i++){
	Message message = messages.get(i);
	String content = message.getContent();
	if (content.equals("&&&") && (!message.isUnread())){
		String sender = message.getSender();
		String receiver = message.getRecepient();
		Boolean isFriend = db.isFriend(sender, receiver);
		String print = (isFriend) ?"Accepted":"Declined";
		%>
		Friend Request:
	 <li class="list-group-item"><%= message.getSender() %> Sent You a friend request at <%= message.getTime()%></li>
		<br/>
		<%
	}	
	%>
	<% 
	if (content.contains(challenge) && !message.isUnread()){ %>
	<%String url = content.substring(3); %>
	Challenge:
	<%= message.getSender() %> sends you a challenge at <%= message.getTime()%>
	<li class="list-group-item"><a href="<%=url%>">Take the Challenge </a></li>
	<br/>
	
	<%} %>
	
	
	<%
	 if (!content.equals("&&&") && (!message.isUnread()) && !content.contains(challenge)){
		%>
		<%= message.getSender() %> Sends You a Message: 
		<%= message.getContent() %> at <%= message.getTime()%><br/>
		<%
	}
	%>
	
	<%} %>
	</ul>
	<br/>

<%--end new message --%>





<a href="index.jsp">Back</a>
	
</body>
</html>