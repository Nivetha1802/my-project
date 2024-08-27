<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Book Lending Details</title>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body class="library-page">

            <div class="header">
                <button class="back-button" onclick="history.back()">
                    &#8592;
                </button>
                <h1>Digital Library</h1>

                <div class="nav">
                    <a href="/studentHomePage">Home</a>
                    <a href="/login">Sign In</a>/<a href="/signup">Signup</a>
                    <a href="/login">Logout</a>
                </div>
            </div>
            <div class="lend_container">
                <h1>Lent Books Details</h1>
                <p>Total Books: ${fn:length(selectedBooks)}</p>
                <c:if test="${empty selectedBooks}">
                    <p>No books selected for lending.</p>
                </c:if>
                <c:if test="${not empty selectedBooks}">
                    <table id="lendDetailsTable">
                        <thead>
                            <tr>
                                <th>Book Id</th>
                                <th>Title</th>
                                <th>Authors</th>
                                <th>Publisher</th>
                                <th>Lend Date</th>
                                <th>Return Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="book" items="${selectedBooks}">
                                <tr>
                                    <td>${book.id}</td>
                                    <td>${book.title}</td>
                                    <td>${book.authors}</td>
                                    <td>${book.publisher}</td>
                                    <td class="lending-date"></td>
                                    <td class="return-date"></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <button type="submit" onclick="submitLendDetails()">Lend Selected Books</button>
                </c:if>
            </div>
            <form id="lendDetForm" action="submitlenddetails" method="post">
                <input type="hidden" id="selectedBooks" name="selectedBooks" value="">
            </form>
            <script src="${pageContext.request.contextPath}/js/script.js"></script>

        </body>

        </html>