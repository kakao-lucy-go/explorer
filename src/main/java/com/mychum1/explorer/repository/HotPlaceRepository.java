package com.mychum1.explorer.repository;

import com.mychum1.explorer.domain.HotPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotPlaceRepository extends JpaRepository<HotPlace, String> {


    @Modifying
    @Query(value="update hotplace p set count = count+1 where p.keyword=?1", nativeQuery=true)
    Integer updateHotKeyword(String keyword);

    @Query(value="select top ?1 * from hotplace order by count desc", nativeQuery = true)
    List<HotPlace> getTopRank(int num);
}
