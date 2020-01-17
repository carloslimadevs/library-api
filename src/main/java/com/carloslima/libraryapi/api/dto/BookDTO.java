package com.carloslima.libraryapi.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString @EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
	
	private Integer id;
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String author;
	
	@NotEmpty
	private String isbn;
	
	/*
	 * public Integer getId() { return id; }
	 * 
	 * public void setId(Integer id) { this.id = id; }
	 * 
	 * public String getTitle() { return title; }
	 * 
	 * public void setTitle(String title) { this.title = title; }
	 * 
	 * public String getAutor() { return autor; }
	 * 
	 * public void setAutor(String autor) { this.autor = autor; }
	 * 
	 * public String getIsbn() { return isbn; }
	 * 
	 * public void setIsbn(String isbn) { this.isbn = isbn; }
	 */
}
