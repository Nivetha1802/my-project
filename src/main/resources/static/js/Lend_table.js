let selectedBooks = [];

        function toggleBook(button, bookId, bookName, bookAuthor, bookSubject, bookInfo) {
            if (selectedBooks.some(book => book.bookId === bookId)) {
                // Remove book from selection
                selectedBooks = selectedBooks.filter(book => book.bookId !== bookId);
                button.textContent = 'Add';
                button.classList.remove('added');
            } else {
                // Add book to selection
                selectedBooks.push({ bookId, bookName, bookAuthor, bookSubject, bookInfo });
                button.textContent = 'Added';
                button.classList.add('added');
            }
        }

        function submitLendBooks() {
            if (selectedBooks.length === 0) {
                alert("No books selected for lending.");
                console.log("no books");
                return;
            }

            const form = document.createElement('form');
            form.method = 'POST';
            form.action = contextPath + '/submitlendtable';

            selectedBooks.forEach(book => {
                const inputBookId = document.createElement('input');
                inputBookId.type = 'hidden';
                inputBookId.name = 'bookIds';
                inputBookId.value = book.bookId;
                form.appendChild(inputBookId);
                console.log(inputBookId);

                const inputBookName = document.createElement('input');
                inputBookName.type = 'hidden';
                inputBookName.name = 'bookNames';
                inputBookName.value = book.bookName;
                form.appendChild(inputBookName);
                console.log(inputBookId);

                const inputBookAuthor = document.createElement('input');
                inputBookAuthor.type = 'hidden';
                inputBookAuthor.name = 'bookAuthors';
                inputBookAuthor.value = book.bookAuthor;
                form.appendChild(inputBookAuthor);
				console.log(inputBookId);

				const inputBookSubject = document.createElement('input');
				inputBookSubject.type = 'hidden';
				inputBookSubject.name = 'bookSubjects';
				inputBookSubject.value = book.bookSubject;
				form.appendChild(inputBookSubject);
				console.log(inputBookId);

				const inputBookInfo = document.createElement('input');
				inputBookInfo.type = 'hidden';
				inputBookInfo.name = 'bookInfos';
				inputBookInfo.value = book.bookInfo;
				form.appendChild(inputBookInfo);
                console.log(inputBookId);
            });

            document.body.appendChild(form);
            console.log("data added");
            form.submit();
            console.log("data submitted");
        }