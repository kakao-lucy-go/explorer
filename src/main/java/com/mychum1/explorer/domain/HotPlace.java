package com.mychum1.explorer.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hotplace")
public class HotPlace {
    @Id
    private String keyword;
    private Integer count;

    public HotPlace() {}
    public HotPlace(String keyword, Integer count) {this.keyword=keyword; this.count=count;}
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
