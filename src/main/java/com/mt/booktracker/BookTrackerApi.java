package com.mt.booktracker;

import com.mt.booktracker.model.AddBookCommand;
import com.mt.booktracker.model.BookResponse;
import com.mt.booktracker.model.Page;
import com.mt.booktracker.model.UpdateBookCommand;
import com.mt.booktracker.service.TrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookTrackerApi {

    private final TrackerService trackerService;

    @GetMapping
    public ResponseEntity<Page<BookResponse>> getBooks(Pageable pageable) {
        return ResponseEntity.ok(trackerService.getBooks(pageable));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponse> findById(@PathVariable String isbn) {
        return ResponseEntity.ok(trackerService.findByIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody @Valid AddBookCommand addBookCommand) {
        String isbn = trackerService.addBook(addBookCommand);
        return ResponseEntity.created(createLocationHeader(isbn)).build();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
        trackerService.deleteBook(isbn);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Void> updateBook(@PathVariable String isbn, @RequestBody @Valid UpdateBookCommand updateBookCommand) {
        trackerService.updateBook(isbn, updateBookCommand);
        return ResponseEntity.ok().build();
    }

    private URI createLocationHeader(String isbn) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{isbn}")
                .buildAndExpand(isbn)
                .toUri();
    }

}
