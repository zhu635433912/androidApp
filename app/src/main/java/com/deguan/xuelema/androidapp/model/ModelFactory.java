package com.deguan.xuelema.androidapp.model;


import com.deguan.xuelema.androidapp.model.impl.OrderModelImpl;
import com.deguan.xuelema.androidapp.model.impl.TuijianModelImpl;

/**
 * model工厂类
 * Created by dell on 2016/4/5.
 */
public class ModelFactory {
    private static volatile ModelFactory instance = null;

    private ModelFactory(){
    }

    public static ModelFactory getInstance() {
        if (instance == null) {
            synchronized (ModelFactory.class) {
                if (instance == null) {
                    instance = new ModelFactory();
                }
            }
        }
        return instance;
    }

    public TuijianModelImpl getTuijianModelImlp(){
        return TuijianModelImpl.getInstance();
    }

    public OrderModelImpl getOrderModelImpl(){
        return OrderModelImpl.getInstance();
    }
}
