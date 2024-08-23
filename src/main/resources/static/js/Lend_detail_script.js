document.addEventListener('DOMContentLoaded', function () {
    populateDates();
});

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
            title: row.querySelector('td:nth-child(1)').textContent,
            authors: row.querySelector('td:nth-child(2)').textContent,
            publisher: row.querySelector('td:nth-child(3)').textContent,
            lendDate: row.querySelector('.lending-date').textContent,
            returnDate: row.querySelector('.return-date').textContent
        };
        selectedBooks.push(book);
    });

    const selectedBooksInput = document.getElementById('selectedBooks');
    selectedBooksInput.value = JSON.stringify(selectedBooks);
    console.log(selectedBooksInput.value);
    document.getElementById('lendDetForm').submit();
}