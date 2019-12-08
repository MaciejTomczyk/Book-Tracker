package com.mt.booktracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity(name = "books")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Book {

    @Id
    private String isbn;

    private String title;

    private String author;

    private int numberOfPages;

    private int rating;

    @Version
    private long version;

}
