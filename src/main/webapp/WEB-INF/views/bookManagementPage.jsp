<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Book Management</title>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
            </head>

            <body class="library-page">

                <div class="header">
                    <button class="back-button" onclick="window.location.href='/librarianHome'">
                        &#8592;
                    </button>
                    <h1>Digital Library</h1>

                    <div class="nav">
                        <a href="/librarianHome">Home</a>
                        <a href="/login">Sign In</a>/<a href="/signup">Signup</a>
                        <a href="/login">Logout</a>
                    </div>
                </div>
                <div class="bookManagement-container">
                    <div class="container">
                        <div class="sidebar">
                            <h2>Book Management</h2>
                            <ul>
                                <li><a href="/books/">Add Book</a></li>
                                <li><a href="/books/update">Update book</a></li>
                                <li><a href="/books/delete">Delete Book</a></li>
                            </ul>
                        </div>
                        <div class="form-container">
                            <h2> Add Book</h2>
                            <h3>Enter Book Details</h3>
                            <form:form action="/books/add" method="post" modelAttribute="addBook">
                                <div class="form-group">
                                    <form:errors path="*" cssClass="error" element="div" />
                                </div>
                                <label path="bookid" for="bookid">Book ID</label>
                                <input path="bookid" type="text" id="bookid" name="bookid" required>

                                <label path="bookname" for="bookname">Book name</label>
                                <input path="bookname" type="text" id="bookname" name="bookname" required>

                                <label path="author" for="author">Author name</label>
                                <input path="author" type="text" id="author" name="author" required>

                                <label path="subject" for="subject">Book subject</label>
                                <input path="subject" type="text" id="subject" name="subject" required>

                                <label path="info" for="info">Book info</label>
                                <input path="info" type="text" id="info" name="info" required>

                                <label path="Bookcount" for="Bookcount">Book Count</label>
                                <input path="Bookcount" type="text" id="Bookcount" name="Bookcount" required>
                                <div>
                                    <button type="submit">Add</button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </body>

            </html>