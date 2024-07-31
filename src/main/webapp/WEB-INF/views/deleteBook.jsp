<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Book</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lendstyle.css">
</head>
<body class="library-page">
    <div class="header">
        <h1>Digital Library</h1>
        <a href="/librarianHomePage">Home</a>
        <div class="nav">
            <a href="/login">Sign In</a>/<a href = "/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    <button class="back-button" onclick="history.back()">
        &#8592;
    </button>
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
        <div class="form-container">
        <h2> Delete Book</h2>
        <h3>Enter Book Details</h3>
        <form action="submitDeleteBook" method="post" modelAttribute="deleteBook">
            
            <label for="bookid">Book ID</label>
            <input type="text" id="bookid" name="bookid" required>
            <div>
            <button type="submit">Delete</button>
            </div>
        </div>    
        </div>
    </div>
</div>
</body>
</html>
