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
        <button class="back-button" onclick="window.location.href='/studentHome'">
            &#8592;
        </button>
        <h1>Digital Library</h1>
        
        <div class="nav">
            <a href="/studentHome">Home</a>
            <a href="/login">Sign In</a>/<a href = "/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>

    <div class="search-container">
        <form class="search-bar" action="/services/search" method="get">
            <input type="text" id="query" name="query" value="${query}" placeholder="Search a book">
            <button type="submit">&#128269;</button>
        </form>
    </div>
   
    <c:if test="${not empty query}">
        <h2>Search results for: "${query}"</h2>
        <table border="1">
            <thead>
                <tr>
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

</body>
</html>
