package com.carloslima.libraryapi.service.impl;

import org.springframework.stereotype.Service;

import com.carloslima.libraryapi.api.domain.Book;
import com.carloslima.libraryapi.api.service.BookService;
import com.carloslima.libraryapi.domain.repository.BookRepository;
import com.carloslima.libraryapi.exeception.BusinessException;

@Service
public class BookServiceImp implements BookService {

	private BookRepository repository;

	
	public BookServiceImp(BookRepository bookRepository) {
		// TODO Auto-generated constructor stub
		this.repository = bookRepository;
	}
	
	@Override
	public Book save(Book book) {
		if(repository.existsByIsbn(book.getIsbn())) {
			throw new BusinessException("Isbn ja cadastrado");
		}
		// TODO Auto-generated method stub
		return repository.save(book);
	}

}
