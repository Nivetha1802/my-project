let selectedBooks = [];

function toggleBook(button, lendId) {
    if (selectedBooks.some(book => book.bookid === bookid)) {
        selectedBooks = selectedBooks.filter(book => book.bookid !== bookid);
        button.textContent = 'Renew';
        button.classList.remove('Added');
    } else {
        selectedBooks.push({ button, lendId});
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
