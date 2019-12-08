package com.mt.booktracker.model;

import lombok.Value;

import java.util.List;

@Value
public class Page<T> {

    private List<T> content;
    private int totalPages;
    private Long totalElements;
    private int page;
}
