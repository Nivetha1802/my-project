<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Digital Library</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/search.js"></script>
</head>
<body class="library-page">
    
    <div class="header">
        <h1>Digital Library</h1>
        
        <div class="nav">
            <a href="/login">Sign In</a>/<a href = "/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    <div class="search-container">
        <form class="search-bar" onsubmit="searchBooks(event)">
            <input type="text" id="query" placeholder="Search a book">
            <button type="submit">&#128269;</button>
        </form>
    </div>
    <div id="search-results"></div>
    <div class="button-container">
        <button onclick="window.location.href='/bookManagement'">Manage Books</button>
        <button onclick="window.location.href='/allBooks'">View All Books</button>
    </div>
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
