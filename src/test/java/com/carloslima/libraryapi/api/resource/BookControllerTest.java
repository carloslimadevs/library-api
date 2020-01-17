package com.carloslima.libraryapi.api.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.carloslima.libraryapi.api.domain.Book;
import com.carloslima.libraryapi.api.dto.BookDTO;
import com.carloslima.libraryapi.api.service.BookService;
import com.carloslima.libraryapi.exeception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class) Junit <= 4
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

	static String BOOK_API = "/api/books";
	@Autowired
	MockMvc mvc;

	@MockBean
	BookService service;

	@Test
	@DisplayName("Deve criar um livro com sucesso")
	public void createBookTest() throws Exception {

		BookDTO dto  = createNewBookDTO();

		Book savedBook = Book.builder().id(101).author("artur").title("As aventuras").isbn("001").build();

		BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(savedBook);

		String json = new ObjectMapper().writeValueAsString(dto);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
				.andExpect(jsonPath("title").value(dto.getTitle())).andExpect(jsonPath("author").value(dto.getAuthor()))
				.andExpect(jsonPath("isbn").value(dto.getIsbn()));

		;

	}

	@Test
	@DisplayName("Deve lancar erro de validacao quando nao houver dados suficientes")
	public void createInvalidBookTest() throws Exception {

		String json = new ObjectMapper().writeValueAsString(new BookDTO());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("errors", Matchers.hasSize(3)));
	}
	
	
    //Validacao de Regra de Negocio
	@Test
	@DisplayName("Deve lancar erro ao tentar cadastrar um livro com isbn ja utilizado por outro.")
	public void createBookWithDuplicatedIsbn() throws Exception{

		BookDTO dto  = createNewBookDTO();

		String json = new ObjectMapper().writeValueAsString(dto);
		
		BDDMockito.given(service.save(Mockito.any(Book.class))).willThrow(new BusinessException("Isbn ja cadastrado"));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(request).andExpect(status().isBadRequest())
		.andExpect(jsonPath("errors", Matchers.hasSize(1)))
		.andExpect(jsonPath("errors[0]").value("Isbn ja cadastrado"));
	}

	
	
	private BookDTO createNewBookDTO() {
	 return BookDTO.builder().author("artur").title("As aventuras").isbn("001").build();
	}
}
