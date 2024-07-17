function calculateFine(bookId) {
    const dueDateInput = document.getElementById(`due_date_${bookId}`).innerText;

    if (!dueDateInput) {
        document.getElementById(`fine_${bookId}`).innerText = '0';
        return;
    }

    const dueDate = new Date(dueDateInput);
    const actualReturnDate = new Date();

    const dailyFineRate = 10;
    let fineAmount = 0;

    if (actualReturnDate > dueDate) {
        const lateDays = Math.ceil((actualReturnDate - dueDate) / (1000 * 60 * 60 * 24));
        fineAmount = lateDays * dailyFineRate;
    }

    const actualreturnDateStr = actualReturnDate.toISOString().split('T')[0];

    console.log("success");
    document.getElementById(`fine_${bookId}`).innerText = fineAmount.toFixed(2);
    const rows = document.querySelectorAll('#lendDetailsTable tbody tr');

            rows.forEach(row => {
                row.querySelector('.lending-date').textContent = lendingDateStr;
                row.querySelector('.return-date').textContent = returnDateStr;
            });
    document.getElementById(`actual_return_date_${bookId}`).innerText = actualreturnDateStr;
}

let selectedBooks = [];

function toggleBook(button, lendId) {
    if (selectedBooks.includes(lendId)) {
        selectedBooks = selectedBooks.filter(id => id !== lendId);
        button.textContent = 'Return';
        button.classList.remove('Returned');
    } else {
        selectedBooks.push(lendId);
        button.textContent = 'Returned';
        button.classList.add('Returned');
    }
    console.log(slectedBooks);
}

function submitReturnBooks() {
    var selectedBooksInput = document.getElementById('selectedBooks');
    selectedBooksInput.value = selectedBooks.join(',');
    console.log(selectedBooksInput.value);
    const form = document.getElementById('returnBooksForm');
    form.submit();
}
