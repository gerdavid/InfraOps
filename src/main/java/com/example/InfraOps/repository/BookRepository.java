package com.example.InfraOps.repository;

import com.example.InfraOps.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
