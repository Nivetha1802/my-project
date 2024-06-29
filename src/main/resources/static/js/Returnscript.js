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
function setDates() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); 
    const day = String(today.getDate()).padStart(2, '0');
    const formattedDate = `${year}-${month}-${day}`;

    const returnDateInput = document.getElementById('actual_date_of_return');
    returnDateInput.value = formattedDate;
    console.log("success");
}

window.onload = function() {
    setDates();
    calculateFine();
};