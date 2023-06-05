SELECT book.ISBN, book.Title, book.Edition_No, book.NumOfCop, book.NumLeft, _author.Name, _author.Surname
FROM book
JOIN book_Author bookAuthor ON book.ISBN = bookAuthor.ISBN
JOIN author _author ON _author.AuthorId = bookAuthor.AuthorId
WHERE book.ISBN = <insert isbn>;

SELECT book.ISBN, book.Title, book.Edition_No, book.NumOfCop, book.NumLeft, _author.Name, _author.Surname
FROM book
JOIN book_Author bookAuthor ON book.ISBN = bookAuthor.ISBN
JOIN author _author ON _author.AuthorId = bookAuthor.AuthorId;

SELECT cust_book.ISBN, book.Title, book.Edition_No, book.NumOfCop, book.NumLeft, _author.Name, _author.Surname
FROM cust_book
JOIN book _book ON _book.ISBN = cust_book.ISBN
JOIN book_Author bookAuthor ON book.ISBN = bookAuthor.ISBN
JOIN author _author ON _author.AuthorId = bookAuthor.AuthorId
JOIN customer _customer ON _customer.CustomerID = cust_book.CustomerID

SELECT author.Name, author.Surname
FROM author
WHERE author.AuthorId = <Insert value>;

SELECT author.Name, author.Surname
FROM author

SELECT * FROM CUST_