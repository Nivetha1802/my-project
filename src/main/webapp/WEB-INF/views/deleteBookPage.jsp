<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Delete Book</title>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
                <script src="${pageContext.request.contextPath}/js/script.js"></script>
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
                            <h2> Delete Book</h2>
                            <h3>Enter Book Details</h3>
                            <form:form action="/books/delete" method="post" modelAttribute="deleteBook">
                                <div class="form-group">
                                    <form:errors path="*" cssClass="error" element="div" />
                                </div>
                                <label path="id" for="id">Book ID</label>
                                <input path="id" type="text" id="id" name="id" required>
                                <div>
                                    <button type="submit">Delete</button>
                                </div>
                            </form:form>

                        </div>
                    </div>
                </div>
                <div>
                    <c:if test="${not empty error}">
                        <script>
                            showMessage("${error}", 'error');
                        </script>
                    </c:if>
                    <c:if test="${not empty message}">
                        <script>
                            showMessage("${message}", 'success');
                        </script>
                    </c:if>
            </div>
            </body>

            </html>