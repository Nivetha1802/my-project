<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>    
    <meta charset="UTF-8">
    <title>Book Returning</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    
</head>
<body class="library-page">
   
    <div class="header">
        <button class="back-button" onclick="window.location.href='/studentHomePage'">
            &#8592;
        </button>
        <h1>Digital Library</h1>
        
        <div class="nav">
            <a href="/studentHomePage">Home</a>
            <a href="/login">Sign In</a>/<a href = "/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    <div class="lend_container">
        <h1>Return Books</h1>
        <p>Total Books: ${fn:length(lendBooks)}</p>
        <c:if test="${empty lendBooks}">
            <p>No books to return.</p>
        </c:if>
        <c:if test="${not empty lendBooks}">
        <table id="returnTable">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Author</th>
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
                            <td>${book.title}</td>
                            <td>${book.authors}</td>
                            <td id="date_of_lending_${book.bookid}">${book.lendDate}</td>
                            <td id="due_date_${book.bookid}">${book.returnDate}</td>
                            <td class="actual_return_date"></td>
                            <td>
                                <button type="button" onclick="toggleBookForReturn(this, '${book.bookid}', '${book.id}')">Return</button>
                            </td>
                            <td>${book.fine}</td>
                            <td>
                                <button type="button">Pay</button>
                            </td>
                            </tr>
                    </c:forEach>
                </tbody>
            </table>
            <button type="submit" onclick="submitReturnBooks()">Return Selected Books</button>
        </c:if>
    </div>
    <form id="returnBooksForm" action="submitReturnBooks" method="post">
        <input type="hidden" id="selectedBooks" name="selectedBooks" value="" />
    </form>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
   
</body>

</html>
