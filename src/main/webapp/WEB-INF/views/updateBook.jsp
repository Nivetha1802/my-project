<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Management</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="header">
        <h1>Digital Library</h1>
        <div class="nav">
            <a href="/login">Sign</a>/<a href = "/signup">Signup</a>
            <a href="#">Logout</a>
        </div>
    </div>
    <div class="bookManagement-container">
    <div class="container">
        <div class="sidebar">
            <h2>Book Management</h2>
            <ul>
                <li><a href="bookManagement.jsp">Add Book</a></li>
                <li><a href="updateBook.jsp">Update book</a></li>
                <li><a href="deleteBook.jsp">Delete Book</a></li>
            
            </ul>
        </div>
        <div class="form-container">
            <h2>Enter Book Details</h2>
            <label for="bookid">Book ID</label>
            <input type="text" id="bookid" name="bookid">

            <label for="bookName">Book name</label>
            <input type="text" id="bookName" name="bookName">

            <label for="subject">Book subject</label>
            <input type="text" id="subject" name="subject">

            <label for="info">Book info</label>
            <input type="text" id="info" name="info">
        </br>
            <button type="submit">Update</button>
            
        </div>
    </div>
</div>
</body>
</html>
