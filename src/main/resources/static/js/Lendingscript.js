function setDates() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    const formattedDate = `${year}-${month}-${day}`;

    const lendingDateInput = document.getElementById('date_of_lending');
    lendingDateInput.value = formattedDate;

    const returnDate = new Date(today);
    returnDate.setDate(today.getDate() + 14);
    const returnYear = returnDate.getFullYear();
    const returnMonth = String(returnDate.getMonth() + 1).padStart(2, '0');
    const returnDay = String(returnDate.getDate()).padStart(2, '0');
    const formattedReturnDate = `${returnYear}-${returnMonth}-${returnDay}`;

    const returnDateInput = document.getElementById('date_of_return');
    returnDateInput.value = formattedReturnDate;
    console.log("Dates set successfully");
}

// function calculateFine() {
//     const lendingDateInput = document.getElementById('date_of_lending').value;
//     const returnDateInput = document.getElementById('date_of_return').value;

//     if (!lendingDateInput || !returnDateInput) {
//         document.getElementById('fine').value = 0;
//         return;
//     }

//     const lendingDate = new Date(lendingDateInput);
//     const returnDate = new Date(returnDateInput);

//     const dueDate = new Date(lendingDate);
//     dueDate.setDate(lendingDate.getDate() + 14);

//     const dailyFineRate = 10; // Fine rate per day in currency units
//     let fineAmount = 0;

//     if (returnDate > dueDate) {
//         const lateDays = Math.ceil((returnDate - dueDate) / (1000 * 60 * 60 * 24));
//         fineAmount = lateDays * dailyFineRate;
//     }

//     document.getElementById('fine').value = fineAmount;
//     console.log("Fine calculated successfully");
// }

window.onload = function() {
    setDates();
    // calculateFine();
};
