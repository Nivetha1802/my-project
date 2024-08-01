<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/books.js"></script>
</head>
<body>
    <div class="header">
        <h1>Book List</h1>
    </div>
    <div class="pagination-controls">
        <button onclick="loadBooks(1)">First</button>
        <button onclick="loadBooks(currentPage - 1)">Previous</button>
        <button onclick="loadBooks(currentPage + 1)">Next</button>
        <button onclick="loadBooks(totalPages)">Last</button>
    </div>
    <div id="book-results"></div>
    <script>
        let currentPage = 1;
        let totalPages = 1;

        function loadBooks(page) {
            if (page < 1 || page > totalPages) return;
            currentPage = page;

            const xhr = new XMLHttpRequest();
            xhr.open("GET", `/books?page=${currentPage}&pageSize=10`, true);
            xhr.setRequestHeader("Content-Type", "application/json");

            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        const response = JSON.parse(xhr.responseText);
                        displayBooks(response);
                    } else {
                        document.getElementById("book-results").innerHTML = "<p>No books found.</p>";
                    }
                }
            };
            xhr.send();
        }

        function displayBooks(books) {
            const resultsContainer = document.getElementById("book-results");
            resultsContainer.innerHTML = '';

            if (books && books.length > 0) {
                let table = `<table>
                    <thead>
                        <tr>
                            <th>Book Id</th>
                            <th>Title</th>
                            <th>Authors</th>
                            <th>Publisher</th>
                            <th>Published Date</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>`;
                books.forEach(book => {
                    table += `<tr>
                        <td>${book.id}</td>
                        <td>${book.title}</td>
                        <td>${book.authors.join(', ')}</td>
                        <td>${book.publisher}</td>
                        <td>${book.publishedDate}</td>
                        <td>${book.description}</td>
                    </tr>`;
                });
                table += `</tbody></table>`;
                resultsContainer.innerHTML = table;
            } else {
                resultsContainer.innerHTML = "<p>No books found.</p>";
            }
        }

        window.onload = function() {
            loadBooks(currentPage);
        };
    </script>
</body>
</html>
