package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

//Class book has isdn for id and extra elements: title,author,currentState.
@Entity
public class Book {

    @Id
    private long isbn;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private String author;

    //Available book for rent: code 1
    private int currentstate=1;

    public Book() {}

    public Book(long isbn,String title,String author){
        this.title=title;
        this.author=author;
        this.isbn=isbn;
    }

    public String getTitle(){return title;}

    public String getAuthor(){return author;}
    public long getIsbn(){return isbn;}
    public int getCurrentstate(){return currentstate;}
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCurrentstate(int currentstate) { this.currentstate = currentstate; }
}
