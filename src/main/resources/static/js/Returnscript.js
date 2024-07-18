let selectedBooks = [];
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
    
    if (selectedBooks.some(book => book.bookid === bookid)) {
        selectedBooks = selectedBooks.filter(book => book.bookid !== bookid);
        button.textContent = 'Return';
        button.classList.remove('Added');
    } else {
        selectedBooks.push({ button, bookid, lendId});
        button.textContent = 'Added';
        button.classList.add('Added');
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
