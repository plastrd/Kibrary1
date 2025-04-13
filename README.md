
  Welcome to Kibrary application.
-------------------

 The Kibrary application handles Rest Api requests and manages the underlying database.

 IDEA
 
 Each book in the system, has a unique ISBN and can be borrowed by only one user at a time (total copies = 1).
 Book becomes available for rent again, only after the previous user return it.

 BORROWING LOGIC
 
 When a user rent a book, a new record is created in the database and when he return it , the BorrowingState (variable)
 is set to 0 (indicating that the borrowing has been completed).
 However, the borrowing_record is not deleted, still remains in database like user's history. For example,
 this allows us, to track how many times a user has borrowed books or which specific books they borrowed.

 CAUTION
 
 Because system retains all borrowing records, Admin can remove a book from them database, only when
 the book has never been borrowed. In other words, book can be removed only if its isbn_key is not referenced as
 a foreign key in the borrowing table.

_________________________

 DATABASE
 
 The application uses PostgreSQL as its database system and includes the following three tables:

1. Book Table (Stores book records)
   
   Primary Key: isbn (each book has a unique ISBN).
   
   All columns are NOT NULL – every field is required.

3. Borrowing Table (Stores all the data related to book borrowings)
   
   Primary Key: username (each user has a unique username).
   
   All columns are NOT NULL– every field is required.

5. Library_User Table (Stores users' personal data)
   
   Primary Key: borrowing_id.
   
   Includes foreign keys:
    1) isbn references the Book Table
    2) username  references the Library_User Table

-------------------------

 The main part of the program contains 4 main packages, main application file,
 and the application.properties configuration file.

 Packages:
 
 1.Model
  Contains the core classes of the system: Book, Borrowing, LibraryUser and Login class.
  These classes represent the main entities of the application.

2.Repository
  Contains the repositories: BookRepository, BorrowingRepository, UserRepository.
  Each repository provides methods for direct interaction with its corresponding database table.

3.Service
  Contains the services: BookService,BorrowingService,TokenService,UserService.
  The services contain the logic of the application and act as a bridge between controllers and repositories.

4.Controller
  Contains the controllers: BookController, BorrowingController, UserController.
  Controllers handle incoming HTTP requests (URLs) and call the appropriate service to perform the requested operations.



  Available requests:
-------------------

 1)UserController
-------------------
-->LibraryUser Registration.

  Method: POST
  
  URL: http://localhost:8080/api/user/registration
  
  Authorization: Not required
  
  Request Body Examples:
  
  For User:
  {
    "name":"Thanasis",
    "surname":"papadopoulos",
    "username":"thanos35",
    "password":"3443",
    "role":1
  }
  
  For Admin:
  {
    "name":"Nikoleta",
    "surname":"Anastasiou",
    "username":"admin1",
    "password":"8888",
    "role": 0
  }

--->Login

  Authenticates a user and returns a Bearer Token if the credentials are valid.
  
  Method: POST
  
  URL: http://localhost:8080/api/user/login
  
  Authorization: Not required
  
  Request Body Examples:
  {
    "username":"thanos35",
    "password":"3443"
  }
  
  {
    "username":"admin1",
    "password":"8888"
  }
  
  {
    "username":"than",
    "password":"3090"
 }

 2)BookController
-------------------


--->Add new book in database.

 Method: POST
 
 URL: http://localhost:8080/api/book
 
 Authorization: Requires Admin's Bearer Token
 
 Request Body Example:
 {
   "isbn":"1234",
   "title":"SecretOfLife",
   "author":"papanikolas"
 }
 
 {
   "isbn":"8989",
   "title":"Computer",
   "author":"papadopoulos"
 }
 
 {
   "isbn":"98909",
   "title":"SecretOfLife", 
   "author":"Nikolaou"
 }
 
 {
   "isbn":"89876",
   "title":"Fire",
   "author":"Nikolaou"
 }



--->Returns all books from database.

  Method: GET
  
  URL: http://localhost:8080/api/book/all
  
  Authorization: Requires Admin's Bearer Token
  


--->Returns all books that match the given title.

  Method: GET
  
  URL: http://localhost:8080/api/book/bytitle?title=...
  
  Authorization: Not required
  
  Example: http://localhost:8080/api/book/bytitle?title=secret
           http://localhost:8080/api/book/bytitle?title=SecretOfLife



