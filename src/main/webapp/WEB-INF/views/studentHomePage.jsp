<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Digital Library</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/signup.js"></script>

</head>

<body class="library-page" >
   
    <div class="header">
        
        <h1>Digital Library</h1>
        
        <div class="nav">
            <a href="/login">Sign In</a>/<a href = "/signup">Signup</a>
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

    <div>
        <c:if  test="${empty message}">

        </c:if>
        <c:if  test="${empty error}">
            
        </c:if>
        <c:if test="${not empty error}">
            <script>
                showMessage("${error}", 'error');
            </script>
        </c:if>
        <c:if test="${not empty message}">
            <script>
                showMessage("${message}", 'success');
            </script>
        </c:if>
</div>
</body>

</html>