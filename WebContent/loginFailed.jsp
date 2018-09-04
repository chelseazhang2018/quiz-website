<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Information Incorrect</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<h1>Please Try Again</h1>

<p>Your user name or password is incorrect or two passwords do not match. Please try again.</p>

<div id="fullscreen_bg" class="fullscreen_bg">

<div class="container">
<form class="form-signin" action="LoginServlet" method="post">
<h1 class="form-signin-heading text-muted">Sign In</h1> 
    User Name: <input class="form-control" placeholder="User Name" type="text" name="name"/> 
    <br/>
    Password: <input class="form-control" placeholder="password" type="password" name="password"/>
    <input type="checkbox" name = "remember me" value = "yes"/> Auto-Login<br/>
    <input class="btn btn-lg btn-primary btn-block" type="submit" value="login"/>
    <a class="btn btn-lg btn-primary btn-block"  href="createAccount.jsp">Create New Account</a>
<br/>
<a class="btn btn-lg btn-primary btn-block" href="index2.jsp">Guest Login</a>

</form>
</div>
</div>
</body>
</html>

