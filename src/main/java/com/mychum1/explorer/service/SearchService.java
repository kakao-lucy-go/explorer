package com.mychum1.explorer.service;

import com.mychum1.explorer.exception.SearchException;

import java.io.IOException;

public interface SearchService<T> {

    T searchPlacesByKeyword(String keyword, Integer page, Integer size) throws IOException, SearchException;
}
