<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Returning</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lendstyle.css">
</head>
<body>
    <div class="header">
        <h1>Digital Library</h1>
        <div class="nav">
            <a href="/login">Signin</a>/<a href="/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    <div class="lend_container">
    <h1>Book Search Results</h1>
    <form action="${pageContext.request.contextPath}/search" method="post">
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
        <div class="search-container">
                <input type="text" name="query" placeholder="Search a book">
                <button type="submit">&#128269;</button>
        </div>
    </form>

    <table border="1">
        <thead>
            <tr>
				<th>No.</th>
                <th>Title</th>
                <th>Author</th>
				<th>Subject</th>
				<th>Info</th>
                <th>Books count</th>
				<th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>${book.bookid}</td>
                    <td>${book.bookname}</td>
					<td>${book.author}</td>
					<td>${book.subject}</td>
                    <td>${book.info}</td>
                    <td>${book.bookcount}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>
