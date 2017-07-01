package com.deguan.xuelema.androidapp.EntityToos;

import android.util.Log;

import com.deguan.xuelema.androidapp.entities.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/6/30.
 */

public class SaxRegionParser extends DefaultHandler {
    private EntityProvince entityProvince;
    private EntityCity entityCity;
    private EntityRegion entityRegion;
    private List<EntityProvince>  listProvince;


    private String nodeName;

    public List<EntityProvince> getListProvince(){
        return listProvince;
    }


    public SaxRegionParser(String name){
        nodeName=name;
    }


    @Override
    public void startDocument() throws SAXException {
        // 接受到一个XML文档时候的通知。 <?xml version="1.0" encoding="utf-8"?>
        listProvince=new ArrayList<>();


    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // 接受到一个开始元素的通知，并且可以在此获得元素的属性
        //<China>
        //<Province>
        //<City>
        //<District>
        //-----------------------------------------------------
        if (qName.equals("Province")){
            //如果遇到Province这个节点则实例化Base类
            entityProvince=new EntityProvince();
            entityProvince.setName(attributes.getValue(0));
            entityProvince.setCity(new ArrayList<EntityCity>());
        }else if (qName.equals("City")){
            entityCity=new EntityCity();
            entityCity.setName(attributes.getValue(0));
            entityCity.setList(new ArrayList<EntityRegion>());
        }else if (qName.equals("District")){
            entityRegion=new EntityRegion();
            entityRegion.setName(attributes.getValue(0));
            entityRegion.setLng(attributes.getValue(1));
            entityRegion.setLat(attributes.getValue(2));
        }


    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("District")){
            entityCity.getList().add(entityRegion);
        }else if (qName.equals("City")){
            entityProvince.getCity().add(entityCity);
        } else if (qName.equals("Province")){
            listProvince.add(entityProvince);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //接收元素中字符数据的通知。


    }

    @Override
    public void endDocument() throws SAXException {
        //接受一个文档的结束通知。
        //</China>
        //</Province>
        //</City>
        //</District>
    }
}
