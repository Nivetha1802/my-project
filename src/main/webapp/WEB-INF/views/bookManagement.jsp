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
        </div>
        <div class="form-container">
            <h2> Add Book</h2>
            <h3>Enter Book Details</h3>
            <form action="submitAddBook" method="post" modelAttribute="addBook">

            <label path="bookid" for="bookid">Book ID</label>
            <input path="bookid" type="text" id="bookid" name="bookid" required>

            <label path="bookName" for="bookName">Book name</label>
            <input path="bookName" type="text" id="bookName" name="bookName" required>

            <label path="author" for="author">Author name</label>
            <input path="author" type="text" id="author" name="author" required>

            <label path="subject" for="subject">Book subject</label>
            <input path="subject" type="text" id="subject" name="subject" required>

            <label path="info" for="info">Book info</label>
            <input path="info" type="text" id="info" name="info" required>
        <div>
            <button type="submit">Add</button>
        </div>  
        </div>
    </div>
</div>
</body>
</html>
