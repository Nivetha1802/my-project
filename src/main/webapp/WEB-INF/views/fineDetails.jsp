<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Fine Details</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="./script.js"></script>
</head>
<body>
    <div class="header">
        <h1>Digital Library</h1>
        <div class="nav">
            <a href="/login">Sign In</a>/<a href="/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div> 
    <div class="form-container">
        <h2> Fine Details</h2>
        <h3>Enter Book Details</h3>
        <form action="submitFineDetails" method="post" modelAttribute="finedet">

            <label path="id" for="id">ID number</label>
            <input path="id" type="text" id="id" name="id">
            </br>
            <button type="submit">Enter</button>
        </form>
    </div>
</body>
</html>