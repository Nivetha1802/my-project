<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Book Search Results</title>
</head>
<body>
    <h1>Book Search Results</h1>
    <form action="${pageContext.request.contextPath}/search" method="get">
        <fieldset>
            <legend>Filter Options:</legend>
            <label>
                <input type="checkbox" name="filter" value="bookName">
                Book Name
            </label>
            <label>
                <input type="checkbox" name="filter" value="subject">
                Subject
            </label>
            <label>
                <input type="checkbox" name="filter" value="author">
                Author
            </label>
        </fieldset>
        <input type="text" id="searchValue" name="searchValue" required>
        <button type="submit">Search</button>
    </form>

    <table border="1">
        <thead>
            <tr>
                <th>Book Name</th>
                <th>Author</th>
                <th>Subject</th>
                <!-- Add more columns as needed -->
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>${book.bookName}</td>
                    <td>${book.author}</td>
                    <td>${book.subject}</td>
                    <!-- Display more book details as needed -->
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
