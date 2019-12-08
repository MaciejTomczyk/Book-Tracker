package com.mt.booktracker.service;

import com.mt.booktracker.BookRepository;
import com.mt.booktracker.exception.BookAlreadyExistsException;
import com.mt.booktracker.exception.BookNotFoundException;
import com.mt.booktracker.model.AddBookCommand;
import com.mt.booktracker.model.Book;
import com.mt.booktracker.model.UpdateBookCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackerServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private Book book;

    @InjectMocks
    private TrackerService trackerService;

    @Test
    @DisplayName("should return existing books")
    void getBooks() {
        //given
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(book)));

        //when
        var result = trackerService.getBooks(mock(Pageable.class));

        //then
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("should return existing book by id")
    void findByIdBookExists() {
        //given
        when(bookRepository.findById(any(String.class))).thenReturn(Optional.of(book));

        //then
        var result = assertDoesNotThrow(() -> trackerService.findByIsbn(anyString()));
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("should throw exception - book does not exists")
    void findByIdBookDoesNotExist() {
        //given
        when(bookRepository.findById(any(String.class))).thenReturn(Optional.empty());

        //then
        assertThrows(BookNotFoundException.class, () -> trackerService.findByIsbn(anyString()));
    }

    @Test
    @DisplayName("should add a new book")
    void addNonExistingBook() {
        //given
        AddBookCommand addBookCommand = prepareAddCommand();

        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        //then
        assertDoesNotThrow(() -> trackerService.addBook(addBookCommand));
    }

    @Test
    @DisplayName("should throw exception - book already exists")
    void addExistingBook() {
        //given
        AddBookCommand addBookCommand = prepareAddCommand();

        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));

        //then
        assertThrows(BookAlreadyExistsException.class, () -> trackerService.addBook(addBookCommand));
    }

    @Test
    @DisplayName("should delete book by id")
    void deleteExistingBook() {
        //given
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));

        //then
        assertDoesNotThrow(() -> trackerService.deleteBook(anyString()));
    }

    @Test
    @DisplayName("should throw exception - book does not exist")
    void deleteNonExistingBook() {
        //given
        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());

        //then
        assertThrows(BookNotFoundException.class, () -> trackerService.deleteBook(anyString()));
    }

    @Test
    @DisplayName("should update existing book")
    void updateExistingBook() {
        //given
        UpdateBookCommand updateBookCommand = prepareUpdateCommand();

        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));

        //then
        assertDoesNotThrow(() -> trackerService.updateBook(anyString(), updateBookCommand));
        verify(bookRepository, atMostOnce()).save(any(Book.class));
    }

    @Test
    @DisplayName("should throw exception - book does not exist")
    void updateNonExistingBook() {
        //given
        UpdateBookCommand updateBookCommand = prepareUpdateCommand();

        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());

        //then
        assertThrows(BookNotFoundException.class, () -> trackerService.updateBook(anyString(), updateBookCommand));
        verify(bookRepository, times(0)).save(any(Book.class));
    }

    private AddBookCommand prepareAddCommand() {
        return new AddBookCommand("isbn", "title", "author", 1, 2);
    }

    private UpdateBookCommand prepareUpdateCommand() {
        return new UpdateBookCommand("title", "author", 3, 4);
    }
}