<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
	<title>Book Lending Details</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lendstyle.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Lendingscript.js"></script>
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
    <h1>Lent Books Details</h1>
    <p>Total Books: ${fn:length(selectedBooks)}</p>
    <c:if test="${empty selectedBooks}">
        <p>No books selected for lending.</p>
    </c:if>
    <table>
        <thead>
            <tr>
                <th>No.</th>
                <th>Book name</th>
                <th>Author</th>
                <th>Subject</th>
                <th>Lending Date</th>
                <th>Return Date</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${selectedBooks}">
                <tr>
                    <td>${book.bookid}</td>
                    <td>${book.bookname}</td>
                    <td>${book.author}</td>
                    <td>${book.subject}</td>
                    <td></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
