<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Book Lending</title>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
            <script src="${pageContext.request.contextPath}/js/script.js"></script>
        </head>

        <body class="library-page">

            <div class="header">
                <button class="back-button" onclick="window.location.href='/studentHomePage'">
                    &#8592;
                </button>
                <h1>Digital Library</h1>

                <div class="nav">
                    <a href="/studentHome">Home</a>
                    <a href="/login">Sign In</a>/<a href="/signup">Signup</a>
                    <a href="/login">Logout</a>
                </div>
            </div>
            <div class="lend_container">

                <h1>Lend Books</h1>
                <div class="search-container">
                    <form class="search-bar" action="/services/lend" method="get">
                        <input type="text" id="query" name="query" value="${query}" placeholder="Search a book">
                        <button type="submit">&#128269;</button>
                    </form>
                </div>

                <c:if test="${not empty books}">
                    <h2>Search results for: "${query}"</h2>
                    <div>
                        <table>
                            <thead>
                                <tr>
                                    <th>Title</th>
                                    <th>Authors</th>
                                    <th>Publisher</th>
                                    <th>Published Date</th>
                                    <th>Thumbnail</th>
                                    <th>action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="book" items="${books}">
                                    <tr>
                                        <td>${book.title}</td>
                                        <td>${book.authors}</td>
                                        <td>${book.publisher}</td>
                                        <td>${book.publishedDate}</td>
                                        <td><img src="${book.thumbnail}" alt="Thumbnail" /></td>
                                        <td>
                                        <button type="button"
                                            onclick="toggleBookForAdd(this, '${book.id}', '${book.title}', '${book.authors}', '${book.publisher}', '${book.publishedDate}')">Add</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </div>
                    <button type="submit" onclick="submitLendBooks()">Lend Selected Books</button>
            </div>
            </c:if>
            
            <form id="lendForm" action="${pageContext.request.contextPath}/services/lend" method="post">
                <input type="hidden" id="selectedBooks" name="selectedBooks" value="">
            </form>
        </body>

        </html>