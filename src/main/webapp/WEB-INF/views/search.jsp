<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Search</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lendstyle.css">
</head>
<body class="library-page">
    <div class="header">
        <h1>Digital Library</h1>
        <a href="/studentHomePage">Home</a>
        <div class="nav">
            <a href="/login">Signin</a>/<a href="/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    <button class="back-button" onclick="history.back()">
        &#8592;
    </button>
    <div class="search-container">
        <form class="search-bar" action="search" method="get">
            <input type="radio" name="searchType" value="id" checked> Search by ID
            <input type="radio" name="searchType" value="title"> Search by Title
            <input type="radio" name="searchType" value="author"> Search by Author
            <input path="query" type="text" name="query" placeholder="Search a book" value="${search.query}">
            <button type="submit">&#128269;</button>
        </form>
    </div>


    <table border="1">
        <thead>
            <tr>
                <th>Book Id</th>
                <th>Title</th>
                <th>Authors</th>
                <th>Publisher</th>
                <th>Published Date</th>
                <th>Description</th>
                <th>Thumbnail</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${fn:join(book.authors, ', ')}</td>
                    <td>${book.publisher}</td>
                    <td>${book.publishedDate}</td>
                    <td>${book.description}</td>
                    <td><img src="${book.thumbnail}" alt="Thumbnail" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div>
        <c:if test="${not empty errors}">
            <div>
                <c:forEach var="error" items="${errors.allErrors}">
                    <p style="color: red;">${error.defaultMessage}</p>
                </c:forEach>
            </div>
        </c:if>
    </div>
</body>
</html>
