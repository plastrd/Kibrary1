package com.example.demo.controller;

import com.example.demo.service.BorrowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controller for borrowing Book. User/Admin can borrow book and return it.
@RestController
@RequestMapping("/api/borrowing")
public class BorrowingController {
    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService){
        this.borrowingService=borrowingService;
    }

    // http://localhost:8080/api/borrowing/{isbn}?username=...
    // Creates a new record in the borrowing table, indicating that a user has borrowed a book with the given ISBN.
    @PostMapping("/{isbn}")
    public ResponseEntity<String> new_borrowing(@PathVariable  Long isbn,@RequestParam String username){
        return ResponseEntity.ok(borrowingService.newBorrow(isbn,username));
    }

    // http://localhost:8080/api/borrowing/return/{isbn}?username=...
    // find  specific record in the borrowing ,if borrowingState==1 update it to 0.
    @PatchMapping("/return/{isbn}")
    public ResponseEntity<String> return_Book(@PathVariable  Long isbn,@RequestParam String username){
        return ResponseEntity.ok(borrowingService.returnBook(isbn,username));
    }
}
