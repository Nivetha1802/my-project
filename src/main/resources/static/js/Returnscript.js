document.addEventListener('DOMContentLoaded', function() {
    populateDates();
});

function populateDates() {
    const returnDate = new Date();
    const returnDatestr = returnDate.toISOString().split('T')[0];
    const rows = document.querySelectorAll('#returnTable tbody tr');
    console.log("loaded dates")
    rows.forEach(row => {
        row.querySelector('.actual_return_date').textContent = returnDatestr;
    });
}

function toggleBook(button, bookid, lendId) {
    const bookIndex = selectedBooks.findIndex(book => book.bookid === bookid && book.lendId === lendId);

    if (bookIndex !== -1) {
        selectedBooks.splice(bookIndex, 1);
        button.textContent = 'Return';
        button.classList.remove('Returned');
    } else {
        
        selectedBooks.push({ bookid, lendId });
        button.textContent = 'Returned';
        button.classList.add('Returned');
    }
    console.log(selectedBooks);
}

function submitReturnBooks() {
    var selectedBooksInput = document.getElementById('selectedBooks');
    selectedBooksInput.value = JSON.stringify(selectedBooks);
    console.log(selectedBooksInput.value);
    const form = document.getElementById('returnBooksForm');
    form.submit();
}
