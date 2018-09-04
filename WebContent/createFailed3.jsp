<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Question</title>
<link rel="stylesheet" href="style/main.css">
<link data-require="bootstrap-css@3.1.1" data-semver="3.1.1" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />
</head>
<body>


<div id="fullscreen_bg" class="fullscreen_bg">

<div class="container">
<form class="form-signin" action="creationServlet" method="post">
<h1>Name and Password Cannot Be Empty</h1>
<h1 class="form-signin-heading text-muted">Please Re-Enter</h1> 
    User Name: <input class="form-control" placeholder="User Name" type="text" name="name"/> 
    <br/>
    Password: <input class="form-control" placeholder="password" type="password" name="password"/>
    Confirm Password: <input class="form-control" type="password" name="password2"/><br/>
    
    <input class="btn btn-lg btn-primary btn-block" type="submit" value="create"/>
    
<br/>
<a class="btn btn-lg btn-primary btn-block" href="index2.jsp">Guest Login</a>

</form>
</div>
</div>

</body>
</html>