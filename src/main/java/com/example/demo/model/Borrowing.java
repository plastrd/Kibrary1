package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

//Class for borrowing book, use  the Book class and  LibraryUser class.
@Entity
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private LibraryUser user;
    private LocalDate date;

    //borrowing on progress code:1, complete borrowing code:0
    private int borrowingState =1;

    public Borrowing(){}
    public Borrowing(Book book,LibraryUser user){
        this.book=book;
        this.user=user;
        this.date=LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public LibraryUser getUser() {
        return user;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getBorrowingState() {
        return borrowingState;
    }
    public void setBorrowingState(int borrowingState ) {
        this.borrowingState = borrowingState;
    }
}
