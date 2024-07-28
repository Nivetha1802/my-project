function searchBooks(event) {
    event.preventDefault();

    const query = document.getElementById("query").value;
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `/searchBooks?query=${query}`, true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                displayResults(response);
            } else {
                displayResults(null); // handle case when no book is found
            }
        }
    };
    xhr.send();
}

function displayResults(book) {
    const resultsContainer = document.getElementById("search-results");
    resultsContainer.innerHTML = '';

    if (book) {
        let table = `<table>
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
                <tr>
                    <td>${book.bookid}</td>
                    <td>${book.bookname}</td>
                    <td>${book.author}</td>
                    <td>${book.subject}</td>
                    <td>${book.info}</td>
                    <td>${book.bookcount}</td>
                </tr>
            </tbody>
        </table>`;
        resultsContainer.innerHTML = table;
    } else {
        resultsContainer.innerHTML = "<p>No books found.</p>";
    }
}
