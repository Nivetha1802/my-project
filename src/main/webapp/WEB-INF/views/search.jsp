<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="header">
        <h1>Digital Library</h1>
    </div>

    <div class="search-container">
        <form class="search-bar" action="searchBooks" method="get">
            <input type="radio" name="searchType" value="title" ${searchType == 'title' ? 'checked' : ''}> Search by Title
            <input type="radio" name="searchType" value="isbn" ${searchType == 'isbn' ? 'checked' : ''}> Search by ISBN
            <input type="radio" name="searchType" value="author" ${searchType == 'author' ? 'checked' : ''}> Search by Author
            <input type="text" name="query" placeholder="Search a book" value="${query}">
            <button type="submit">&#128269;</button>
        </form>
    </div>

    <c:if test="${not empty books}">
        <table border="1">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Authors</th>
                    <th>ISBN</th>
                    <th>Publisher</th>
                    <th>Published Date</th>
                    <th>Description</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${books}">
                    <tr>
                        <td>${book.title}</td>
                        <td>${fn:join(book.authors, ', ')}</td>
                        <td>${book.isbn}</td>
                        <td>${book.publisher}</td>
                        <td>${book.publishedDate}</td>
                        <td>${book.description}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty books}">
        <p>No books found.</p>
    </c:if>
</body>
</html>
