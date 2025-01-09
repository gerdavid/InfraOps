package com.example.InfraOps.utils;

import com.example.InfraOps.entity.Book;
import com.example.InfraOps.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        bookRepository.save(new Book("The Promised Land", "Grace Ogot"));
        bookRepository.save(new Book("Tracking the Scent of My Mother", "Muthoni Garland"));
        bookRepository.save(new Book("The Cockroach Dance", "Meja Mwangi"));
    }
}
