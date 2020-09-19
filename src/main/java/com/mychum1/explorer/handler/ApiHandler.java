package com.mychum1.explorer.handler;

import com.mychum1.explorer.domain.Place;
import com.mychum1.explorer.domain.Poi;
import com.mychum1.explorer.service.HotKeywordService;
import com.mychum1.explorer.service.kakao.KaKaoSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ApiHandler {

    @Autowired
    private KaKaoSearchService searchService;

    @Autowired
    private HotKeywordService hotKeywordService;

    /**
     * 카카오 장소 검색 API 를 사용해서 keyword 를 검색한다.
     * @param keyword
     * @return
     * @throws IOException
     */
    public List<Place> searchPlacesByKeywordUsingKaKao(String keyword, Integer page, Integer size) throws IOException {
        List<Place> list = (List<Place>) searchService.searchPlacesByKeyword(keyword, page, size);
        hotKeywordService.updateHotKeyword(keyword);
        return list;
    }

    /**
     * 원하는 개수로 핫한 키워드를 랭크한다.
     * @param num
     * @return
     */
    public List<Poi> topRanking(int num) {
        return hotKeywordService.topRanking(num);
    }

}
