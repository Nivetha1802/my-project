<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>	
	<meta charset="UTF-8">
	<title>Book Lending</title>
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
    <h1>Lend Books</h1>
    <p>Total Books: ${fn:length(books)}</p>
    <c:if test="${empty books}">
        <p>No books available for lending.</p>
    </c:if>
    <table>
        <thead>
            <tr>
				<th>Book Id</th>
                <th>Title</th>
                <th>Author</th>
				<th>Subject</th>
				<th>Info</th>
                <th>Books count</th>
				<th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${books}">
                <tr>
					<td>${book.bookid}</td>
                    <td>${book.bookname}</td>
					<td>${book.author}</td>
					<td>${book.subject}</td>
                    <td>${book.info}</td>
                    <td>${book.bookcount}</td>
                    <td>
						<!-- <form action="${pageContext.request.contextPath}/submitlendtable" method="post"> -->
                            <c:choose>
                                <c:when test="${book.bookcount == 0}">
                                    <button type="button" disabled>Out of stock</button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" onclick="toggleBook(this, '${book.bookid}', '${book.bookname}', '${book.author}', '${book.subject}', '${book.bookcount}', '${book.info}')">Add</button>
                                </c:otherwise>
                            </c:choose>
                        <!-- </form> -->
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	<button type="button" onclick="submitLendBooks()">Lend Selected Books</button>
    </div>
    <form id="lendForm" action="submitlendtable" method="post">
        <input type="hidden" id="selectedBooks" name="selectedBooks" value="">
    </form>
    
</body>
</html>
