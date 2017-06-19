package com.deguan.xuelema.androidapp.viewimpl;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public interface TurnoverView {
    void successTurnover(List<Map<String,Object>> list);
    void failTurnover(String msg);
}
