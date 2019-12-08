package com.mt.booktracker.model;

import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class UpdateBookCommand {

    @Size(min = 1, max = 255)
    private String title;

    @Size(min = 1, max = 255)
    private String author;

    @Min(1)
    @NotNull
    private Integer numberOfPages;

    @Max(5)
    @Min(1)
    @NotNull
    private Integer rating;
}
