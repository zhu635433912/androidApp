package com.deguan.xuelema.androidapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.deguan.xuelema.androidapp.dao.DaoMaster;
import com.deguan.xuelema.androidapp.dao.DaoSession;
import com.deguan.xuelema.androidapp.dao.TeacherEntityDao;
import com.deguan.xuelema.androidapp.dao.XuqiuEntityDao;
import com.deguan.xuelema.androidapp.entities.TeacherEntity;

public class DbUtil {
    private static DaoSession session;
    public static void init(Context context){
        RoomOpenHelper helper = new RoomOpenHelper(context,"xuqiulist.db",null);
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
            XuqiuEntityDao dao = new DaoMaster(db).newSession().getXuqiuEntityDao();
            TeacherEntityDao dao1 = new DaoMaster(db).newSession().getTeacherEntityDao();
        }
    }
}
