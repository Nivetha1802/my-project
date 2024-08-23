let selectedBooks = [];

function toggleBook(button, id) {
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
