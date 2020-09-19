package com.mychum1.explorer.domain;

import java.util.List;

public class KaKaoDocuments extends Documents{
    private Meta meta;
    private List<KaKaoPlace> documents;

    public List<KaKaoPlace> getDocuments() {
        return documents;
    }

    public void setDocuments(List<KaKaoPlace> documents) {
        this.documents = documents;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "KaKaoDocuments{" +
                "meta=" + meta +
                ", documents=" + documents +
                '}';
    }
}
