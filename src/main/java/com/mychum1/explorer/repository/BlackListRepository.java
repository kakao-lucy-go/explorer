package com.mychum1.explorer.repository;

import com.mychum1.explorer.domain.BlackList;
import com.mychum1.explorer.domain.Member2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList, String> {

}
