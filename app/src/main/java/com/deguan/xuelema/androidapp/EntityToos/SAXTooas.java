package com.deguan.xuelema.androidapp.EntityToos;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.deguan.xuelema.androidapp.entities.EntityProvince;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * Created by Administrator on 2017/6/30.
 */

public class SAXTooas {
    private Context mContext;
    private List<EntityProvince> list;

    public SAXTooas(Context context){
        mContext=context;
    }

    //获取xml初始化方法
    public  List<EntityProvince> initProvinceData() {
        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream input = assetManager.open("province_datas.xml");
            //实例化SAX工厂类
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //实例化SAX解析器。
            SAXParser sParser = null;
            sParser = factory.newSAXParser();
            //实例化DefaultHandler，设置需要解析的节点
            SaxRegionParser myHandler = new SaxRegionParser("Province");
            // 开始解析
            sParser.parse(input, myHandler);
            // 解析完成之后，关闭流
            input.close();

            list = myHandler.getListProvince();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       return list;
    }
}
