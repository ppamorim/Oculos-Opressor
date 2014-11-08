package com.oculosopressor.oculosopressor.core.model;

/**
 * Created by pedro on 08/11/14.
 */
public class BaseModel {

    public static final String _RID = "remote_id";

    public int getRemoteId() {
        return -1;
    }

//    public static void truncate(Class<? extends Model> type) {
//        try {
//
//            TableInfo tableInfo = Cache.getTableInfo(type);
//            ActiveAndroid.execSQL("delete from " + tableInfo.getTableName() + ";");
//            ActiveAndroid.execSQL("delete from sqlite_sequence where name='" + tableInfo.getTableName() + "';");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
