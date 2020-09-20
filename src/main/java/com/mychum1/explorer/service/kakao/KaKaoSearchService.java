package com.mychum1.explorer.service.kakao;

import com.mychum1.explorer.common.CommonCode;
import com.mychum1.explorer.domain.KaKaoDocuments;
import com.mychum1.explorer.domain.KaKaoPlace;
import com.mychum1.explorer.exception.SearchException;
import com.mychum1.explorer.service.SearchService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class KaKaoSearchService<T> implements SearchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${kakao.search.url}")
    private String kakaoSearchUrl;

    @Value("${kakao.key}")
    private String kakaoKey;

    @Value("${daum.map.url}")
    private String daumMapUrl;

    private String appkakaoKey;

    private HttpHeaders headers;

    @PostConstruct
    public void initialize() {
        //자주 사용하는 키 값을 미리 정의한다.
        appkakaoKey = new StringBuilder("KakaoAK ").append(kakaoKey).toString();

        //자주 사용하는 헤더 값을 미리 정의한다.
        headers = new HttpHeaders();
        headers.add("Authorization",appkakaoKey);
    }


    /**
     * 키워드 기반으로 장소를 검색한다.
     * @param keyword : 장소 명
     * @return
     * @throws IOException
     */
    @Override
    public T searchPlacesByKeyword(String keyword, Integer page, Integer size) throws SearchException {

        logger.info("call searchPlacesByKeyword(). keyword:{}, page:{}, size:{}", keyword, page, size);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", String.valueOf(page));
        params.add("size", String.valueOf(size));
        params.add("query", keyword);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(kakaoSearchUrl)
                .queryParams(params);

        try {
            if(keyword.equals("")) {
                throw new SearchException(HttpStatus.BAD_REQUEST.value(), CommonCode.ERROR_PARAMETER_WEIRD);
            }
            ResponseEntity<KaKaoDocuments> kaKaoDocumentsResponseEntity = restTemplate.exchange(builder.encode().build().toUri(), HttpMethod.GET, new HttpEntity<>(headers), KaKaoDocuments.class);
            KaKaoDocuments kaKaoDocuments = null;
            if (kaKaoDocumentsResponseEntity.getStatusCode().is2xxSuccessful()) {
                kaKaoDocuments = kaKaoDocumentsResponseEntity.getBody();
            } else {
                throw new SearchException(kaKaoDocumentsResponseEntity.getStatusCode().value(), CommonCode.INTERNAL_SERVER_ERROR);
            }

            List<KaKaoPlace> list = new ArrayList<>();
            if (isValidKaKaoDocuments(kaKaoDocuments)) {
                list = kaKaoDocuments.getDocuments();
            }

            //바로가기 링크 생성
            list.stream().forEach(i -> i.setLink(new StringBuilder(daumMapUrl).append(i.getId()).toString()));

            return (T) kaKaoDocuments;

        }catch(SearchException se) {
            throw new SearchException(se.getCode(), se.getMessage(), se);
        }
    }

    /**
     * KaKaoDocuments 유효성 확인
     * @param kaKaoDocuments
     * @return
     */
    public boolean isValidKaKaoDocuments(KaKaoDocuments kaKaoDocuments) {
        return kaKaoDocuments != null && kaKaoDocuments.getDocuments() != null && kaKaoDocuments.getDocuments().size() > 0;
    }
}
