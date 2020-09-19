package com.mychum1.explorer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("pageable_count")
    private Integer pageableCount;

    private Boolean isEnd;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageableCount() {
        return pageableCount;
    }

    public void setPageableCount(Integer pageableCount) {
        this.pageableCount = pageableCount;
    }

    public Boolean getEnd() {
        return isEnd;
    }

    public void setEnd(Boolean end) {
        isEnd = end;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "totalCount=" + totalCount +
                ", pageableCount=" + pageableCount +
                ", isEnd=" + isEnd +
                '}';
    }
}