--->Returns all books that match the given author.

  Method: GET
  
  URL: http://localhost:8080/api/book/byauthor?author=...
  
  Authorization: Not required
  
  Example: http://localhost:8080/api/book/byauthor?author=Nikolaou
           http://localhost:8080/api/book/byauthor?author=ioann



--->Returns all available books for borrowing.

  Method: GET
  
  URL: http://localhost:8080/api/book/available
  
  Authorization: Not required

  

--->Update Book Details (title,author) by ISBN.

 Method: PATCH
 
 URL: http://localhost:8080/api/book/update/{isbn}?title=....&author=....
 
 Authorization: Requires Admin's Bearer Token
 
 Example: http://localhost:8080/api/book/update/1234?title=life&author=ioannou
          http://localhost:8080/api/book/update/1234456789?title=life&author=ioannou



--->Update Book Title by ISBN.

  Method: PATCH
  
  URL: http://localhost:8080/api/book/update/title/{isbn}?title=....
  
  Authorization: Requires Admin's Bearer Token
  
  Example: http://localhost:8080/api/book/update/title/1234?title=TheMeaningOfLife
           http://localhost:8080/api/book/update/title/1234456789?title=life



--->Update Book Author by ISBN.

  Method: PATCH
  
  URL: http://localhost:8080/api/book/update/author/{isbn}?author=....
  
  Authorization: Requires Admin's Bearer Token
  
  Example: http://localhost:8080/api/book/update/author/1234?author=argiriou
           http://localhost:8080/api/book/update/author/1234456789?author=life

--->Delete Book by ISBN.

  Method: DELETE
  
  URL: http://localhost:8080/api/book/{isbn}
  
  Authorization: Requires Admin's Bearer Token
  
  Example: http://localhost:8080/api/book/8989
           http://localhost:8080/api/book/12348989


 3)BorrowingController
-------------------


--->Borrow a Book.

  Creates a new record in the borrowing table, indicating that a user has borrowed a book with the given ISBN.
  
  Method: POST
  
  URL: http://localhost:8080/api/borrowing/{isbn}?username=...
  
  Authorization: Not required
  
  Example: http://localhost:8080/api/borrowing/1234?username=thanos35
           http://localhost:8080/api/borrowing/190976?username=thanos35


--->Return a book.

  Method: PATCH
  
  URL: http://localhost:8080/api/borrowing/return/{isbn}?username=...
  
  Authorization: Not required
  
  Example: http://localhost:8080/api/borrowing/return/1234?username=thanos35
           http://localhost:8080/api/borrowing/return/784512?username=thanos35

------------------------- 

Authentication

Because I didn't know how to do it, i created a manually generated code like token for each role.
When the user login succesfully, system returns a role-based token.
This token must be included in the request when accessing endpoints that require authorization.
Tokens are simple strings (not real JWTs) and are used to identify if the user has the right permissions.

-->Endpoints That Require Authorization

  Only Admin users can perform the following actions:

  Add a new book
  
  Update an existing book
  
  Delete a book
  
  Get all books

 All other endpoints, are available to both Admins and regular Users.

  More ideas:
  
  Generate unique tokens on login.
  On every successful login, the system could generate a unique personal token per user and save it.
  This token would then be required in authorized requests.

  The other idea is to include username and password in every request URL.

-----------------------

 Ideas for Future Improvements

 1.Book Copies
 
 Currently, each book has only one available copy, so it can only be borrowed by one user at a time.
 A future improvement would be to allow multiple copies per book, so multiple users can borrow the same book
 if there are enough copies.

 2.Hide Deleted Books Instead of Removing
 
 When the admin deletes a book, instead of removing it from the database, the system can just hide it
 from the available books list.
 This way, the borrowing history has no problem, and the book is just marked as "not available" instead of being deleted.

 3.Better JSON Error Handling
 
 Currently, if the request body (in add book or user registration) has wrong JSON format, the system shows a 500 Error.
 System has problem, bus it doesn't need restart.
 It would be smarter if the system caught this issue and returned a friendly message.

 4.ISBN Validation Improvement
 
 Currently, the system does not handle ISBN validation explicitly. 
 
 A future improvement could include:
 Ensuring the ISBN is a valid numeric string (only digits).
 If the ISBN contains any non-numeric characters, the system should return an error message.
 
