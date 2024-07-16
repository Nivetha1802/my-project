<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>	
	<meta charset="UTF-8">
	<title>Book Returning</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lendstyle.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Renewscript.js"></script>
</head>
<body class="library-page">
    <div class="header">
        <h1>Digital Library</h1>
        <div class="nav">
            <a href="/login">Sign In</a>/<a href="/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    <h1>Lend Books</h1>
    <p>Total Books: ${fn:length(lendbooks)}</p>
    <c:if test="${empty lendbooks}">
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
				<th>Action</th>
                <th>Fine</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${lendbooks}">
                <tr>
					<td>${book.bookid}</td>
                    <td>${book.bookname}</td>
					<td>${book.author}</td>
					<td>${book.subject}</td>
					<td>${book.info}</td>
                    <td>
						<button type="button" onclick="toggleBook(this, '${lendbooks.bookid}', '${lendbooks.bookname}', '${lendbooks.author}','${lendbooks.subject}','${lendbooks.info}')">Return</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	<button type="submit" onclick="submitReturnBooks()">Return Selected Books</button>
</body>
</html>
