package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.Borrowing;
import com.example.demo.model.LibraryUser;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Class BorrowingService manage only two methods: newBorrow and returnBook.
@Service
public class BorrowingService {
    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final UserService userService;

    public BorrowingService(BorrowingRepository borrowingRepository,BookRepository bookRepository, BookService bookService, UserService userService) {
        this.borrowingRepository = borrowingRepository;
        this.bookRepository=bookRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    //Search book by isbn from  book table
    //Search user by username from Library_user table
    // if book is available, add new record in borrowing table
    public String newBorrow(Long isbn, String username) {
        Optional<Book> opBook = bookService.getBooksById(isbn);
        if (opBook.isPresent()) {
                Optional<LibraryUser> opUser = userService.getUserById(username);
                if (opUser.isPresent()) {
                    int available=opBook.get().getCurrentstate();
                    if (available==1){
                        Book updatedBook = opBook.get();
                        LibraryUser user = opUser.get();
                        // set currentState=0
                        updatedBook.setCurrentstate(0);
                        bookRepository.save(updatedBook);
                        //Create borrowing
                        Borrowing bb = new Borrowing(updatedBook, user);
                        borrowingRepository.save(bb);
                        return "Book borrowed successfully.";
                    }else{
                        return "Book has already been borrowed. Borrowing unsuccessful.";
                    }
                } else {
                    return "User not found.";
                }
        }
        return "Book not found.";
    }


    // Try to find a specific record in the Borrowing Table and set BorrowingState=0
   public String returnBook(Long isbn, String username) {
       //Search book by isbn from  book table
       Optional<Book> opBook = bookService.getBooksById(isbn);
       if (opBook.isPresent()) {
           //Search user by username from Library_user table
           Optional<LibraryUser> opUser = userService.getUserById(username);
           if (opUser.isPresent()) {

               Optional<Borrowing> opBorrowing = borrowingRepository.findByUserUsernameAndBookIsbnAndBorrowingState(username, isbn, 1);
               if (opBorrowing.isPresent()) {
                   Borrowing updatedBorrowing = opBorrowing.get();
                   Book updatedBook = opBook.get();

                   updatedBook.setCurrentstate(1);
                   bookRepository.save(updatedBook);

                   updatedBorrowing.setBorrowingState(0);
                   borrowingRepository.save(updatedBorrowing);
                   return "Returning progress completed successfully.";

               } else {
                   //Search user's history
                   List<Borrowing> list_Borrowings = borrowingRepository.findAllByUserUsernameAndBookIsbnAndBorrowingState(username, isbn, 0);
                   if (!list_Borrowings.isEmpty()) {
                       return "User: " + username + " borrowed book: "+ isbn+", " + list_Borrowings.size() + " times in the past and returned it.";
                   } else {
                       return "User: " + username + " never borrowed book: " + isbn + ".";
                   }
               }
           } else {
                   return "User not found.";
           }
       } else {
           return "Book not found.";
       }
    }

}
