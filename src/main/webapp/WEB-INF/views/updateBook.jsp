<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Book</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/updateBook.js"></script>
</head>
<body class="library-page">
    
    <div class="header">
        <button class="back-button" onclick="window.location.href='/librarianHomePage'">
            &#8592;
        </button>
        <h1>Digital Library</h1>
        
        <div class="nav">
            <a href="/librarianHomePage">Home</a>
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
                    <li><a href="/updateBook">Update Book</a></li>
                    <li><a href="/deleteBook">Delete Book</a></li>
                </ul>
            </div>
            <form class="form-container" action="submitUpdateBook" method="post" modelattribute="updateBook">
                <h2>Update Book</h2>
                <h3>Enter Book Details</h3>
                <label for="bookid">Book ID</label>
                <input type="text" id="bookid" name="bookid" onblur="fetchBookDetails()" required>

                <label for="bookname">Book Name</label>
                <input type="text" id="bookname" name="bookname" required>

                <label for="author">Author Name</label>
                <input type="text" id="author" name="author" required>

                <label for="subject">Book Subject</label>
                <input type="text" id="subject" name="subject" required>

                <label for="info">Book Info</label>
                <input type="text" id="info" name="info" required>

                <label for="Bookcount">Book Count</label>
                <input type="text" id="Bookcount" name="Bookcount" required>
                <br>
                <button type="submit">Update</button>
            </form>
        </div>
    </div>
</body>
</html>