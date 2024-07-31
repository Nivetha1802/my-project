<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Lending Details</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lendstyle.css">
</head>
<body class="library-page">
    <div class="header">
        <h1>Digital Library</h1>
        <a href="/studentHomePage">Home</a>
        <div class="nav">
            <a href="/login">Sign In</a>/<a href="/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    <button class="back-button" onclick="history.back()">
        &#8592;
    </button>
    <div class="lend_container">
        <h1>Lent Books Details</h1>
        <p>Total Books: ${fn:length(selectedBooks)}</p>
        <c:if test="${empty selectedBooks}">
            <p>No books selected for lending.</p>
        </c:if>
        <table id="lendDetailsTable">
            <thead>
                <tr>
                    <th>Book Id</th>
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
                        <td class="lending-date"></td>
                        <td class="return-date"></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <button type="button" onclick="submitLendDetails()">Lend Selected Books</button>
    </div>
    <form id="lendDetForm" action="submitlenddetails" method="post">
        <input type="hidden" id="selectedBooks" name="selectedBooks" value="">
    </form>
    <c:if test="${not empty successMessage}">
            showSuccessMessage('${successMessage}');
    </c:if>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            populateDates();
        });

        function populateDates() {
            const lendingDate = new Date();
            const returnDate = new Date();
            returnDate.setDate(lendingDate.getDate() + 14);

            const lendingDateStr = lendingDate.toISOString().split('T')[0];
            const returnDateStr = returnDate.toISOString().split('T')[0];

            const rows = document.querySelectorAll('#lendDetailsTable tbody tr');

            rows.forEach(row => {
                row.querySelector('.lending-date').textContent = lendingDateStr;
                row.querySelector('.return-date').textContent = returnDateStr;
            });
        }

        function submitLendDetails() {
            const selectedBooks = [];
            const rows = document.querySelectorAll('#lendDetailsTable tbody tr');

            rows.forEach(row => {
                const book = {
                    bookid: row.querySelector('td:nth-child(1)').textContent,
                    bookname: row.querySelector('td:nth-child(2)').textContent,
                    author: row.querySelector('td:nth-child(3)').textContent,
                    subject: row.querySelector('td:nth-child(4)').textContent,
                    lendDate: row.querySelector('.lending-date').textContent,
                    returnDate: row.querySelector('.return-date').textContent
                };
                selectedBooks.push(book);
            });

            const selectedBooksInput = document.getElementById('selectedBooks');
            selectedBooksInput.value = JSON.stringify(selectedBooks);
            console.log(selectedBooksInput.value);
            document.getElementById('lendDetForm').submit();
        }
        function showSuccessMessage(message) {
            toastr.success(message, 'Success', {
                closeButton: true,
                progressBar: true,
                positionClass: 'toast-top-right',
                showDuration: '300',
                hideDuration: '1000',
                timeOut: '5000',
                extendedTimeOut: '1000'
            });
        }
    </script>
</body>
</html>
