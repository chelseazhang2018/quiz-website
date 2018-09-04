<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*"%>
<div class="container-fluid">
    <!-- Second navbar for categories -->
    <nav class="navbar navbar-default">
      <div class="container">
      <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-4">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <% String userName1 = (String)session.getAttribute("userName"); %>

         <p>Welcome <%=userName1%>!</p>
        </div>
<div class="collapse navbar-collapse" id="navbar-collapse-3">
          <ul class="nav navbar-nav navbar-right">

	<li><i class="fa fa-home"></i><a href="index.jsp">Home</a></li>
	<li><i class="fa fa-gamepad"></i><a href="quiz.jsp">play</a></li>
	<li><i class="fa fa-pencil"></i><a href="quiz-create.jsp">create</a></li>
	<li><i class="fa fa-slideshare"></i><a href="friend.jsp">friends</a></li>
	<li><i class="fa fa-envelope-o"></i><a href="message.jsp">mailbox 	
	<%DBInterface db1 = new DBInterface();
	 session = request.getSession();
	String userName = (String) session.getAttribute("userName");
	//System.out.println("new message"+db.getNewMessages(userName).size());
	if (db1.getNewMessages(userName).size() != 0){
		%>
		(<%=db1.getNewMessages(userName).size() %>)
		<% 
	}
	db1.DBShutDown();
	%>
	</a> 
	</li>  
	
	<%
	
	if((boolean)session.getAttribute("isAdmin")){
		%>
		
		<li><i class="fa fa-gavel"></i><a href="administration.jsp">administration</a></li>
		<%
	}
	%>
	<li><a class="btn btn-default btn-outline btn-circle" href="logoutServlet">Logout</a></li>
	</ul>
</div>
</div>
</nav>
</div>