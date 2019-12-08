package com.mt.booktracker.service;

import com.mt.booktracker.BookRepository;
import com.mt.booktracker.exception.BookAlreadyExistsException;
import com.mt.booktracker.exception.BookNotFoundException;
import com.mt.booktracker.model.AddBookCommand;
import com.mt.booktracker.model.BookResponse;
import com.mt.booktracker.model.Page;
import com.mt.booktracker.model.UpdateBookCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackerService {

    private final BookRepository bookRepository;

    public Page<BookResponse> getBooks(Pageable pageable) {
        var books = bookRepository
                .findAll(pageable)
                .map(BookMapper::map);
        return new Page<>(books.getContent(), books.getTotalPages(), books.getTotalElements(), pageable.getPageNumber());
    }

    public BookResponse findByIsbn(String isbn) {
        return bookRepository.findById(isbn)
                .map(BookMapper::map)
                .orElseThrow(BookNotFoundException::new);
    }

    @Transactional
    public String addBook(AddBookCommand addBookCommand) throws DataIntegrityViolationException {
        bookRepository.findById(addBookCommand.getIsbn()).ifPresent(i -> {
            throw new BookAlreadyExistsException();
        });
        var savedBook = bookRepository.save(BookMapper.map(addBookCommand));
        log.info(String.format("Successfully saved book with isbn: %s", savedBook.getIsbn()));
        return savedBook.getIsbn();
    }

    public void deleteBook(String isbn) {
        var existingBook = bookRepository
                .findById(isbn)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.delete(existingBook);
        log.info(String.format("Successfully deleted book with isbn: %s", isbn));
    }

    public void updateBook(String isbn, UpdateBookCommand updateBookCommand) {
        var existingBook = bookRepository
                .findById(isbn)
                .orElseThrow(BookNotFoundException::new);
        var updatedBook = BookMapper.createUpdatedBook(existingBook.getIsbn(), updateBookCommand);
        bookRepository.save(updatedBook);
        log.info(String.format("Successfully updated book with isbn: %s", isbn));
    }
}
