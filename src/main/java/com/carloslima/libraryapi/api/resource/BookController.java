package com.carloslima.libraryapi.api.resource;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carloslima.libraryapi.api.domain.Book;
import com.carloslima.libraryapi.api.dto.BookDTO;
import com.carloslima.libraryapi.api.exception.ApiErrors;
import com.carloslima.libraryapi.api.service.BookService;
import com.carloslima.libraryapi.exeception.BusinessException;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService service;
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody @Valid BookDTO dto) {
		
		Book entityBook = modelMapper.map(dto, Book.class);
		
		entityBook = service.save(entityBook);

		return modelMapper.map(entityBook, BookDTO.class);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidationExceptions(MethodArgumentNotValidException exception) {
		
		BindingResult bindingResult = exception.getBindingResult();
		
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		
		return new ApiErrors (bindingResult);
		
	}
	
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleBusinessException(BusinessException exception) {
		//BindingResult bindingResult = exception.getBindingResult();
		//List<ObjectError> allErrors = bindingResult.getAllErrors();
		
		return new ApiErrors(exception);
		
	}
}
