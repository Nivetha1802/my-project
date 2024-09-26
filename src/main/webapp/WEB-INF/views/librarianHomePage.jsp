<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Digital Library</title>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
                <script src="${pageContext.request.contextPath}/js/script.js"></script>
            </head>

            <body class="library-page">
                <div class="header">
                    <h1>Digital Library</h1>

                    <div class="nav">
                        <a href="/login">Sign In</a>/<a href="/signup">Signup</a>
                        <a href="/login">Logout</a>
                    </div>
                </div>
                <div class="search-container">
                    <form:form class="search-bar" onsubmit="searchBooks(event)" modelAttribute="search">
                        <div class="form-group">
                            <form:errors path="*" cssClass="error" element="div" />
                        </div>
                        <input type="text" id="query" placeholder="Search a book">
                        <button type="submit">&#128269;</button>
                    </form:form>
                </div>
                <div id="search-results"></div>
                <div class="button-container">
                    <button onclick="window.location.href='/books'">Manage Books</button>
                    <button onclick="window.location.href='/books/allBooks'">View All Books</button>
                </div>
                <div>
                    <c:if test="${not empty errors}">
                        <div>
                            <c:forEach var="error" items="${errors.allErrors}">
                                <p style="color: red;">${error.defaultMessage}</p>
                            </c:forEach>
                        </div>
                    </c:if>
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