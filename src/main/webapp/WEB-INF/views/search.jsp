<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="library-page">
    <div class="header">
        <button class="back-button" onclick="history.back()">
            &#8592;
        </button>
        <h1>Digital Library</h1>
        
        <div class="nav">
            <a href="/studentHomePage">Home</a>
            <a href="/login">Sign In</a>/<a href = "/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>

    <div class="search-container">
        <form class="search-bar" action="search" method="post" modelAttribute="search">
            <input type="text" name="query" placeholder="Search a book" value="${search.query}">
            <button type="submit">&#128269;</button>
        </form>
    </div>
   
    <c:if test="${not empty search.query}">
        <h2>Search results for: "${search.query}"</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
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
                    <tr><td>${book.id}</td>
                        <td>${book.title}</td>
                        <td>${book.authors}</td>
                        <td>${book.publisher}</td>
                        <td>${book.publishedDate}</td>
                        <td>${book.description}</td>
                        <td><img src="${book.thumbnail}" alt="Thumbnail" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty search.query}">
        <p>No books found.</p>
    </c:if>
</body>
</html>
