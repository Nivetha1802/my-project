function searchBooks(event) {
    event.preventDefault();

    const bookid = document.getElementById("query").value;
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `/getBookDetails?id=${bookid}`, true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                displayResults(response);
            } else if (xhr.readyState === 4) {
                alert("Book not found");
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
                    <td>${book.id}</td>
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

function showPopup(message) {
    if (message) {
        alert(message);
    }
}
function showMessage(message, type) {
    var div = document.createElement('div');
    div.className = type === 'error' ? 'error-popup' : 'success-popup';
    div.innerText = message;
    document.body.appendChild(div);
    setTimeout(function() {
        document.body.removeChild(div);
    }, 3000);
}