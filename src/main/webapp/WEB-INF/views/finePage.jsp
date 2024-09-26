<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Fine Details</title>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
        </head>

        <body class="library-page">
           
            <div class="header">
                <button class="back-button" onclick="window.location.href='/studentHomePage'">
                    &#8592;
                </button>
                <h1>Digital Library</h1>
                
                <div class="nav">
                    <a href="/studentHomePage">Home</a>
                    <a href="/login">Sign In</a>/<a href = "/signup">Signup</a>
                    <a href="/login">Logout</a>
                </div>
            </div>
            <div class="lend_container">
                <h1>Fine Details</h1>
                <p>Total Books: ${fn:length(books)}</p>
                <p>User ID: ${sessionScope.userId}</p>
                <p>Username: ${user}</p>
                
                <c:if test="${empty books}">
                    <p>No Fine</p>
                </c:if>
                <c:if test="${not empty books}">
                <table>
                    <thead>
                        <tr>
                            <th>Book Id</th>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Fine</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${books}">
                            <tr>
                                <td>${book.bookid}</td>
                                <td>${book.title}</td>
                                <td>${book.authors}</td>
                                <td>${book.fine}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            </div>
    </body>

        </html>