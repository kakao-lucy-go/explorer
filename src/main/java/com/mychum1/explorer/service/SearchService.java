package com.mychum1.explorer.service;

import com.mychum1.explorer.exception.SearchException;

import java.io.IOException;

public interface SearchService<T> {

    /**
     * 장소를 검색한다.
     * @param keyword : 장소 명
     * @param page : 페이지
     * @param size : 데이터 사이즈
     * @return
     * @throws IOException
     * @throws SearchException
     */
    T searchPlacesByKeyword(String keyword, Integer page, Integer size) throws IOException, SearchException;
}
