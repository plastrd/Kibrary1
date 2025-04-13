package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// BookController manages the urls, that relate to the book table  in database.
@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookservice;
    private final TokenService tokenservice;


    public BookController(BookService bookservice,TokenService tokenservice){
        this.bookservice=bookservice;
        this.tokenservice=tokenservice;
    }

    //http://localhost:8080/api/book + jsonbody
    // Add new book in database
    //Required Admin 's Bearer Token
    @PostMapping()
    public ResponseEntity<String> add_book(@RequestHeader("Authorization") String token,@RequestBody Book book){
        String newToken=token.replace("Bearer ", "");
        if(tokenservice.authorize_Admin(newToken)){
            return ResponseEntity.ok(bookservice.addBook(book));
        }else {
           return ResponseEntity.ok("Limited Access.Only Admin.");
        }
    }

    //http://localhost:8080/api/book/all
    // Returns all books from database. Required Admin 's Bearer Token.
    @GetMapping("/all")
    public ResponseEntity<?>  get_AllBooks(@RequestHeader("Authorization") String token){
        String newToken=token.replace("Bearer ", "");
        if(tokenservice.authorize_Admin(newToken)){
            List<Book> allBooks=bookservice.getAllBooks();
            if( allBooks.isEmpty()){
                return ResponseEntity.ok("No book in the database.");
            }
            return ResponseEntity.ok(allBooks);
        }else {
            return ResponseEntity.ok("Limited Access.Only Admin.");
        }
    }

    // http://localhost:8080/api/book/bytitle?title=...
    // Returns all books that match the given title
    @GetMapping("/bytitle")
    public ResponseEntity<?> get_BooksByTitle(@RequestParam String title){
        List<Book> BooksByTitle=bookservice.getBooksByTitle(title);
        if( BooksByTitle.isEmpty()){
            return ResponseEntity.ok("No book found.");
        }
        return ResponseEntity.ok(BooksByTitle);
    }

    // http://localhost:8080/api/book/byauthor?author=...
    // Returns all books that match the given author
    @GetMapping("/byauthor")
    public ResponseEntity<?> get_BooksByAuthor(@RequestParam String author){
        List<Book> BooksByAuthor=bookservice.getBooksByAuthor(author);
        if( BooksByAuthor.isEmpty()){
            return ResponseEntity.ok("No book found.");
        }
        return ResponseEntity.ok(BooksByAuthor);
    }

    // http://localhost:8080/api/book/available
    // Returns all available books for borrowing
    @GetMapping("/available")
    public ResponseEntity<?> get_availableBooks(){
        List<Book> availableBooks=bookservice.getAvailableBooks();
        if( availableBooks.isEmpty()){
            return ResponseEntity.ok("No book found.");
        }
        return ResponseEntity.ok(availableBooks);
    }

    // http://localhost:8080/api/book/update/{isbn}?title=...&author=...
    // Update Book Details (title,author) by ISBN.
    // Required Admin's Bearer Token.
    @PatchMapping("/update/{isbn}")
    public ResponseEntity<String> updateBook(@RequestHeader("Authorization") String token,@PathVariable Long isbn,@RequestParam String title,@RequestParam String author)
    {
        String newToken=token.replace("Bearer ", "");
        if(tokenservice.authorize_Admin(newToken)){
            return ResponseEntity.ok(bookservice.updateBoth(isbn,title,author));
        }else {
            return ResponseEntity.ok("Limited Access.Only Admin.");
        }
    }

    // http://localhost:8080/api/book/update/title/{isbn}?title=....
    // Update Book Title by ISBN.
    // Required Admin's Bearer Token.
    @PatchMapping("/update/title/{isbn}")
    public ResponseEntity<String> updateBookTitle(@RequestHeader("Authorization") String token,@PathVariable Long isbn,@RequestParam String title)
    {
        String newToken=token.replace("Bearer ", "");
        if(tokenservice.authorize_Admin(newToken)){
            return ResponseEntity.ok(bookservice.updateTitle(isbn,title));
        }else {
            return ResponseEntity.ok("Limited Access.Only Admin.");
        }
    }

    // http://localhost:8080/api/book/update/author/{isbn}?author=....
    // Update Book Title by ISBN.
    // Required Admin's Bearer Token.
    @PatchMapping("/update/author/{isbn}")
    public ResponseEntity<String> updateBookAuthor(@RequestHeader("Authorization") String token,@PathVariable Long isbn,@RequestParam String author)
    {
        String newToken=token.replace("Bearer ", "");
        if(tokenservice.authorize_Admin(newToken)){
            return ResponseEntity.ok(bookservice.updateAuthor(isbn,author));
        }else {
            return ResponseEntity.ok("Limited Access. Only Admin.");
        }
    }

    // http://localhost:8080/api/book/{isbn}
    // Delete Book by ISBN, only if book never be borrowing.
    // Required Admin's Bearer Token.
    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> delete_Book(@RequestHeader("Authorization") String token,@PathVariable Long isbn){
        String newToken=token.replace("Bearer ", "");
        if(tokenservice.authorize_Admin(newToken)){
            return ResponseEntity.ok(bookservice.deleteBook(isbn));
        }else {
            return ResponseEntity.ok("Limited Access. Only Admin.");
        }
    }


}
