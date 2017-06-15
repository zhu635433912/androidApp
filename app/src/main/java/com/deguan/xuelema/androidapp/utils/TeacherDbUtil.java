package com.deguan.xuelema.androidapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.deguan.xuelema.androidapp.dao.DaoMaster;
import com.deguan.xuelema.androidapp.dao.DaoSession;
import com.deguan.xuelema.androidapp.dao.TeacherEntityDao;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p>
 * 项目名称：androidApp
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2017-06-14 14:08
 * 修改人：zhuyunjian
 * 修改时间：2017-06-14 14:08
 * 修改备注：
 */
public class TeacherDbUtil {
    private static DaoSession session;
    public static void init(Context context){
        RoomOpenHelper helper = new RoomOpenHelper(context,"teacherlist.db",null);
        session = new DaoMaster(helper.getWritableDatabase()).newSession();
    }
    public static DaoSession getSession(){
        return session;
    }
    private static class RoomOpenHelper extends DaoMaster.DevOpenHelper{

        public RoomOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            super.onCreate(db);
            TeacherEntityDao dao = new DaoMaster(db).newSession().getTeacherEntityDao();
        }
    }
}
