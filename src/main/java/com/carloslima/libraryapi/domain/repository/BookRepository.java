package com.carloslima.libraryapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carloslima.libraryapi.api.domain.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

	boolean existsByIsbn(String isbn);

	
}
