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
    <h2> Menu </h2>  
    <div class="button-container">
        <button onclick="window.location.href='/lendtable'">Lend books</button>
        <button onclick="window.location.href='/returntable'">Return books</button>
        <button onclick="window.location.href='/renewtable'">Renew books</button>
        <button onclick="window.location.href='/fineDetails'">Fine Details</button>
        <button onclick="window.location.href='/allBooks'">View All Books</button>
        <button onclick="window.location.href='/search'">Search Books</button>
        
    </div>

</body>

</html>