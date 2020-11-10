package com.mychum1.explorer.controller;

import com.mychum1.explorer.common.CommonCode;
import com.mychum1.explorer.domain.KaKaoDocuments;
import com.mychum1.explorer.domain.Response;
import com.mychum1.explorer.exception.SearchException;
import com.mychum1.explorer.handler.ApiHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class ApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiHandler apiHandler;

    /**
     * 카카오 검색 API 를 활용하여 키워드로 장소를 검색한다.
     * @param keyword : 장소 명
     * @param repeat : 반복 여부. 인기 검색어 업데이트에 영향을 준다. true: 업데이트 하지 않음. false: 업데이트 함
     * @param page : 페이지 번호
     * @param size : 데이터 사이즈
     * @return
     */
    //@Secured("ROLE_ADMIN")
    @GetMapping("/kakao/places")
    public ResponseEntity<Response> searchPlacesByKeywordUsingKaKao(@RequestParam(value = "keyword") String keyword,
                                                                       @RequestParam(value = "repeat", required = false, defaultValue = "false") Boolean repeat,
                                                                       @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                                       @RequestParam(value = "size", required = false, defaultValue = "15") Integer size) {
        logger.info("call searchPlacesByKeywordUsingKaKao() keyword:{}, repeat:{}, page:{}, size:{}", keyword, repeat, page, size);
        try {
            KaKaoDocuments kaKaoDocuments = apiHandler.searchPlacesByKeywordUsingKaKao(keyword, page, size, repeat);
            if(kaKaoDocuments == null) {
                return new ResponseEntity<>(new Response<KaKaoDocuments>(HttpStatus.NO_CONTENT.value(), CommonCode.NO_DATA, null), HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), CommonCode.SUCCESS_MSG, kaKaoDocuments), HttpStatus.OK);
            }

        }catch(SearchException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(new Response<KaKaoDocuments>(e.getCode(), e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 인기 키워드 랭킹을 반환한다.
     * @param num : 반환받을 인기 키워드 데이터 사이즈
     * @return
     */
    //@Secured("ROLE_ADMIN")
    @GetMapping("/rank")
    public ResponseEntity<Response> topRanking(@RequestParam(value = "num", required = false, defaultValue = "10") int num) {
        logger.info("call topRanking() num:{}", num);
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), CommonCode.SUCCESS_MSG, apiHandler.topRanking(num)), HttpStatus.OK);
    }


}
