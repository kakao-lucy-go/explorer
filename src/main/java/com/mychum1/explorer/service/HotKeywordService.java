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
        /**
         * Redis에 값을 넣고 출력하는 예제
         */
        Integer result = 0;
        if (repository.existsById(keyword)) {

            result = repository.updateHotKeyword(keyword);
//            Poi temp = repository.findById(keyword).get();
//            temp.setCount(temp.getCount() + 1);
//            repository.save(temp);

            System.out.println("keyword : " + keyword);
            repository.findAll().forEach(i -> System.out.println(i.getKeyword() +" " + i.getCount()));

        } else {
            Poi poiEntity = new Poi();
            poiEntity.setKeyword(keyword);
            poiEntity.setCount(1);
            repository.save(poiEntity);
        }

        //System.out.println(repository.findAll().get(0).getCount());
        return result;
    }

    public List<Poi> topRanking(int num) {
        return repository.getTop10(num);
    }
}
