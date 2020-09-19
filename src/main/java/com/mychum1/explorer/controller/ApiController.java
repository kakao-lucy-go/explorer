package com.mychum1.explorer.controller;

import com.mychum1.explorer.domain.Place;
import com.mychum1.explorer.domain.Poi;
import com.mychum1.explorer.handler.ApiHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiHandler apiHandler;

    //TODO 예외처리 추가
    //TODO 결과값 정의할까?
    @Secured("ROLE_ADMIN")
    @GetMapping("/kakao/places")
    public ResponseEntity<List<Place>> searchPlacesByKeywordUsingKaKao(@RequestParam(value = "keyword") String keyword,@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,@RequestParam(value = "size", required = false, defaultValue = "15") Integer size) {
        logger.info("call searchPlacesByKeywordUsingKaKao()");
        try {
            return new ResponseEntity<>(apiHandler.searchPlacesByKeywordUsingKaKao(keyword, page, size), HttpStatus.ACCEPTED);
        }catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/rank")
    public ResponseEntity<List<Poi>> topRanking(@RequestParam(value = "num", defaultValue = "10") int num) {
        return new ResponseEntity<>(apiHandler.topRanking(num), HttpStatus.ACCEPTED);
    }


}
