package com.carloslima.libraryapi.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carloslima.libraryapi.api.domain.Book;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	BookRepository repository;
	
	@Test
	@DisplayName("Deve retornar verdadeiro quando existir Isbn ja cadastrado/duplicado")
	public void returnTrueWhenIsbnExists() {
		
		String isbn = "123";
		Book book = Book.builder().author("artur").title("As aventuras").isbn("123").build();
		entityManager.persist(book );
		boolean exists = repository.existsByIsbn(isbn);
		
		assertThat(exists).isTrue();
	}
}
