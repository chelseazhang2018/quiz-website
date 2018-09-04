<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create New Quiz</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<h1>Create New Quiz</h1>
<br/><br/>


<form action="createNewQuizServlet" method="post">
    Quiz Title: <input class="form-control" type="text" name="title"/> <br/>
    Quiz Description: <input class="form-control" type="text" name="description"/> <br/>

Random Order
					<select class="form-control" name="random order">
						<option value="yes">Yes</option>
						<option value="no">No</option>
					</select><br/>


Quiz Display
					<select class="form-control" name="quiz display">
						<option value="single">Single</option>
						<option value="multiple">Multiple</option>
					</select><br/>
					
Category
					<select class="form-control" name="category">
						<option value="General">General</option>
						<option value="Science">Science</option>
						<option value="Culture">Culture</option>
						<option value="Animals">Animals</option>
						<option value="History">History</option>
						<option value="Movies">Movies</option>
						<option value="Geography">Geography</option>
						<option value="Others">Others</option>
					</select><br/>
					
Tags <br/>
					<input class="form-control" type="text" name="tags"><br/>
                  
                   
					
Allow Immediate Correction
					<select class="form-control" name="immediate correction">
						<option value="yes">Yes</option>
						<option value="no">No</option>
					</select>
					<br/>

					

Allow Practice
					<select class="form-control" name="allow practice">
						<option value="yes">Yes</option>
						<option value="no">No</option>
					</select>
					
					<input class="form-control" type = "hidden" name="allow practice" value ="no">
					<br/>
					
					

    <input class = "btn btn-primary" type="submit" value="create"/>
</form>
<a href="index.jsp">back</a>
</body>
</html>