let selectedBooks = [];

function toggleBook(button, lendId) {
    if (selectedBooks.includes(lendId)) {
        selectedBooks = selectedBooks.filter(id => id !== lendId);
        button.textContent = 'Add';
        button.classList.remove('Added');
        document.getElementById(`extended_return_date_${lendId}`).textContent = '';
    } else {
        selectedBooks.push(lendId);
        button.textContent = 'Added';
        button.classList.add('Added');
        calculateExtendedReturnDate(lendId);
    
    }
    console.log(selectedBooks);
}

function calculateExtendedReturnDate(lendId) {
    const dueDateElement = document.getElementById(`due_date_${lendId}`);
    console.log(dueDateElement);
    const dueDate = new Date(dueDateElement.innerText);
    dueDate.setDate(dueDate.getDate() + 14);
    console.log(dueDate);
    const extendedReturnDateStr = dueDate.toISOString().split('T')[0];
    document.getElementById(`extended_return_date_${lendId}`).textContent = extendedReturnDateStr;
}

function submitRenewBooks() {
    const selectedBooksInput = document.getElementById('selectedBooks');
    selectedBooksInput.value = selectedBooks.join(',');
    document.getElementById('RenewForm').submit();
}
