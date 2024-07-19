<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Digital Library</title>
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
    <div class="search-container">
        <form class="search-bar" action="studentHomePage" method="post" modelAttribute="search">
            <input path="query" type="text" name="query" placeholder="Search a book" value="${search.query}">
            <button type="submit">&#128269;</button>
        </form>
    </div>
    
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
                        <td>${sessionScope.bookd}</td>
                        <td>${sessionScope.bookname}</td>
                        <td>${sessionScope.author}</td>
                        <td>${sessionScope.subject}</td>
                        <td>${sessionScope.info}</td>
                        <td>${sessionScope.bookcount}</td>
                    </tr>
            </tbody>
        </table>
    </div>
    
</body>
</html>
