package com.mychum1.explorer.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="black_list")
public class BlackList {

    @Id
    private String token;


    public BlackList() {

    }

    public BlackList(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
