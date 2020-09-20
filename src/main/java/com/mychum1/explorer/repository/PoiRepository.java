package com.mychum1.explorer.repository;

import com.mychum1.explorer.domain.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoiRepository extends JpaRepository<Poi, String> {


    @Modifying
    @Query(value="update poi p set count = count+1 where p.keyword=?1", nativeQuery=true)
    Integer updateHotKeyword(String keyword);

    @Query(value="select top ?1 * from poi order by count desc", nativeQuery = true)
    List<Poi> getTop10(int num);
}
