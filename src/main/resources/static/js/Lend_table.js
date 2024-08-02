let selectedBooks = [];

        function toggleBook(button, id, title, authors, publisher, publishedDate) {
            if (selectedBooks.some(book => book.id === id)) {
                selectedBooks = selectedBooks.filter(book => book.id !== id);
                button.textContent = 'Add';
                button.classList.remove('added');
            }else {
                const authorsArray = authors.replace(/^\[|\]$/g, '').split(',').map(author => author.trim());
                selectedBooks.push({ button, id, title, authors: authorsArray, publisher, publishedDate });
                button.textContent = 'Added';
                button.classList.add('added');
            }
        }


        function submitLendBooks() {
            var selectedBooksInput = document.getElementById('selectedBooks');
            selectedBooksInput.value = JSON.stringify(selectedBooks);
            console.log(selectedBooksInput.value);
            document.getElementById('lendForm').submit();
        }

        
        
    