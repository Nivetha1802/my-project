<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>	
	<meta charset="UTF-8">
	<title>All Books</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
    </head>
<body class="library-page">
   
    <div class="header">
        <button class="back-button" onclick="window.location.href='/librarianHomePage'">
            &#8592;
        </button>
        <h1>Digital Library</h1>
        
        <div class="nav">
            <a href="/librarianHomePage">Home</a>
            <a href="/login">Sign In</a>/<a href = "/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    
    <div class="lend_container">
    <h1>All Books</h1>
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
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${books}">
                <tr>
					<td>${book.id}</td>
                    <td>${book.bookname}</td>
					<td>${book.author}</td>
					<td>${book.subject}</td>
                    <td>${book.info}</td>
                    <td>${book.bookcount}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
