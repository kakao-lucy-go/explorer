package com.mychum1.explorer.service.kakao;

import com.mychum1.explorer.domain.KaKaoDocuments;
import com.mychum1.explorer.domain.KaKaoPlace;
import com.mychum1.explorer.service.SearchService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class KaKaoSearchService<T> implements SearchService {

    private CloseableHttpClient httpclient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${kakao.search.url}")
    private String kakaoSearchUrl;

    private String kakaoSearchUrlWithQuery;

    @Value("${kakao.key}")
    private String kakaoKey;

    private String appkakaoKey;

    private HttpHeaders headers;

    @PostConstruct
    public void setHttpClient() {
        appkakaoKey = new StringBuilder("KakaoAK ").append(kakaoKey).toString();
        //
        kakaoSearchUrlWithQuery = new StringBuilder(kakaoSearchUrl).append("?page={page}&size={size}&query={query}").toString();
        headers = new HttpHeaders();
        headers.add("Authorization",appkakaoKey);
    }


    /**
     * 키워드 기반으로 장소를 검색한다.
     * @param keyword
     * @return
     * @throws IOException
     */
    @Override
    public T searchPlacesByKeyword(String keyword, Integer page, Integer size) throws IOException {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", String.valueOf(page));
        params.add("size", String.valueOf(size));
        params.add("query", keyword);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(kakaoSearchUrl)
                .queryParams(params);

        ResponseEntity<KaKaoDocuments> kaKaoDocumentsResponseEntity = restTemplate.exchange(builder.encode().build().toUri(), HttpMethod.GET, new HttpEntity<>(headers), KaKaoDocuments.class);
        KaKaoDocuments kaKaoDocuments = null;
        if(kaKaoDocumentsResponseEntity.getStatusCode().is2xxSuccessful()) {
            kaKaoDocuments = kaKaoDocumentsResponseEntity.getBody();
        }

        List<KaKaoPlace> list = new ArrayList<>();
        if(isValidKaKaoDocuments(kaKaoDocuments)) {
            list = kaKaoDocuments.getDocuments();
        }

        //TODO 이부분 스트림형식으로 수정
        int resultSize = list.size();
        System.out.println(resultSize);
        for (int i = 0; i < resultSize; i++) {
            KaKaoPlace place = list.get(i);
            place.setLink(new StringBuilder("https://map.kakao.com/link/map/").append(place.getId()).toString());
        }

        return (T) kaKaoDocuments;
    }

    public boolean isValidKaKaoDocuments(KaKaoDocuments kaKaoDocuments) {
        return kaKaoDocuments != null && kaKaoDocuments.getDocuments() != null && kaKaoDocuments.getDocuments().size() > 0;
    }
}
