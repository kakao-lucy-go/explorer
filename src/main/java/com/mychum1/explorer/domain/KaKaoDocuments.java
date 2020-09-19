package com.mychum1.explorer.domain;

import java.util.List;

public class KaKaoDocuments extends Documents{
    private List<KaKaoPlace> documents;

    public List<KaKaoPlace> getDocuments() {
        return documents;
    }

    public void setDocuments(List<KaKaoPlace> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "KaKaoDocuments{" +
                "documents=" + documents +
                '}';
    }
}
