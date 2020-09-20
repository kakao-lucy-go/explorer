package com.mychum1.explorer.controller;

import com.mychum1.explorer.domain.KaKaoDocuments;
import com.mychum1.explorer.domain.Response;
import com.mychum1.explorer.exception.SearchException;
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

@Controller
@RequestMapping("/api")
public class ApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiHandler apiHandler;

    //TODO 예외처리 추가
    @Secured("ROLE_ADMIN")
    @GetMapping("/kakao/places")
    public ResponseEntity<Response> searchPlacesByKeywordUsingKaKao(@RequestParam(value = "keyword") String keyword,
                                                                       @RequestParam(value = "repeat", required = false, defaultValue = "false") Boolean repeat,
                                                                       @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                                       @RequestParam(value = "size", required = false, defaultValue = "15") Integer size) {
        logger.info("call searchPlacesByKeywordUsingKaKao()");
        try {
            KaKaoDocuments kaKaoDocuments = apiHandler.searchPlacesByKeywordUsingKaKao(keyword, page, size, repeat);
            if(kaKaoDocuments == null) {
                return new ResponseEntity<>(new Response<KaKaoDocuments>(HttpStatus.NO_CONTENT.value(), "no data", null), HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), "success", kaKaoDocuments), HttpStatus.OK);
            }

        }catch(SearchException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(new Response<KaKaoDocuments>(e.getCode(), e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/rank")
    public ResponseEntity<Response> topRanking(@RequestParam(value = "num", defaultValue = "10") int num) {
        logger.info("call topRanking()");
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), "success", apiHandler.topRanking(num)), HttpStatus.OK);
    }


}
