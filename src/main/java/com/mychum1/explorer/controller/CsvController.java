package com.mychum1.explorer.controller;

import com.mychum1.explorer.common.CommonCode;
import com.mychum1.explorer.common.CsvProcessor;
import com.mychum1.explorer.domain.KaKaoDocuments;
import com.mychum1.explorer.domain.Response;
import com.mychum1.explorer.exception.SearchException;
import com.mychum1.explorer.handler.ApiHandler;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/csv")
public class CsvController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CsvProcessor csvProcessor;

    @GetMapping("/test")
    public ResponseEntity<Response> test(@RequestParam(value = "num", required = false, defaultValue = "10") int num) throws IOException {
        logger.info("call test() num:{}", num);
        csvProcessor.test();
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), CommonCode.SUCCESS_MSG, "good"), HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<Response> test2(@RequestParam(value = "num", required = false, defaultValue = "10") int num) throws IOException, CsvValidationException {
        logger.info("call test() num:{}", num);
        csvProcessor.saveCsvData();
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), CommonCode.SUCCESS_MSG, csvProcessor.readCsvData()), HttpStatus.OK);
    }


}
