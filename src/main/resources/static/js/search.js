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
                console.log(book);
            } else {
                displayResults(null);
            }
        }
    };
    xhr.send();
}

function displayResults(book) {
    console.log(book);
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
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.authors}</td>
                    <td>${book.publisher}</td>
                    <td>${book.publishedDate}</td>
                    <td>${book.description}</td>
                </tr>
            </tbody>
        </table>`;
        resultsContainer.innerHTML = table;
    } else {
        resultsContainer.innerHTML = "<p>No books found.</p>";
    }
}
