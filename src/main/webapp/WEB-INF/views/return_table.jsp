<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>    
    <meta charset="UTF-8">
    <title>Book Returning</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lendstyle.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Returnscript.js"></script>
    
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
        <h1>Return Books</h1>
        <p>Total Books: ${fn:length(lendBooks)}</p>
        <c:if test="${empty lendBooks}">
            <p>No books to return.</p>
        </c:if>
        <table id="returnTable">
                <thead>
                    <tr>
                        <th>Book Id</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Subject</th>
                        <th>Info</th>
                        <th>Lend Date</th>
                        <th>Due Date</th>
                        <th>Actual Return Date</th>
                        <th>Action</th>
                        <th>Fine</th>
                        <th>Payment</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${lendBooks}">
                        <tr>
                            <td>${book.bookid}</td>
                            <td>${book.bookname}</td>
                            <td>${book.author}</td>
                            <td>${book.subject}</td>
                            <td>${book.info}</td>
                            <td id="date_of_lending_${book.bookid}">${book.lendDate}</td>
                            <td id="due_date_${book.bookid}">${book.returnDate}</td>
                            <td class="actual_return_date"></td>
                            <td>
                                <button type="button" onclick="toggleBook(this, '${book.bookid}', '${book.lendId}')">Return</button>
                            </td>
                            <td>${book.fine}</td>
                            <td>
                                <button type="button">Pay</button>
                            </td>
                            </tr>
                    </c:forEach>
                </tbody>
            </table>
            <button type="button" onclick="submitReturnBooks()">Return Selected Books</button>
    </div>
    <form id="returnBooksForm" action="submitReturnBooks" method="post">
        <input type="hidden" id="selectedBooks" name="selectedBooks" value="" />
    </form>    
</body>
<script>
    document.addEventListener('DOMContentLoaded', function() {
    populateDates();
});

function populateDates() {
    const returnDate = new Date();
    const returnDatestr = returnDate.toISOString().split('T')[0];
    const rows = document.querySelectorAll('#returnTable tbody tr');
    console.log("loaded dates")
    rows.forEach(row => {
        row.querySelector('.actual_return_date').textContent = returnDatestr;
    });
}
</script>
</html>
