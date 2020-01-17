package com.carloslima.libraryapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carloslima.libraryapi.api.domain.Book;
import com.carloslima.libraryapi.api.service.BookService;
import com.carloslima.libraryapi.domain.repository.BookRepository;
import com.carloslima.libraryapi.exeception.BusinessException;
import com.carloslima.libraryapi.service.impl.BookServiceImp;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {
	
	BookService service;
	
	@MockBean
    BookRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.service = new BookServiceImp(repository);
		
	}
	@Test
	@DisplayName("Deve salvar um livro")
	public void saveBookTest() {
		
		Book book = Book.builder().isbn("1212").author("fulano").title("As aventuras").build();
		
		Mockito.when(repository.save(book)).thenReturn(Book.builder().id(11).isbn("1212").author("fulano").title("As aventuras").build());
		
		Book bookSaved = service.save(book);
		
		assertThat(bookSaved.getId()).isNotNull();
		assertThat(bookSaved.getIsbn()).isEqualTo("1212");
		assertThat(bookSaved.getAuthor()).isEqualTo("fulano");
		assertThat(bookSaved.getTitle()).isEqualTo("As aventuras");
	}
	
	@Test
	@DisplayName("Nao deve salvar livro com Isbn ja existente")
	public void ShouldNotSaveBookWithIsbnDuplicated() {
		
		Book book = Book.builder().isbn("1212").author("fulano").title("As aventuras").build();
		
		//service.save(book)
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);
		
		Throwable exception = Assertions.catchThrowable(() -> service.save(book));
		
		assertThat(exception)
		.isInstanceOf(BusinessException.class).hasMessage("Isbn ja cadastrado");
		
		Mockito.verify(repository,Mockito.never()).save(book);
		
		
	}
}
