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
            <a href="/login">Sign In</a>/<a href="/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    <div class="search-container">
        <form class="search-bar" action="searchResults.jsp" method="get">
            <input type="text" name="query" placeholder="Search a book">
            <button type="submit">&#128269;</button>
        </form>
    </div>
    <div class="button-container">
        <button onclick="window.location.href='/bookManagement'">Manage Books</button>
        <!-- <button onclick="window.location.href='/fineDetails'">Fine Details</button> -->
        <button onclick="window.location.href='/allBooks'">View All Books</button>
    </div>
</body>
</html>

