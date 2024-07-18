<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Fine Details</title>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lendstyle.css">
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/Lend_table.js"></script>
        </head>

        <body class="library-page">
            <div class="header">
                <h1>Digital Library</h1>
                <div class="nav">
                    <a href="/login">Sign In</a>/<a href="/signup">Signup</a>
                    <a href="/login">Logout</a>
                </div>
            </div>
            <div class="lend_container">
                <h1>Fine Details</h1>
                <p>Total Books: ${fn:length(books)}</p>
                <c:if test="${not empty user}">
                <p>User ID: ${sessionScope.userId}</p>
                <p>Username: ${user}</p>
                </c:if>
                <c:if test="${empty books}">
                    <p>No Fine</p>
                </c:if>
                <table>
                    <thead>
                        <tr>
                            <th>Book Id</th>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Subject</th>
                            <th>Fine</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${books}">
                            <tr>
                                <td>${book.bookid}</td>
                                <td>${book.bookname}</td>
                                <td>${book.author}</td>
                                <td>${book.subject}</td>
                                <td>${book.fine}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
    </body>

        </html>