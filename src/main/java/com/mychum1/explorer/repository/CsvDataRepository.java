package com.mychum1.explorer.repository;

import com.mychum1.explorer.domain.BlackList;
import com.mychum1.explorer.domain.CsvData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvDataRepository extends JpaRepository<CsvData, String> {

}
