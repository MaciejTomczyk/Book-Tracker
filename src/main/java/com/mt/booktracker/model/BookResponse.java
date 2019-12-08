package com.mt.booktracker.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookResponse {

    private String isbn;

    private String title;

    private String author;

    private int numberOfPages;

    private int rating;
}
