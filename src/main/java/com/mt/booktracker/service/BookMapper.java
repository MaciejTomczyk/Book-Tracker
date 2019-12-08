package com.mt.booktracker.service;

import com.mt.booktracker.model.AddBookCommand;
import com.mt.booktracker.model.Book;
import com.mt.booktracker.model.BookResponse;
import com.mt.booktracker.model.UpdateBookCommand;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookMapper {

    public static Book map(AddBookCommand addBookCommand) {
        return Book.builder()
                .isbn(addBookCommand.getIsbn())
                .title(addBookCommand.getTitle())
                .author(addBookCommand.getAuthor())
                .numberOfPages(addBookCommand.getNumberOfPages())
                .rating(addBookCommand.getRating())
                .build();
    }

    public static BookResponse map(Book book) {
        return BookResponse.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .numberOfPages(book.getNumberOfPages())
                .rating(book.getRating())
                .build();
    }

    public static Book createUpdatedBook(String id, UpdateBookCommand updateBookCommand) {
        return Book.builder()
                .isbn(id)
                .title(updateBookCommand.getTitle())
                .author(updateBookCommand.getAuthor())
                .numberOfPages(updateBookCommand.getNumberOfPages())
                .rating(updateBookCommand.getRating())
                .build();
    }
}
