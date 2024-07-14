<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>	
	<meta charset="UTF-8">
	<title>Book Lending</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Lend_table.js"></script>
</head>
<body>
    <h1>Lend Books</h1>
    <p>Total Books: ${fn:length(books)}</p>
    <c:if test="${empty books}">
        <p>No books available for lending.</p>
    </c:if>
    <table>
        <thead>
            <tr>
				<th>No.</th>
                <th>Title</th>
                <th>Author</th>
				<th>Subject</th>
				<th>Info</th>
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
                    <td>
						<form action="${pageContext.request.contextPath}/submitLendtable}" method="post">
						<button type="button" onclick="toggleBook(this, ${book.bookid}, '${book.bookname}', '${book.author}','${book.subject}','${book.info}')">Add</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	<button type="button" onclick="submitLendBooks()">Lend Selected Books</button>
</body>
</html>
