package com.mt.booktracker.validation;

import java.util.HashMap;
import java.util.Map;

public class IsbnConfiguration {

    static final String ISBN10_PATTERN = "^(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})" +
            "[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";

    static final String ISBN13_PATTERN = "^(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)" +
            "97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$";

    static final String ISBN10 = "ISBN10";
    static final String ISBN13 = "ISBN13";


    public static Map<String, String> initPatters() {
        Map<String, String> patterns = new HashMap<>();
        patterns.put(ISBN10, ISBN10_PATTERN);
        patterns.put(ISBN13, ISBN13_PATTERN);
        return patterns;
    }
}
