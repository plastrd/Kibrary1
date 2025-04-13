package com.example.demo.repository;
import com.example.demo.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    Optional<Borrowing> findByUserUsernameAndBookIsbnAndBorrowingState(String username, Long isbn,int borrowingState);
    List<Borrowing>  findAllByUserUsernameAndBookIsbnAndBorrowingState(String username, Long isbn,int borrowingState);
    List<Borrowing>  findAllByUserUsernameAndBorrowingState(String username,int borrowingState);
    List<Borrowing>  findAllByBookIsbn(Long isbn);
}
