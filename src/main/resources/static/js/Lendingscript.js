document.addEventListener('DOMContentLoaded', function() {
    populateDates();
});

function populateDates() {
    const lendingDate = new Date();
    const returnDate = new Date();
    returnDate.setDate(lendingDate.getDate() + 14); // 14 days after lending date

    const lendingDateStr = lendingDate.toISOString().split('T')[0];
    const returnDateStr = returnDate.toISOString().split('T')[0];

    const rows = document.querySelectorAll('#lendDetailsTable tbody tr');

    rows.forEach(row => {
        row.querySelector('.lending-date').textContent = lendingDateStr;
        row.querySelector('.return-date').textContent = returnDateStr;
    });
}

function submitLendDetails() {
    const selectedBooks = JSON.parse(sessionStorage.getItem('selectedBooks')) || [];
    const selectedBooksInput = document.getElementById('selectedBooks');
    selectedBooksInput.value = JSON.stringify(selectedBooks);
    console.log(selectedBooksInput.value);
    document.getElementById('lendDetForm').submit();
}
function showSuccessMessage(message) {
    toastr.success(message, 'Success', {
        closeButton: true,
        progressBar: true,
        positionClass: 'toast-top-right',
        showDuration: '300',
        hideDuration: '1000',
        timeOut: '5000',
        extendedTimeOut: '1000'
    });
}
