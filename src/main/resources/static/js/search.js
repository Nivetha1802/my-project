function searchBooks(event) {
    event.preventDefault();

    const query = document.getElementById("query").value;
    console.log(query);
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `/searchBooks?query=${query}`, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    console.log("req sent")

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                displayResults(response);
                console.log(response);
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
                    <th>ID</th>
                    <th>Title</th>
                    <th>Authors</th>
                    <th>Publisher</th>
                    <th>Published Date</th>
                    <th>Description</th>
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
