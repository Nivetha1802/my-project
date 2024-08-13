function fetchBookDetails() {
    const bookId = document.getElementById("bookid").value;
    if (bookId) {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", `/getBookDetails?bookid=${bookId}`, true);
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