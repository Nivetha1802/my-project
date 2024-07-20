<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Digital Library</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <div class="header">
        <h1>Digital Library</h1>
        <div class="nav">
            <a href="/login">Signin</a>/<a href="/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    <div class="search-container">
        <form class="search-bar" action="studentHomePage" method="post" modelAttribute="search">
            <input path="query" type="text" name="query" placeholder="Search a book" value="${search.query}">
            <button type="submit">&#128269;</button>
        </form>
    </div>
    <c:if test="${not empty books}">
    <div class="lendcontainer">
        <table>
            <thead>
                <tr>
                    <th>Book Id</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Subject</th>
                    <th>Info</th>
                    <th>Books count</th>
                </tr>
            </thead>
            <tbody>

                <tr>
                    <td>${books.bookid}</td>
                    <td>${books.bookname}</td>
                    <td>${books.author}</td>
                    <td>${books.subject}</td>
                    <td>${books.info}</td>
                    <td>${books.bookcount}</td>
                </tr>
            </tbody>
        </table>
    </div>
</c:if>
<c:if test="${empty books}">
    <p>can search books here</p>
</c:if>
    <div class="button-container">
        <button onclick="window.location.href='/lendtable'">Lend a book</button>
        <button onclick="window.location.href='/returntable'">Return a book</button>
        <button onclick="window.location.href='/renewtable'">Renew a book</button>
        <button onclick="window.location.href='/fineDetails'">Fine Details</button>
        <button onclick="window.location.href='/allBooks'">View All Books</button>
    </div>
</body>

</html>