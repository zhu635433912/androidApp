package com.deguan.xuelema.androidapp.entities;

import java.util.List;

/**
 * 市
 */

public class EntityCity {
    private List<EntityRegion> list;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EntityRegion> getList() {
        return list;
    }

    public void setList(List<EntityRegion> list) {
        this.list = list;
    }
}
