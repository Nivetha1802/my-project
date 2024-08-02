<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Book Lending</title>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
            <script src="${pageContext.request.contextPath}/js/Lend_table.js"></script>
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

                <h1>Lend Books</h1>
                <div class="search-container">
                    <form class="search-bar" action="lendsearch" method="post" modelAttribute="search">
                        <input type="text" name="query" placeholder="Search a book" value="${search.query}">
                        <button type="submit">&#128269;</button>
                    </form>
                </div>

                <c:if test="${not empty search.query}">
                    <h2>Search results for: "${search.query}"</h2>
                    <div>
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
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
                                        <td>${book.id}</td>
                                        <td>${book.title}</td>
                                        <td><c:forEach var="author" items="${book.authors}">${author}<br/></c:forEach></td>
                                        <td>${book.publisher}</td>
                                        <td>${book.publishedDate}</td>
                                        <td><img src="${book.thumbnail}" alt="Thumbnail" /></td>
                                        <td>
                                        <button type="button"
                                            onclick="toggleBook(this, '${book.id}', '${book.title}', '${book.authors}', '${book.publisher}', '${book.publishedDate}')">Add</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </div>
                    <button type="submit" onclick="submitLendBooks()">Lend Selected Books</button>
            </div>
            </c:if>
            <c:if test="${empty search}">
                <p>No books found.</p>
            </c:if>
            <form id="lendForm" action="${pageContext.request.contextPath}/submitlendtable" method="post">
                <input type="hidden" id="selectedBooks" name="selectedBooks" value="">
            </form>
        </body>

        </html>