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
                <h2> Menu </h2>
                <div class="button-container">
                    <button onclick="window.location.href='/services/lend'">Lend books</button>
                    <button onclick="window.location.href='/services/return'">Return books</button>
                    <button onclick="window.location.href='/services/renew'">Renew books</button>
                    <button onclick="window.location.href='/services/fine'">Fine Details</button>
                    <button onclick="window.location.href='/services/search'">Search Books</button>

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