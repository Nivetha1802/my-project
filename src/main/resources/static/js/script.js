document.addEventListener('DOMContentLoaded', function () {
    populateDates();
});

function fetchBookDetails() {
    const bookId = document.getElementById("bookid").value;
    if (isNaN(bookId) || bookId.trim() === "") {
        document.getElementById("bookid-error").textContent = "Please enter a valid Book ID (number).";
        return;
    } else {
        document.getElementById("bookid-error").textContent = "";
    }
    if (bookId) {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", `/getBookDetails?id=${bookId}`, true);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                const book = JSON.parse(xhr.responseText);
                console.log(book);
                document.getElementById("bookname").value = book.bookname;
                document.getElementById("author").value = book.author;
                document.getElementById("subject").value = book.subject;
                document.getElementById("info").value = book.info;
                document.getElementById("Bookcount").value = book.bookcount;
            } else if (xhr.readyState === 4) {
                alert("Book not found");
            }
        };
        xhr.send();
    }
}

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
            id: 1,
            title: row.querySelector('td:nth-child(2)').textContent,
            authors: row.querySelector('td:nth-child(3)').textContent,
            publisher: row.querySelector('td:nth-child(4)').textContent,
            lendDate: row.querySelector('.lending-date').textContent,
            returnDate: row.querySelector('.return-date').textContent,
            bookid: row.querySelector('td:nth-child(1)').textContent
        };
        selectedBooks.push(book);
    });

    const selectedBooksInput = document.getElementById('selectedBooks');
    selectedBooksInput.value = JSON.stringify(selectedBooks);
    console.log(selectedBooksInput.value);
    document.getElementById('lendDetForm').submit();
}


let selectedBooks = [];

function toggleBookForAdd(button, id, title, authors, publisher, publishedDate) {
    if (selectedBooks.some(book => book.id === id)) {
        selectedBooks = selectedBooks.filter(book => book.id !== id);
        button.textContent = 'Add';
        button.classList.remove('added');
    } else {
        selectedBooks.push({id, title, authors, publisher, publishedDate });
        button.textContent = 'Added';
        button.classList.add('added');
    }
}


function submitLendBooks() {
    var selectedBooksInput = document.getElementById('selectedBooks');
    selectedBooksInput.value = JSON.stringify(selectedBooks);
    console.log(selectedBooksInput.value);
    document.getElementById('lendForm').submit();
}


function toggleBookForRenew(button, id) {
    if (selectedBooks.some(book => book.id === id)) {
        selectedBooks = selectedBooks.filter(book => book.id !==id);
        button.textContent = 'Renew';
        button.classList.remove('Added');
    } else {
        selectedBooks.push({ button, id});
        button.textContent = 'Added';
        button.classList.add('Added');
    }
    console.log(selectedBooks);
}

function submitRenewBooks() {
    const selectedBooksInput = document.getElementById('selectedBooks');
    selectedBooksInput.value = JSON.stringify(selectedBooks);
    console.log(selectedBooksInput.value);
    document.getElementById('RenewForm').submit();
}


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
                displayResultsForSearch(response);
            } else if (xhr.readyState === 4) {
                alert("Book not found");
            }
        }
    };
    xhr.send();
}

function displayResultsForSearch(book) {
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