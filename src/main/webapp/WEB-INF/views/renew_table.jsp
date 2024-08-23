<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Renew</title>
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
        <h1>Renew Books</h1>
        <p>Total Books: ${fn:length(lendBooks)}</p>
        <c:if test="${empty lendBooks}">
            <p>No books to renew.</p>
        </c:if>
        <c:if test="${not empty lendBooks}">
        <table id="renewTable">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Lend date</th>
                    <th>Due date</th>
                    <th>Action</th>
                    <th>Extended Return Date</th>
                    <th>Fine</th>
                    <th>Payment</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${lendBooks}">
                    <tr>
                        <td>${book.title}</td>
                        <td>${book.authors}</td>
                        <td>${book.lendDate}</td>
                        <td>${book.returnDate}</td>
                        <td>
                            <button type="button" onclick="toggleBook(this, '${book.id}')">
                                Renew
                            </button>
                        </td>
                        <td>${book.calculateExtendedReturnDate()}</td>
                        <td>${book.fine}</td>
                        <td>
                            <button type="button">Pay</button>
                        </td>                    
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <button type="submit" onclick="submitRenewBooks()">
            Renew Selected Books
        </button>
    </c:if>
    </div>
    <form id="RenewForm" action="submitRenewtable" method="post">
        <input type="hidden" id="selectedBooks" name="selectedBooks" value="" />
    </form>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Renewscript.js"></script>
</body>
</html>
