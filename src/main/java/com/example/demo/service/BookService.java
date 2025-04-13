package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.Borrowing;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Class BookService manage methods about book table in database.
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BorrowingRepository borrowingRepository;

    public BookService(BookRepository bookRepository,BorrowingRepository borrowingRepository) {
        this.bookRepository = bookRepository;
        this.borrowingRepository=borrowingRepository;
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
    public Optional<Book> getBooksById(Long isbn){
        return bookRepository.findById(isbn);
    }
    public List<Book> getBooksByTitle(String title){
        return bookRepository.findByTitle(title);
    }
    public List<Book> getBooksByAuthor(String author){
        return bookRepository.findByAuthor(author);
    }
    public List<Book> getAvailableBooks(){
        return bookRepository.findByCurrentstate(1);
    }

    //Add new book in book table
    public String addBook(Book book){
        Long tempIsbn =book.getIsbn();
        Optional <Book> opUser= bookRepository.findById(tempIsbn);
        //if isbn exists, stop the process
        if (opUser.isPresent()){
            return "This isbn already exists. Please try another one.";
        }
        bookRepository.save(book);
        return "New book has been successfully added.";
    }

    //Try to find book by isbn and update title,author.
    public String updateBoth(Long isbn,String title,String author){
        Optional<Book> currentBook= getBooksById(isbn);
        if (currentBook.isPresent()){
            Book UpdateBook= currentBook.get();
            UpdateBook.setTitle(title);
            UpdateBook.setAuthor(author);
            bookRepository.save(UpdateBook);
            return ("Update completed successfully.");
        }else{
            return ("Book: "+isbn+" not found.");
        }
    }

    //Try to find book by isbn and update only title.
    public String updateTitle(Long isbn,String title){
        Optional<Book> currentBook= getBooksById(isbn);
        if (currentBook.isPresent()){
            Book UpdateBook= currentBook.get();
            UpdateBook.setTitle(title);
            bookRepository.save(UpdateBook);
            return ("Title: "+title+" updated successfully.");
        }else{
            return ("Book: "+isbn+" not found.");
        }
    }

    //Try to find book by isbn and update only author.
    public String updateAuthor(Long isbn,String author){
        Optional<Book> currentBook= getBooksById(isbn);
        if (currentBook.isPresent()){
            Book UpdateBook= currentBook.get();
            UpdateBook.setAuthor(author);
            bookRepository.save(UpdateBook);
            return ("Author: "+author+" updated successfully.");
        }else{
            return ("Book: "+isbn+" not found.");
        }
    }

    //Try to find book by isbn and delete it.
    //This method search in book table and borrowing Table
    public String deleteBook(Long isbn) {
        Optional<Book> currentBook = getBooksById(isbn);
        if (currentBook.isPresent()) {
            //if some user borrowed this book, delete stop.
            List<Borrowing> borrowingList = borrowingRepository.findAllByBookIsbn(isbn);
            if (borrowingList.isEmpty()) {
                bookRepository.deleteById(isbn);
                return "Book: " + isbn + " deleted.";
            }
            return "Book:" + isbn + " cannot be deleted due to history with users.";
        } else {
            return ("Book:" + isbn + " not found.");
        }
    }
}
