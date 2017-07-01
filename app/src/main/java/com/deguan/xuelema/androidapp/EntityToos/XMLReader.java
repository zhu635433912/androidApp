package com.deguan.xuelema.androidapp.EntityToos;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Created by Administrator on 2017/6/30.
 */

public interface XMLReader {
    //注册处理XML文档解析事件ContentHandler
    public void setContentHandler(ContentHandler handler);

    //开始解析一个XML文档
    public void parse(InputStream input) throws SAXException;


}
