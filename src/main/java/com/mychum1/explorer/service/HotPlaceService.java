package com.mychum1.explorer.service;

import com.mychum1.explorer.domain.HotPlace;
import com.mychum1.explorer.repository.HotPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HotPlaceService {


    @Autowired
    private HotPlaceRepository repository;

    /**
     * 장소 검색 키워드 횟수를 업데이트한다.
     * @param keyword : 장소 명
     * @return
     */
    @Transactional
    public Integer updateHotKeyword(String keyword) {
        Integer result = 0;
        if (repository.existsById(keyword)) {
            result = repository.updateHotKeyword(keyword);
        } else {
            HotPlace hotPlace = new HotPlace();
            hotPlace.setKeyword(keyword);
            hotPlace.setCount(1);
            repository.save(hotPlace);
            result +=1;
        }

        return result;
    }

    /**
     * 인기 장소 검색 키워드를 반환한다.
     * @param num : 반환받을 검색 키워드 수
     * @return
     */
    public List<HotPlace> topRanking(int num) {
        return repository.getTopRank(num);
    }
}
