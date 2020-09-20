package com.mychum1.explorer.service;

import com.mychum1.explorer.domain.Poi;
import com.mychum1.explorer.repository.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HotKeywordService {


    @Autowired
    private PoiRepository repository;

    //TODO 차이점 확인해두기
    @Transactional
    public Integer updateHotKeyword(String keyword) {
        //메모리 디비에 키워드 저장
        Integer result = 0;
        if (repository.existsById(keyword)) {

            result = repository.updateHotKeyword(keyword);
        } else {
            Poi poiEntity = new Poi();
            poiEntity.setKeyword(keyword);
            poiEntity.setCount(1);
            repository.save(poiEntity);
            result +=1;
        }

        return result;
    }

    public List<Poi> topRanking(int num) {
        return repository.getTop10(num);
    }
}
