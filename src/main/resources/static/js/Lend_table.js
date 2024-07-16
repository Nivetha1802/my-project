let selectedBooks = [];

        function toggleBook(button, bookid, bookname, author, subject, bookcount, info) {
            if (bookcount == 0) {
                return; 
            }
            if (selectedBooks.some(book => book.bookid === bookid)) {
                // Remove book from selection
                selectedBooks = selectedBooks.filter(book => book.bookid !== bookid);
                button.textContent = 'Add';
                button.classList.remove('added');
            } else {
                // Add book to selection
                selectedBooks.push({ button, bookid, bookname, author, subject, bookcount, info});
                button.textContent = 'Added';
                button.classList.add('added');
            }
        }


        function submitLendBooks() {
            var selectedBooksInput = document.getElementById('selectedBooks');
            selectedBooksInput.value = JSON.stringify(selectedBooks);
            console.log(selectedBooksInput);
            console.log(selectedBooks);
            document.getElementById('lendForm').submit();
        }
        
    