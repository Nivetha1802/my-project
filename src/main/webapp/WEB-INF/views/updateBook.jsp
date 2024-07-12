<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Book</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="header">
        <h1>Digital Library</h1>
        <div class="nav">
            <a href="/login">Sign In</a>/<a href = "/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    <div class="bookManagement-container">
    <div class="container">
        <div class="sidebar">
            <h2>Book Management</h2>
            <ul>
                <li><a href="/bookManagement">Add Book</a></li>
                <li><a href="/updateBook">Update book</a></li>
                <li><a href="/deleteBook">Delete Book</a></li>
            
            </ul>
        </div>
        <form class="form-container" action="submitUpdateBook" method="post" modelattribute="updateBook">
            <h2> Update Book</h2>
            <h3>Enter Book Details</h3>
            <label for="bookid">Book ID</label>
            <input type="text" id="bookid" name="bookid" required>

            <label for="bookName">Book name</label>
            <input type="text" id="bookName" name="bookName" required>
      
            <label path="author" for="author">Author name</label>
            <input path="author" type="text" id="author" name="author" required>

            <label for="subject">Book subject</label>
            <input type="text" id="subject" name="subject" required>

            <label for="info">Book info</label>
            <input type="text" id="info" name="info" required>
        </br>
            <button type="submit">Update</button>
            
        </div>
    </div>
</div>
</body>
</html>
