package com.deguan.xuelema.androidapp.entities;

import java.util.List;

/**
 * 省
 */

public class EntityProvince {
    List<EntityCity> City;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<EntityCity> getCity() {
        return City;
    }

    public void setCity(List<EntityCity> city) {
        City = city;
    }
}
