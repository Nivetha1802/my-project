function calculateFine() {
    const lendingDateInput = document.getElementById('date_of_lending').value;
    const returnDateInput = document.getElementById('date_of_return').value;

    if (!lendingDateInput || !returnDateInput) {
        document.getElementById('fine').value = 0;
        return;
    }

    const lendingDate = new Date(lendingDateInput);
    const returnDate = new Date(returnDateInput);
    
    // Assuming a lending period of 14 days
    const dueDate = new Date(lendingDate);
    dueDate.setDate(lendingDate.getDate() + 14);

    const dailyFineRate = 10; // Fine rate per day in currency units
    let fineAmount = 0;

    if (returnDate > dueDate) {
        const lateDays = Math.ceil((returnDate - dueDate) / (1000 * 60 * 60 * 24));
        fineAmount = lateDays * dailyFineRate;
    }
    console.log("success");
    document.getElementById('fine').value = fineAmount;
}
function setExtendedReturnDate() {
    const returnDateInput = document.getElementById('date_of_return').value;
    const returnDate = new Date(returnDateInput);

    // Check if returnDate is valid
    if (isNaN(returnDate.getTime())) {
        console.error('Invalid return date:', returnDateInput);
        return;
    }

    // Add 14 days to the return date
    returnDate.setDate(returnDate.getDate() + 14);
    const returnYear = returnDate.getFullYear();
    const returnMonth = String(returnDate.getMonth() + 1).padStart(2, '0');
    const returnDay = String(returnDate.getDate()).padStart(2, '0');
    const formattedReturnDate = `${returnYear}-${returnMonth}-${returnDay}`;

    const extendedReturnDateInput = document.getElementById('extended_return_date');
    extendedReturnDateInput.value = formattedReturnDate;

    console.log("Extended return date set successfully");
}

window.onload = function() {
    setExtendedReturnDate();
    calculateFine();
};
