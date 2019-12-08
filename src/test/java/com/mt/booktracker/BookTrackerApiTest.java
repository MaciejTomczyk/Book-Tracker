package com.mt.booktracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.booktracker.model.AddBookCommand;
import com.mt.booktracker.model.UpdateBookCommand;
import com.mt.booktracker.service.BookMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookTrackerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    @AfterEach
    void cleanUp(){
        bookRepository.deleteAll();
    }

    private static final String BOOK_ID = "978-2-123-45680-3";

    @Test
    void addBook() throws Exception {
        AddBookCommand addBookCommand = new AddBookCommand(BOOK_ID, "MyBook", "I", 123, 4);

        mockMvc.perform(MockMvcRequestBuilders.post("/books").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addBookCommand)))
                .andExpect(status().isCreated());
    }

    @Test
    void addExistingBook() throws Exception {
        AddBookCommand addBookCommand = new AddBookCommand(BOOK_ID, "MyBook", "I", 123, 4);
        bookRepository.save(BookMapper.map(addBookCommand));

        mockMvc.perform(MockMvcRequestBuilders.post("/books").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addBookCommand)))
                .andExpect(status().isConflict());
    }

    @Test
    void findById() throws Exception {
        addBookToDb();

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(BOOK_ID));
    }

    @Test
    void getBooks() throws Exception {
        addBookToDb();

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].isbn").value(BOOK_ID));
    }

    @Test
    void updateBook() throws Exception {
       addBookToDb();

        UpdateBookCommand updateBookCommand = new UpdateBookCommand("MyBook", "Someone Else", 123, 4);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + BOOK_ID).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateBookCommand)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBook() throws Exception {
        addBookToDb();

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/" + BOOK_ID))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNonExistingBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/" + BOOK_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateNonExistingBook() throws Exception {
        UpdateBookCommand addBookCommand = new UpdateBookCommand("MyBook", "Someone Else", 123, 4);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + BOOK_ID).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addBookCommand)))
                .andExpect(status().isNotFound());
    }

    private void addBookToDb(){
        AddBookCommand addBookCommand = new AddBookCommand(BOOK_ID, "MyBook", "I", 123, 4);
        bookRepository.save(BookMapper.map(addBookCommand));
    }


}